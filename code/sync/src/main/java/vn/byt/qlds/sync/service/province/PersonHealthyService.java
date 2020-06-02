package vn.byt.qlds.sync.service.province;

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
import vn.byt.qlds.sync.configuration.YamlConfig;
import vn.byt.qlds.sync.core.ES.BulkServices;
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.core.utils.StringUtils;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.PersonRequest;
import vn.byt.qlds.sync.model.entity.PersonHealthy;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class PersonHealthyService extends CrudService<PersonHealthy, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    BulkServices bulkServices;
    @Autowired
    PersonService personService;
    @Value("${urlES}")
    public String url;
    private static final int SIZE_THREAD = 15;
    private static final int LIMIT = 100000;
    private static final String INDEX = "person-healthy";
    private static final String TYPE = "_doc";
    @Autowired
    YamlConfig yamlConfig;

    @RabbitListener(queues = "PersonHealthy")
    public void receivePersonHealthy(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<PersonHealthy> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<PersonHealthy>>() {
        }.getType());
        PersonHealthy personHealthy = esMessageSync.data;
        String id = syncOne(esMessageSync.getDbName(), personHealthy);
        System.out.println("\nCreate new PersonHealthy with id = " + id);

    }

    public void syncAllPersonHealthy(String dbName) {
        long startTime = System.currentTimeMillis();
        long totalAll = count(dbName);
        int totalPage = (int) (totalAll % LIMIT == 0 ? totalAll / LIMIT : totalAll / LIMIT + 1);
        for (int j = 1; j <= totalPage; j++) {
            System.out.printf("syncing person healthy page %d", j);
            List<PersonHealthy> personHealthies = this.getPage(dbName, j, LIMIT).getList();
            Map<Integer, String> pageToPercent = new HashMap<>();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(SIZE_THREAD);
            int size = personHealthies.size();
            int pageSize = (size % SIZE_THREAD == 0) ? size / SIZE_THREAD : size / SIZE_THREAD + 1;
            for (int i = 0; i < SIZE_THREAD; i++) {
                int from = i * pageSize;
                int to;
                if (i == (SIZE_THREAD - 1)) {
                    to = size;
                } else {
                    to = from + pageSize;
                }
                List<PersonHealthy> subPersonHealthy = personHealthies.subList(from, to);
                RequestHandle requestHandler = new RequestHandle(subPersonHealthy, from, to);
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
        System.out.println("\nPerson Healthy done !");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private String syncOne(String dbName, PersonHealthy personHealthy) {
        Map<String, Object> request = convert(personHealthy);
        String id = dbName + "_" + personHealthy.getId();
        qldsRestTemplate.putForObject(url + "/person-healthy/_doc/" + id, new Gson().toJson(request), String.class);
        return id;
    }

    private Map<String, Object> convert(PersonHealthy personHealthy) {
        PersonRequest personMother = personService.searchAndPerson(personHealthy.getPersonalId(), personHealthy.getRegionId());
        int genInAge = 10000;
        if (personMother != null) {
            long genDate = personHealthy.getGenDate() != null ? personHealthy.getGenDate().getTime() : 0L;
            long dateOfBirth = personMother.getDateOfBirth() != null ? personMother.getDateOfBirth() : 0L;

            Timestamp timestampGenDate = new Timestamp(genDate);
            LocalDate localGenDate = LocalDate.of(timestampGenDate.getYear() + 1900, timestampGenDate.getMonth() + 1, timestampGenDate.getDate());
            Timestamp timestampBirth = new Timestamp(dateOfBirth);
            LocalDate localDateBirth = LocalDate.of(timestampBirth.getYear() + 1900, timestampBirth.getMonth() + 1, timestampBirth.getDate());
            genInAge = StringUtils.calculateAge(localDateBirth, localGenDate) + 1;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(personHealthy, Map.class);
        map.put("dateSlss", personHealthy.getDateSlss() != null ? personHealthy.getDateSlss().getTime() : null);
        map.put("dateSlts1", personHealthy.getDateSlts1() != null ? personHealthy.getDateSlts1().getTime() : null);
        map.put("dateSlts2", personHealthy.getDateSlts2() != null ? personHealthy.getDateSlts2().getTime() : null);
        map.put("dateUpdate", personHealthy.getDateUpdate() != null ? personHealthy.getDateUpdate().getTime() : null);
        map.put("genDate", personHealthy.getGenDate() != null ? personHealthy.getGenDate().getTime() : null);
        map.put("timeCreated", personHealthy.getTimeCreated() != null ? personHealthy.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", personHealthy.getTimeLastUpdated() != null ? personHealthy.getTimeLastUpdated().getTime() : null);
        map.put("genInAge", genInAge);
        return map;
    }

    private class RequestHandle implements Runnable {
        public String dbName;
        int from, to;
        List<PersonHealthy> personHealthies;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<PersonHealthy> personHealthies, int from, int to) {
            this.personHealthies = personHealthies;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("\nStart Person Healthy synchronization progress from %d to %d", from, to));
            System.out.println("\nPerson healthy syncing...");
            BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
            personHealthies.forEach(personHealthy -> {
                String id = dbName + "_" + personHealthy.getId();
                Map<String, Object> personHealthyRequest = convert(personHealthy);
                IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                        .source(personHealthyRequest, XContentType.JSON);
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
            System.out.printf("\rPerson Healthy completed: from %d to %d", from, to);
        }
    }

}
