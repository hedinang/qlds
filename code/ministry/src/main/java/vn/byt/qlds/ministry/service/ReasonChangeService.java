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
import vn.byt.qlds.ministry.model.ReasonChange;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReasonChangeService extends CrudService<ReasonChange, String> {

    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    protected String url;
    private final static String index = "reason-change";
    JsonNode jsonNode;
    ObjectMapper objectMapper = new ObjectMapper();

    public ReasonChange read(Integer id) throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryBuilder.must(QueryBuilders.matchQuery("id", id));
        searchSourceBuilder.query(boolQueryBuilder);
        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/reason-change/_search", searchSourceBuilder.toString(), String.class));
        JsonNode result = jsonNode.findPath("_source");
        ReasonChange reasonChange = new ReasonChange();
        reasonChange.setId(result.findPath("id").intValue());
        reasonChange.setChangeTypeCode(result.findPath("changeTypeCode").textValue());
        reasonChange.setChangeCodeOld(result.findPath("changeCodeOld").textValue());
        reasonChange.setChangeTypeName(result.findPath("changeTypeName").textValue());
        reasonChange.setIsActive(result.findPath("isActive").booleanValue());
        reasonChange.setIsDeleted(result.findPath("isDeleted").booleanValue());
        return reasonChange;
    }

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PageResponse response = elasticSearchService.findPaging(index, request);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private ReasonChange convert(Object item) {
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return mapper.convertValue(tmp, ReasonChange.class);
    }
}
