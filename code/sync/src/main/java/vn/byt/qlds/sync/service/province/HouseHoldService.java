package vn.byt.qlds.sync.service.province;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.sync.configuration.QldsRestTemplate;
import vn.byt.qlds.sync.core.ES.BulkServices;
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.AddressRequest;
import vn.byt.qlds.sync.model.ES.request.HouseHoldRequest;
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.HouseHold;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class HouseHoldService extends CrudService<HouseHold, Integer> {

    private static final String INDEX = "house-hold";
    private static final String TYPE = "_doc";
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    AddressService addressService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    BulkServices bulkServices;
    @Value("${urlES}")
    public String url;
    private static final int SIZE_THREAD = 15;
    private static final int LIMIT = 60000;


    public HouseHoldRequest searchAndHouseHold(String houseHoldId, String regionId) throws JsonProcessingException {
        if (houseHoldId == null || regionId == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", houseHoldId);
        mCondition.put("regionId", regionId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/house-hold/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), HouseHoldRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "HouseHold")
    public void receiveHouseHold(Message message) throws JsonProcessingException {
        String json = new String(message.getBody());
        ESMessageSync<HouseHold> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<HouseHold>>() {
        }.getType());
        String id = syncOneHouseHold(esMessageSync.getDbName(), esMessageSync.data);
        System.out.println("Create new house hold with id = " + id);
    }

    public void syncAllHouseHold(String dbName) {
        long startTime = System.currentTimeMillis();
        long totalAll = count(dbName);
        int totalPage = (int) (totalAll % LIMIT == 0 ? totalAll / LIMIT : totalAll / LIMIT + 1);
        for (int j = 1; j <= totalPage; j++) {
            List<HouseHold> houseHoldList = this.getPage(dbName, j, LIMIT).getList();
            Map<Integer, String> pageToPercent = new HashMap<>();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) SIZE_THREAD);
            int size = houseHoldList.size();
            int pageSize = (size % SIZE_THREAD == 0) ? size / SIZE_THREAD : size / SIZE_THREAD + 1;
            for (int i = 0; i < SIZE_THREAD; i++) {
                int from = i * pageSize;
                int to;
                if (i == (SIZE_THREAD - 1)) {
                    to = size;
                } else {
                    to = from + pageSize;
                }
                List<HouseHold> subHouseHold = houseHoldList.subList(from, to);
                HouseHoldService.RequestHandle requestHandler = new HouseHoldService.RequestHandle(subHouseHold, from, to);
                requestHandler.pageToPercent = pageToPercent;
                requestHandler.dbName = dbName;
                executor.execute(requestHandler);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Chờ xử lý hết các request còn chờ trong Queue ...
            }
        }

        System.out.println("\nHouseHold done !");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private String syncOneHouseHold(String dbName, HouseHold houseHold) {
        Map<String, Object> houseHoldRequest = convert(houseHold);
        String id = dbName + "_" + houseHold.getId();
        qldsRestTemplate.putForObject(url + "/house-hold/_doc/" + id, new Gson().toJson(houseHoldRequest), String.class);
        return id;
    }

    private Map<String, Object> convert(HouseHold houseHold) {
        UnitCategoryRequest commune = houseHold.getRegionId() != null ? unitCategoryService.searchAndUnitCategory(houseHold.getRegionId()) : null;
        UnitCategoryRequest district = commune != null ? unitCategoryService.searchAndUnitCategory(commune.parent) : null;

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(houseHold, Map.class);
        map.put("timeCreated", houseHold.getTimeCreated() != null ? houseHold.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", houseHold.getTimeLastUpdated() != null ? houseHold.getTimeLastUpdated().getTime() : null);
        map.put("startDate", houseHold.getStartDate() != null ? houseHold.getStartDate().getTime() : null);
        map.put("endDate", houseHold.getEndDate() != null ? houseHold.getEndDate().getTime() : null);
        map.put("districtId", commune != null ? commune.parent : "");
        map.put("provinceId", district != null ? district.parent : "");
        return map;
    }

    private class RequestHandle implements Runnable {
        public String dbName;
        int from, to;
        List<HouseHold> houseHolds;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<HouseHold> houseHolds, int from, int to) {
            this.houseHolds = houseHolds;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("\nStart HouseHold synchronization progress from %d to %d", from, to));
            System.out.println("\nHouseHold syncing....");
            BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
            houseHolds.forEach(houseHold -> {
                String id = dbName + "_" + houseHold.getId();
                Map<String, Object> request = convert(houseHold);
                IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                        .source(request, XContentType.JSON);
                bulkProcessor.add(indexRequest);
            });
            try {
                boolean terminated = bulkProcessor.awaitClose(60L, TimeUnit.SECONDS);
                bulkProcessor.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("\rHouseHold completed: from %d to %d", from, to);
        }
    }

}
