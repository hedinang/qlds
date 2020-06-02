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
import vn.byt.qlds.sync.model.ES.request.HouseHoldRequest;
import vn.byt.qlds.sync.model.ES.request.PersonRequest;
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.Person;
import vn.byt.qlds.sync.service.common.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class PersonService extends CrudService<Person, Integer> {

    private static final String INDEX = "person";
    private static final String TYPE = "_doc";
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    HouseHoldService houseHoldService;
    @Autowired
    PersonHealthyService personHealthyService;
    @Autowired
    GenderService genderService;
    @Autowired
    EducationService educationService;
    @Autowired
    NationCategoryService nationCategoryService;
    @Autowired
    MaritalStatusService maritalStatusService;
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    ResidenceService residenceService;
    @Autowired
    TechnicalService technicalService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    BulkServices bulkServices;
    @Value("${urlES}")
    public String url;
    private static final float SIZE_THREAD = 15;
    private static final int LIMIT = 90000;
    public PersonRequest searchAndPerson(Integer personalId, String regionId) {
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", personalId);
        mCondition.put("regionId", regionId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/person/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), PersonRequest.class);

        } catch (Exception e) {
            return new PersonRequest();
        }
    }

    @RabbitListener(queues = "Person")
    public void receivePerson(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<Person> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Person>>() {
        }.getType());
        Person person = esMessageSync.data;
        syncOnePerson(esMessageSync.getDbName(), person);
        String id = person.getRegionId() + person.getPersonalId();
        System.out.println("Create new Person with id = " + id);
    }

    public void syncAllPerson(String dbName) {
        long startTime = System.currentTimeMillis();
        long totalAll = count(dbName);
        int totalPage = (int) (totalAll % LIMIT == 0 ? totalAll / LIMIT : totalAll / LIMIT + 1);

        for (int j = 1; j <= totalPage; j++) {
            System.out.printf("\nsyncing person page %d", j);
            List<Person> personList = this.getPage(dbName, j, LIMIT).getList();
            Map<Integer, String> pageToPercent = new HashMap<>();
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int) SIZE_THREAD);
            int size = personList.size();
            int pageSize = (int) (size % SIZE_THREAD == 0 ? size / SIZE_THREAD : size / SIZE_THREAD + 1);
            for (int i = 0; i < SIZE_THREAD; i++) {
                int from = i * pageSize;
                int to;
                if (i == (SIZE_THREAD - 1)) {
                    to = size;
                } else {
                    to = from + pageSize;
                }
                List<Person> subPerson = personList.subList(from, to);
                RequestHandle requestHandler = new RequestHandle(subPerson, from, to);
                requestHandler.dbName = dbName;
                requestHandler.pageToPercent = pageToPercent;
                executor.execute(requestHandler);
            }

            executor.shutdown();
            while (!executor.isTerminated()) {
                // Chờ xử lý hết các request còn chờ trong Queue ...
            }
        }

        System.out.println("\nPerson done!");
        System.out.println(System.currentTimeMillis() - startTime);
    }

    private void syncOnePerson(String dbName, Person person) throws JsonProcessingException, ParseException {

        Map<String, Object> personRequest = convert(dbName, person);
        String id = dbName + "_" + person.getId();
        String jsonBody = new Gson().toJson(personRequest);
        qldsRestTemplate.putForObject(url + "/person/_doc/" + id, jsonBody, String.class);
    }

    private Map<String, Object> convert(String dbName, Person person) throws JsonProcessingException, ParseException {
        HouseHoldRequest houseHoldRequest = houseHoldService.searchAndHouseHold(String.valueOf(person.getHouseHoldId()), person.getRegionId());
        UnitCategoryRequest commune = person.getRegionId() != null ? unitCategoryService.searchAndUnitCategory(person.getRegionId()) : null;
        UnitCategoryRequest district = commune != null ? unitCategoryService.searchAndUnitCategory(commune.parent) : null;
        Person mother = this.read(dbName, person.getMotherId());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(person, Map.class);
        map.put("dateOfBirth", person.getDateOfBirth() != null ? StringUtils.convertDateToLong(person.getDateOfBirth(), "dd/MM/yyyy") : null);
        map.put("dateOfBirthMother", mother != null ? StringUtils.convertDateToLong(mother.getDateOfBirth(), "dd/MM/yyyy") : null);
        map.put("timeCreated", person.getTimeCreated() != null ? person.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", person.getTimeLastUpdated() != null ? person.getTimeLastUpdated().getTime() : null);
        map.put("startDate", person.getStartDate() != null ? person.getStartDate().getTime() : null);
        map.put("endDate", person.getEndDate() != null ? person.getEndDate().getTime() : null);
        map.put("addressId", houseHoldRequest != null ? houseHoldRequest.getAddressId() : null);
        map.put("districtId", commune != null ? commune.parent : "");
        map.put("provinceId", district != null ? district.parent : "");
        map.put("fullName", person.getLastName() + " " + person.getFirstName());
        return map;
    }

    private class RequestHandle implements Runnable {
        int from, to;
        List<Person> peopleList;
        String dbName;
        Map<Integer, String> pageToPercent;

        public RequestHandle(List<Person> people, int from, int to) {
            this.peopleList = people;
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            System.out.println(String.format("Start Person synchronization progress from %d to %d", from, to));
            System.out.println("Person syncing...");
            BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
            peopleList.forEach(person -> {
                String id = dbName + "_" + person.getId();
                try {
                    Map<String, Object> request = convert(dbName, person);
                    IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                            .source(request, XContentType.JSON);
                    bulkProcessor.add(indexRequest);
                } catch (JsonProcessingException | ParseException e) {
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
            System.out.printf("\rPerson completed: from %d to %d", from, to);
        }
    }


}
