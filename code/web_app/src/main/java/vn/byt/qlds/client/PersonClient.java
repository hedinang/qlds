package vn.byt.qlds.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.person.*;
import vn.byt.qlds.model.search.response.CPersonResponse;
import vn.byt.qlds.model.unit.UnitCategory;
import vn.byt.qlds.model.unit.UnitCategoryResponse;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PersonClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    PersonHistoryClient personHistoryClient;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointProvince;
    @Value("${urlES}")
    public String url;
    @Autowired
    UnitCategoryClient unitCategoryClient;
    public PersonClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointProvince = apiEndpointProvince + "/person";
    }

    public Optional<PersonResponse> createPerson(String db, long userId, PersonRequest request) {
        long currentTime = System.currentTimeMillis();
        Person person = new Person(request);
        person.setIsDeleted(false);
        person.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        person.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        person.setUserCreated(userId);
        person.setUserLastUpdated(userId);
        Person result = tenantRestTemplate.postForObject(this.apiEndpointProvince, person, Person.class);
        PersonHistory personHistory = createPersonHistory(db, userId, request.changeTypeCode, result);
        if (result != null && personHistory != null) {
            return Optional.ofNullable(toResponseService.transfer(result));
        } else {
            return Optional.empty();
        }
    }

    /*dùng khi chuyển hộ*/
    public Optional<PersonResponse> createPerson(String db, long userId, Person person, String changeTypeCode) {
        long currentTime = System.currentTimeMillis();
        person.setIsDeleted(false);
        person.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        person.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        person.setUserCreated(userId);
        person.setUserLastUpdated(userId);
        Person result = tenantRestTemplate.postForObject(this.apiEndpointProvince, person, Person.class);
        if (result != null) {
            return Optional.ofNullable(toResponseService.transfer(result));
        } else {
            return Optional.empty();
        }
    }

    public Optional<PersonResponse> getPerson(String db, Integer id) {
        String url = this.apiEndpointProvince + "/" + id;
        Person result = tenantRestTemplate.getForObject(db, url, Person.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Person findPersonById(String regionId, Integer id) {
        if (regionId == null || id == null) {
            return null;
        }
        Map<String, Object> request = new HashMap<>();
        request.put("personalId", id);
        request.put("regionId", regionId);
        List<Person> peoples = getAll(request);

        if (peoples != null && !peoples.isEmpty()) return peoples.get(0);
        else return null;
    }

    public Optional<PersonResponse> updatePerson(String db, long userId, Person person, PersonRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + person.getPersonalId();
        person.createFromRequest(request);
        person.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        person.setUserLastUpdated(userId);
        Person result = tenantRestTemplate.putForObject(db, url, person, Person.class);
        PersonHistory personHistory = createPersonHistory(db, userId, request.changeTypeCode, result);
        if (result != null && personHistory != null)
            return Optional.ofNullable(toResponseService.transfer(result));
        return Optional.empty();
    }

    public Person updatePerson(String db, long userId, Person person, String changeTypeCode) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + person.getPersonalId();
        person.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        person.setUserLastUpdated(userId);
        Person result = tenantRestTemplate.putForObject(db, url, person, Person.class);
        PersonHistory personHistory = createPersonHistory(db, userId, changeTypeCode, result);
        if (result != null && personHistory != null)
            return result;
        return null;
    }

    public boolean deletePerson(String db, long userId, Person person) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + person.getPersonalId();
        person.setIsDeleted(true);
        person.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        person.setUserLastUpdated(userId);
        Person unit = tenantRestTemplate.putForObject(db, url, person, Person.class);
        return unit != null;
    }

    private PersonHistory createPersonHistory(String db, long userId, String changeTypeCode, Person person) {
        /*tạo person thành công hoặc update */
        if (person != null) {
            PersonHistoryRequest historyRequest = new PersonHistoryRequest();
            historyRequest.changeTypeCode = changeTypeCode;
            historyRequest.personalId = person.getPersonalId();
            historyRequest.regionId = person.getRegionId();
            historyRequest.destination = person.getRegionId();
            return personHistoryClient.createPersonHistory(db, userId, historyRequest);
        }
        return null;
    }

    public List<PersonResponse> getAllPerson(Map<String, Object> bodyRequest) {
        String url = this.apiEndpointProvince + "/all";
        List<Person> people = qldsRestTemplate.postForObject(url, bodyRequest, ConvertList.PersonList.class);
        if (people == null) people = new ArrayList<>();
        return people
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<Person> getAll(Map<String, Object> bodyRequest) {
        String url = this.apiEndpointProvince + "/all";
        return tenantRestTemplate.postForObject(url, bodyRequest, ConvertList.PersonList.class);
    }

    public PageResponse<PersonResponse> getPage(Map<String, Object> bodyRequest) {
        String url = this.apiEndpointProvince + "/" + "search-page";
        PageResponse<Person> personPages = tenantRestTemplate.postForObject(url, bodyRequest, ConvertList.PagePersonList.class);
        List<PersonResponse> personResponses = personPages
                .getList()
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<PersonResponse> result = new PageResponse<>();
        result.setPage(personPages.getPage());
        result.setTotal(personPages.getTotal());
        result.setList(personResponses);
        result.setLimit(personPages.getLimit());
        return result;
    }


    public CPersonResponse countTotalPerson(String regionId) throws JsonProcessingException, ParseException {
        UnitCategory unitCategory = unitCategoryClient.getUnitCategoryByCode(regionId)
                .orElse(new UnitCategoryResponse())
                .unitCategory;
        int levelUnit = unitCategory.getLevels();
        String keyRegion = "regionId";
        switch (levelUnit) {
            case 3: {
                keyRegion = "regionId";
                break;
            }
            case 2: {
                keyRegion = "districtId";
                break;
            }
            case 1: {
                keyRegion = "provinceId";
                break;
            }
        }
        /*query counter male*/
        BoolQueryBuilder boolMale = QueryBuilders.boolQuery();
        boolMale.must(QueryBuilders.matchQuery(keyRegion, regionId));
        boolMale.must(QueryBuilders.matchQuery("sexId", Config.MALE));
        boolMale.must(QueryBuilders.matchQuery("isDeleted", false));
        /*query counter female*/
        BoolQueryBuilder boolFemale = QueryBuilders.boolQuery();
        boolFemale.must(QueryBuilders.matchQuery(keyRegion, regionId));
        boolFemale.must(QueryBuilders.matchQuery("sexId", Config.FEMALE));
        boolFemale.must(QueryBuilders.matchQuery("isDeleted", false));
        /*query counter children are born 6 months prior to today tính trung bình 180 ngày*/
        LocalDate localDate = LocalDate.now();
        String dateString = localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + localDate.getYear();
        long endTime = StringUtils.convertDateToLong(dateString, "dd/MM/yyyy");
        long startTime = endTime - 180 * 24 * 60 * 60 * 1000L;// tính thời gian 180 ngày trước
        BoolQueryBuilder boolChildBirth = QueryBuilders.boolQuery();
        boolChildBirth.must(QueryBuilders.matchQuery(keyRegion, regionId));
        boolChildBirth.must(QueryBuilders.matchQuery("isDeleted", false));
        boolChildBirth.must(QueryBuilders.rangeQuery("dateOfBirth").gte(startTime));
        boolChildBirth.must(QueryBuilders.rangeQuery("dateOfBirth").lte(endTime));

        String unitName = unitCategory.getName();
        int totalMale = counterPersonByQuery(boolMale);
        int totalFemale = counterPersonByQuery(boolFemale);
        int totalPerson = totalFemale + totalMale;
        int totalChild = counterPersonByQuery(boolChildBirth);

        CPersonResponse cPersonResponse = new CPersonResponse();
        cPersonResponse.setRegionName(unitName);
        cPersonResponse.setCountMale(totalMale);
        cPersonResponse.setCountFemale(totalFemale);
        cPersonResponse.setCountPerson(totalPerson);
        cPersonResponse.setCountChild(totalChild);
        return cPersonResponse;
    }

    private int counterPersonByQuery(BoolQueryBuilder boolQueryBuilder) throws JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person/_count",
                searchSourceBuilder.query(boolQueryBuilder).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("count").intValue();
    }
}
