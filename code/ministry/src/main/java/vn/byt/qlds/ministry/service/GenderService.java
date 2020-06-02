package vn.byt.qlds.ministry.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.byt.qlds.ministry.core.es.ElasticSearchUtils;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.model.EducationCategory;
import vn.byt.qlds.ministry.model.Gender;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenderService extends CrudService<Gender, Integer> implements vn.byt.qlds.ministry.core.es.CrudService<Gender, Integer> {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RestHighLevelClient client;
    @Value("${urlES}")
    protected String url;

    JsonNode jsonNode;
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Gender read(Integer id) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("id", id));
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        searchSourceBuilder.query(boolQueryBuilder);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/gender/_search", searchSourceBuilder.toString(), String.class));
        JsonNode result = jsonNode.findPath("_source");
        Gender gender = new Gender();
        gender.setId(result.findPath("id").intValue());
        gender.setName(result.findPath("name").textValue());
        gender.setIsDeleted(result.findPath("isDeleted").booleanValue());
        return gender;
    }

    @Override
    public PageResponse<Gender> getPage(Map<String, Object> query) throws IOException {
        Integer limit = 10;
        Integer page = 1;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query != null)
            for (String key : query.keySet()) {
                if (!key.equals("limit") && !key.equals("page") && !key.equals("es_key_like") && !key.equals("es_value_like"))
                    boolQueryBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));
                if (key.equals("limit"))
                    limit = (Integer) query.get(key);
                if (key.equals("page"))
                    page = (Integer) query.get(key);
                if (key.equals("es_key_like"))
                    boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery((String) query.get("es_key_like"), query.get("es_value_like")));

            }

        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from((page - 1) * limit);
        searchSourceBuilder.size(limit);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/gender/_search", searchSourceBuilder.toString(), String.class));
        JsonNode list = jsonNode.path("hits").path("hits");
        List<Gender> listGenders = new ArrayList<>();
        for (JsonNode result : list) {
            Gender gender = new Gender();
            gender.setId(result.findPath("id").intValue());
            gender.setName(result.findPath("name").textValue());
            gender.setIsDeleted(result.findPath("isDeleted").booleanValue());
            listGenders.add(gender);
        }
        Integer count = this.count(query);
        PageResponse<Gender> pageResponse = new PageResponse<>();
        pageResponse.setList(listGenders);
        pageResponse.setPage(page);
        pageResponse.setTotal(count);
        return pageResponse;
    }

    @Override
    public List<Gender> getAll(Map<String, Object> query) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query != null)
            for (String key : query.keySet())
                boolQueryBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(10000);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/gender/_search", searchSourceBuilder.toString(), String.class));
        JsonNode list = jsonNode.path("hits").path("hits");
        List<Gender> listGenders = new ArrayList<>();
        for (JsonNode result : list) {
            Gender gender = new Gender();
            gender.setId(result.findPath("id").intValue());
            gender.setName(result.findPath("name").textValue());
            gender.setIsDeleted(result.findPath("isDeleted").booleanValue());
            listGenders.add(gender);
        }
        return listGenders;
    }

    @Override
    public Integer count(Map<String, Object> query) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query != null)
            for (String key : query.keySet()) {
                if (!key.equals("limit") && !key.equals("page"))
                    boolQueryBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));
            }
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        searchSourceBuilder.query(boolQueryBuilder);
        jsonNode = objectMapper.readTree(restTemplate.postForObject(url + "/gender/_count", searchSourceBuilder.toString(), String.class));
        return jsonNode.path("hits").path("total").path("value").intValue();
    }
}
