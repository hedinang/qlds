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
import vn.byt.qlds.ministry.model.AreaCategory;
import vn.byt.qlds.ministry.model.ContraceptiveCategory;
import vn.byt.qlds.ministry.model.DeadCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DeadCategoryService extends CrudService<DeadCategory, Integer> {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "dead-category";
    @Value("${urlES}")
    protected String url;

    ObjectMapper objectMapper = new ObjectMapper();

    public DeadCategory findOne(Integer id) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("id", id);
        List<DeadCategory> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return objectMapper.convertValue(esResponse.get(0), DeadCategory.class);
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

    private DeadCategory convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return objectMapper.convertValue(tmp, DeadCategory.class);
    }
}
