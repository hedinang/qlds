package vn.byt.qlds.sync.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.sync.configuration.QldsRestTemplate;
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.UnitCategory;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class UnitCategoryService extends CrudService<UnitCategory, Integer> {

    private static final int SIZE_THREAD = 10;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;

    @Value("${urlES}")
    public String url;

    public UnitCategoryRequest searchAndUnitCategory(String regionId) {
        if (regionId == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("code", regionId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/unit-category/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), UnitCategoryRequest.class);

        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "UnitCategory")
    public void receiveResidenceStatus(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<UnitCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<UnitCategory>>() {
        }.getType());

        UnitCategory unitCategory = esMessageSync.data;
        syncOne(unitCategory);
        System.out.println("Create new UnitCategory with id = " + unitCategory.getId());
    }


    public void syncAllUnitCategory() {
        long startTime = System.currentTimeMillis();

        List<UnitCategory> unitCategoryList = this.getAll("common");
        Map<Integer, String> pageToPercent = new HashMap<>();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) SIZE_THREAD);
        int size = unitCategoryList.size();
        int pageSize = (int) Math.ceil(size / SIZE_THREAD);
        for (int i = 0; i < SIZE_THREAD; i++) {
            int from = i * pageSize;
            int to;
            if (i == (SIZE_THREAD - 1)) {
                to = size;
            } else {
                to = from + pageSize;
            }

            UnitCategoryService.RequestHandle requestHandler = new UnitCategoryService.RequestHandle(unitCategoryList, from, to);
            requestHandler.pageToPercent = pageToPercent;
            executor.execute(requestHandler);
            pageToPercent.put(from, String.format("page %d  = %.2f %%", from / pageSize, 0f));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Chờ xử lý hết các request còn chờ trong Queue ...
        }
        System.out.println("UnitCategory done !");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private UnitCategoryRequest convert(UnitCategory unitCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(unitCategory, Map.class);
        map.put("timeCreated", unitCategory.getTimeCreated() != null ? unitCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", unitCategory.getTimeLastUpdated() != null ? unitCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, UnitCategoryRequest.class);
    }

    private void syncOne(UnitCategory unitCategory) {
        UnitCategoryRequest request = convert(unitCategory);
        qldsRestTemplate.putForObject(url + "/unit-category/_doc/" + unitCategory.getId(), new Gson().toJson(request), String.class);
    }

    private class RequestHandle implements Runnable {
        int from, to;
        List<UnitCategory> unitCategories;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<UnitCategory> unitCategories, int from, int to) {
            this.unitCategories = unitCategories;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("Start UnitCategory synchronization progress from %d to %d", from, to));
            int size = from - to;
            int page = from / size;
            for (int index = from; index < to; index++) {
                UnitCategory unitCategory = unitCategories.get(index);
                syncOne(unitCategory);
                pageToPercent.put(from, String.format("page %d  = %.2f %%", page, ((index + 1) - from) * 100f / size));
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.printf("\rUnitCategory completed: %3s%%", pageToPercent.values());
            }
        }
    }
}