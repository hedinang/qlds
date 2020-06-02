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
import vn.byt.qlds.sync.core.utils.StringUtils;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.ContraceptiveCategoryRequest;
import vn.byt.qlds.sync.model.ES.request.FamilyPlanningHistoryRequest;
import vn.byt.qlds.sync.model.ES.request.PersonRequest;
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.FamilyPlanningHistory;
import vn.byt.qlds.sync.model.entity.HouseHold;
import vn.byt.qlds.sync.service.common.ContraceptiveCategoryService;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class FamilyPlanningHistoryService extends CrudService<FamilyPlanningHistory, Integer> {

    private static final int SIZE_THREAD = 15;
    private static final int LIMIT = 100000;
    private static final String INDEX = "family-planning-history";
    private static final String TYPE = "_doc";
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    AddressService addressService;
    @Autowired
    PersonService personService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    ContraceptiveCategoryService contraceptiveCategoryService;
    @Autowired
    BulkServices bulkServices;

    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "FamilyPlanningHistory")
    public void receiveFamilyPlanningHistory(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<FamilyPlanningHistory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<FamilyPlanningHistory>>() {
        }.getType());
        FamilyPlanningHistory familyPlanningHistory = esMessageSync.data;
        String id = syncOne(esMessageSync.getDbName(), familyPlanningHistory);
        System.out.println("Create new FamilyPlanningHistory with id = " + id);
    }

    public FamilyPlanningHistoryRequest searchAndFamilyPlanningHistory(String id, String regionId) throws JsonProcessingException {
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("houseHoldId", regionId);
        mCondition.put("regionId", regionId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/family-planning-history/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), FamilyPlanningHistoryRequest.class);
    }

    public void syncAllFamilyPlanningHistory(String dbName) {
        long startTime = System.currentTimeMillis();
        long totalAll = count(dbName);
        int totalPage = (int) (totalAll % LIMIT == 0 ? totalAll / LIMIT : totalAll / LIMIT + 1);
        for (int j = 1; j <= totalPage; j++) {
            System.out.printf("syncing family planning history page %d", j);
            List<FamilyPlanningHistory> familyPlanningHistoryList = this.getPage(dbName, j, LIMIT).getList();
            Map<Integer, String> pageToPercent = new HashMap<>();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
            int size = familyPlanningHistoryList.size();
            int pageSize = (size % SIZE_THREAD == 0) ? size / SIZE_THREAD : size / SIZE_THREAD + 1;
            for (int i = 0; i < SIZE_THREAD; i++) {
                int from = i * pageSize;
                int to;
                if (i == (SIZE_THREAD - 1)) {
                    to = size;
                } else {
                    to = from + pageSize;
                }

                FamilyPlanningHistoryService.RequestHandle requestHandler = new FamilyPlanningHistoryService.RequestHandle(familyPlanningHistoryList, from, to);
                requestHandler.pageToPercent = pageToPercent;
                requestHandler.dbName = dbName;
                executor.execute(requestHandler);
                pageToPercent.put(from, String.format("page %d.%d  = %.2f %%", j, from / pageSize, 0f));
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Chờ xử lý hết các request còn chờ trong Queue ...
            }
        }

        System.out.println("\nFamilyPlanningHistory done !");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private String syncOne(String dbName, FamilyPlanningHistory familyPlanningHistory) throws ParseException, JsonProcessingException {
        Map<String, Object> request = convert(familyPlanningHistory);
        String id = dbName + "_" + familyPlanningHistory.getId();
        qldsRestTemplate.putForObject(url + "/family-planning-history/_doc/" + id, new Gson().toJson(request), String.class);
        return id;
    }

    private Map<String, Object> convert(FamilyPlanningHistory familyPlanningHistory) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(familyPlanningHistory, Map.class);
        map.put("dateUpdate", familyPlanningHistory.getDateUpdate() != null ? familyPlanningHistory.getDateUpdate().getTime() : null);
        Long contraDate = StringUtils.convertDateToLong(familyPlanningHistory.getContraDate(), "dd/MM/yyyy");
        map.put("contraDate", contraDate);
        map.put("timeCreated", familyPlanningHistory.getTimeCreated() != null ? familyPlanningHistory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", familyPlanningHistory.getTimeLastUpdated() != null ? familyPlanningHistory.getTimeLastUpdated().getTime() : null);
        return map;
    }

    private class RequestHandle implements Runnable {
        public String dbName;
        int from, to;
        List<FamilyPlanningHistory> familyPlanningHistories;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<FamilyPlanningHistory> familyPlanningHistories, int from, int to) {
            this.familyPlanningHistories = familyPlanningHistories;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("\nStart FamilyPlanningHistory synchronization progress from %d to %d", from, to));
            System.out.println("\nFamilyPlanningHistory syncing....");
            BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
            familyPlanningHistories.forEach(houseHold -> {
                String id = dbName + "_" + houseHold.getId();
                try {
                    Map<String, Object> request = convert(houseHold);
                    IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                            .source(request, XContentType.JSON);
                    bulkProcessor.add(indexRequest);
                } catch (ParseException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            try {
                boolean terminated = bulkProcessor.awaitClose(60L, TimeUnit.SECONDS);
                bulkProcessor.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.printf("\rFamilyPlanningHistory completed: from %d to %d", from, to);

        }
    }

}
