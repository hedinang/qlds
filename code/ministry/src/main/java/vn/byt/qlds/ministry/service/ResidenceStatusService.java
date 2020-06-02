package vn.byt.qlds.ministry.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.configuration.QldsRestTemplate;
import vn.byt.qlds.ministry.core.es.ElasticSearchService;
import vn.byt.qlds.ministry.core.es.ElasticSearchUtils;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.core.utils.StringUtils;
import vn.byt.qlds.ministry.model.ResidenceStatus;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ResidenceStatusService extends CrudService<ResidenceStatus, Integer> {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "residence";

    @Value("${urlES}")
    protected String url;
    JsonNode jsonNode;
    ObjectMapper objectMapper = new ObjectMapper();

    public ResidenceStatus read(Integer id) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("residenceCode", id));
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        searchSourceBuilder.query(boolQueryBuilder);
        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/residence/_search", searchSourceBuilder.toString(), String.class));
        JsonNode result = jsonNode.findPath("_source");
        ResidenceStatus residenceStatus = new ResidenceStatus();
        residenceStatus.setResidenceCode(result.findPath("residenceCode").intValue());
        residenceStatus.setResidenceCodeOld(result.findPath("residenceCodeOld").textValue());
        residenceStatus.setResidenceName(result.findPath("residenceName").textValue());
        residenceStatus.setIsDeleted(result.findPath("isDeleted").booleanValue());
        residenceStatus.setIsActive(result.findPath("isActive").booleanValue());
        return residenceStatus;
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

    private ResidenceStatus convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return objectMapper.convertValue(tmp, ResidenceStatus.class);
    }
}