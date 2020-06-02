package vn.byt.qlds.ministry.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.configuration.QldsRestTemplate;
import vn.byt.qlds.ministry.core.es.ElasticSearchService;
import vn.byt.qlds.ministry.core.es.ElasticSearchUtils;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.core.utils.StringUtils;
import vn.byt.qlds.ministry.model.UnitCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UnitCategoryService extends CrudService<UnitCategory, Integer> {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate restTemplate;

    @Value("${urlES}")
    protected String url;
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "unit-category";
    JsonNode jsonNode;
    ObjectMapper objectMapper = new ObjectMapper();

    public UnitCategory findOneById(Integer id) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("id", id);
        List<UnitCategory> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return objectMapper.convertValue(esResponse.get(0), UnitCategory.class);
    }

    public UnitCategory findOneByCode(String ma) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("code", ma);
        List<UnitCategory> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return objectMapper.convertValue(esResponse.get(0), UnitCategory.class);
    }

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private UnitCategory convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return objectMapper.convertValue(tmp, UnitCategory.class);
    }

    public List<UnitCategory> getListProvincesCategory(int level, String code) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryBuilder.must(QueryBuilders.matchQuery("levels", level));
        boolQueryBuilder.must(QueryBuilders.wildcardQuery("code", code.substring(0, 2) + "*"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10000);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
        JsonNode list = jsonNode.findPath("hits").path("hits");
        List<UnitCategory> listUnitCategories = new ArrayList<>();
        for (JsonNode result : list) {
            UnitCategory unitCategory = new UnitCategory();
            unitCategory.setId(result.findPath("id").intValue());
            unitCategory.setArea(result.findPath("area").textValue());
            unitCategory.setBriefName(result.findPath("briefName").textValue());
            unitCategory.setIsActive(result.findPath("isActive").intValue());
            unitCategory.setLevels(result.findPath("levels").intValue());
            unitCategory.setCode(result.findPath("code").textValue());
            unitCategory.setParent(result.findPath("parent").textValue());
            unitCategory.setName(result.findPath("name").textValue());
            unitCategory.setNote(result.findPath("note").textValue());
            unitCategory.setZone(result.findPath("zone").textValue());
            unitCategory.setIsDeleted(result.findPath("isDelete").booleanValue());

            listUnitCategories.add(unitCategory);
        }
        return listUnitCategories;
    }

    public List<UnitCategory> getListDistrictsCategory(String code) throws IOException {
        // 2 la cap huyen
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryByCode = QueryBuilders.boolQuery();
        boolQueryByCode.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryByCode.must(QueryBuilders.matchQuery("code", code));
        searchSourceBuilder.query(boolQueryByCode);
        searchSourceBuilder.size(10000);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
        int levelByCode = jsonNode.findPath("levels").intValue();
        if (levelByCode == 1) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
            boolQueryBuilder.must(QueryBuilders.matchQuery("levels", 2));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("code", code.substring(0, 2) + "*"));
            searchSourceBuilder.query(boolQueryBuilder);
            jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
        } else {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
            boolQueryBuilder.must(QueryBuilders.matchQuery("levels", 2));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("code", code.substring(0, 6) + "*"));
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.size(10000);
            jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
        }
        JsonNode list = jsonNode.path("hits").path("hits");
        List<UnitCategory> listUnitCategories = new ArrayList<>();
        for (JsonNode result : list) {
            UnitCategory r = new UnitCategory();
            r.setId(result.findPath("id").intValue());
            r.setArea(result.findPath("area").textValue());
            r.setBriefName(result.findPath("briefName").textValue());
            r.setIsActive(result.findPath("isActive").intValue());
            r.setLevels(result.findPath("levels").intValue());
            r.setCode(result.findPath("code").textValue());
            r.setParent(result.findPath("parent").textValue());
            r.setName(result.findPath("name").textValue());
            r.setNote(result.findPath("note").textValue());
            r.setZone(result.findPath("zone").textValue());
            listUnitCategories.add(r);
        }
        return listUnitCategories;
    }

    public List<UnitCategory> getListCommunesCategory(String district, String code) throws IOException {
        // 3 la cap xa
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryByCode = QueryBuilders.boolQuery();
        boolQueryByCode.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryByCode.must(QueryBuilders.matchQuery("code", code));
        searchSourceBuilder.query(boolQueryByCode);
        searchSourceBuilder.size(10000);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
        int levelByCode = jsonNode.findPath("levels").intValue();
        List<UnitCategory> listUnitCategories = new ArrayList<>();
        if (levelByCode == 3) {
            UnitCategory r = new UnitCategory();
            r.setId(jsonNode.findPath("id").intValue());
            r.setArea(jsonNode.findPath("area").textValue());
            r.setBriefName(jsonNode.findPath("briefName").textValue());
            r.setIsActive(jsonNode.findPath("isActive").intValue());
            r.setLevels(jsonNode.findPath("levels").intValue());
            r.setCode(jsonNode.findPath("code").textValue());
            r.setParent(jsonNode.findPath("parent").textValue());
            r.setName(jsonNode.findPath("name").textValue());
            r.setNote(jsonNode.findPath("note").textValue());
            r.setZone(jsonNode.findPath("zone").textValue());
            r.setIsDeleted(jsonNode.findPath("isDeleted").booleanValue());

            listUnitCategories.add(r);
        } else {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("parent", district));
            searchSourceBuilder.size(10000);
            searchSourceBuilder.query(boolQueryBuilder);
            jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/unit-category/_search", searchSourceBuilder.toString(), String.class));
            JsonNode list = jsonNode.path("hits").path("hits");
            for (JsonNode result : list) {
                UnitCategory r = new UnitCategory();
                r.setId(result.findPath("id").intValue());
                r.setArea(result.findPath("area").textValue());
                r.setBriefName(result.findPath("briefName").textValue());
                r.setIsActive(result.findPath("isActive").intValue());
                r.setLevels(result.findPath("levels").intValue());
                r.setCode(result.findPath("code").textValue());
                r.setParent(result.findPath("parent").textValue());
                r.setName(result.findPath("name").textValue());
                r.setNote(result.findPath("note").textValue());
                r.setZone(result.findPath("zone").textValue());
                r.setIsDeleted(result.findPath("isDeleted").booleanValue());

                listUnitCategories.add(r);
            }
        }
        return listUnitCategories;
    }

    public List<UnitCategory> getListUnitCategoryForLevel(String idSession, int level, String regionId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        List<Criterion> listCriterion = new ArrayList<>();
        criteria.add(Restrictions.eq("isDeleted", false));
        switch (level) {
            case 1:
                criteria.add(Restrictions.eq("levels", 1));
                criteria.add(Restrictions.eq("parent", "0000000000"));
                break;
            case 2:
                criteria.add(Restrictions.eq("levels", 2));
                criteria.add(Restrictions.like("code", regionId.substring(0, 2), MatchMode.START));
                criteria.add(Restrictions.eq("parent", regionId));
                break;
            case 3:
                criteria.add(Restrictions.eq("levels", 3));
                criteria.add(Restrictions.like("code", regionId.substring(0, 6), MatchMode.START));
                criteria.add(Restrictions.eq("parent", regionId));
                break;
        }
        List<UnitCategory> list = criteria.list();
        session.close();
        return list;
    }

    public List<UnitCategory> getListUnitCategoryForParent(String idSession, String regionId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionParent = Restrictions.eq("parent", regionId);
        LogicalExpression andExp = Restrictions.and(criterionParent, criterionIsDeleted);
        criteria.add(andExp);
        List<UnitCategory> list = criteria.list();
        session.close();
        return list;
    }

}
