package vn.byt.qlds.sync.service.province;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.core.utils.StringUtils;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.*;
import vn.byt.qlds.sync.model.entity.PersonHistory;
import vn.byt.qlds.sync.service.common.ReasonChangeService;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class PersonHistoryService extends CrudService<PersonHistory, Integer> {
    private static final int SIZE_THREAD = 10;
    private static final int LIMIT = 100000;
    private static final String INDEX = "person-history";
    private static final String TYPE = "_doc";

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Value("${urlES}")
    public String url;
    @Autowired
    PersonService personService;
    @Autowired
    ReasonChangeService reasonChangeService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    BulkServices bulkServices;

    @RabbitListener(queues = "PersonHistory")
    public void receivePersonHistory(Message message) throws JsonProcessingException {
        String json = new String(message.getBody());
        ESMessageSync<PersonHistory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<PersonHistory>>() {
        }.getType());
        String id = syncOnePersonHistory(esMessageSync.getDbName(), esMessageSync.data);
        System.out.println("Create new PersonHistory with id = " + id);
    }

    public void syncAllPersonHistory(String dbName) throws JsonProcessingException {
        long startTime = System.currentTimeMillis();
        long totalAll = count(dbName);
        int totalPage = (int) (totalAll % LIMIT == 0 ? totalAll / LIMIT : totalAll / LIMIT + 1);
        for (int j = 1; j <= totalPage; j++) {
            System.out.printf("\nsyncing person history page %d", j);
            List<PersonHistory> personHistoryList = this.getPage(dbName, j, LIMIT).getList();
            Map<Integer, Integer> pageToPercent = new HashMap<>();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) SIZE_THREAD);
            int size = personHistoryList.size();
            System.out.printf("\nPerson history : %d", size);
            int pageSize = (size % SIZE_THREAD == 0) ? size / SIZE_THREAD : size / SIZE_THREAD + 1;
            for (int i = 0; i < SIZE_THREAD; i++) {
                int from = i * pageSize;
                int to;
                if (i == (SIZE_THREAD - 1)) {
                    to = size;
                } else {
                    to = from + pageSize;
                }
                List<PersonHistory> subPersonHistory = personHistoryList.subList(from, to);
                PersonHistoryService.RequestHandle requestHandler = new PersonHistoryService.RequestHandle(subPersonHistory, from, to);
                requestHandler.pageToPercent = pageToPercent;
                requestHandler.dbName = dbName;
                executor.execute(requestHandler);
                pageToPercent.put(from, 0);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Chờ xử lý hết các request còn chờ trong Queue ...
            }
        }
        System.out.println("\nPerson History done !");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private String syncOnePersonHistory(String dbName, PersonHistory personHistory) {
        String id = dbName + "_" + personHistory.getId();
        // doi voi bang rieng (tinh) thi id trong es la regionId + tableId
        Map<String, Object> request = convert(personHistory);
        String jsonObject = new Gson().toJson(request);
        qldsRestTemplate.putForObject(url + "/person-history/_doc/" + id, jsonObject, String.class);
        return id;
    }

    private Map<String, Object> convert(PersonHistory personHistory) {
        PersonRequest personRequest = personService.searchAndPerson(personHistory.getPersonalId(), personHistory.getRegionId());
        ObjectMapper objectMapper = new ObjectMapper();

        long dateUpdate = personHistory.getDateUpdate() != null ? personHistory.getDateUpdate().getTime() : 0L;
        Long dateOfBirth = personRequest.getDateOfBirth();

        Timestamp timestampDateUpdate = new Timestamp(dateUpdate);
        LocalDate localDateUpdate = LocalDate.of(timestampDateUpdate.getYear() + 1900, timestampDateUpdate.getMonth() + 1, timestampDateUpdate.getDate());
        Timestamp timestampBirth = new Timestamp(dateOfBirth != null ? dateOfBirth : 0L);
        LocalDate localDateBirth = LocalDate.of(timestampBirth.getYear() + 1900, timestampBirth.getMonth() + 1, timestampBirth.getDate());
        int updateInAge = StringUtils.calculateAge(localDateBirth, localDateUpdate) + 1;

        Map<String, Object> map = objectMapper.convertValue(personHistory, Map.class);
        map.put("changeDate", personHistory.getChangeDate() != null ? personHistory.getChangeDate().getTime() : null);
        map.put("comeDate", personHistory.getComeDate() != null ? personHistory.getComeDate().getTime() : null);
        map.put("dateUpdate", personHistory.getDateUpdate() != null ? personHistory.getDateUpdate().getTime() : null);
        map.put("dieDate", personHistory.getDieDate() != null ? personHistory.getDieDate().getTime() : null);
        map.put("goDate", personHistory.getGoDate() != null ? personHistory.getGoDate().getTime() : null);
        map.put("timeCreated", personHistory.getTimeCreated() != null ? personHistory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", personHistory.getTimeLastUpdated() != null ? personHistory.getTimeLastUpdated().getTime() : null);
        map.put("personRequest", personRequest);
        map.put("updateInAge", updateInAge);
        return map;
    }

    private class RequestHandle implements Runnable {
        public String dbName;
        int from, to;
        List<PersonHistory> personHistories;
        Map<Integer, Integer> pageToPercent;

        public RequestHandle(List<PersonHistory> personHistories, int from, int to) {
            this.personHistories = personHistories;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("\nStart Person History synchronization progress from %d to %d", from, to));
            System.out.println("\nPerson History syncing....");
            BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
            personHistories.forEach(personHistory -> {
                String id = dbName + "_" + personHistory.getId();
                Map<String, Object> personHistoryRequest = convert(personHistory);
                IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                        .source(new Gson().toJson(personHistoryRequest), XContentType.JSON);
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
            System.out.printf("\rPerson History completed: from %d to %d", from, to);
        }
    }
}
