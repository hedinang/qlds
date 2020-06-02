package vn.byt.qlds.ministry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.configuration.QldsRestTemplate;
import vn.byt.qlds.ministry.core.es.ElasticSearchService;
import vn.byt.qlds.ministry.core.es.ElasticSearchUtils;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.core.utils.StringUtils;
import vn.byt.qlds.ministry.model.UnitLevelCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UnitLevelCategoryService extends CrudService<UnitLevelCategory, Integer> {
    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "unit-level-category";

    @Value("${urlES}")
    protected String url;
    ObjectMapper objectMapper = new ObjectMapper();


    public UnitLevelCategory findOne(Integer id) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("id", id);
        List<UnitLevelCategory> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return esResponse.get(0);
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

    private UnitLevelCategory convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return objectMapper.convertValue(tmp, UnitLevelCategory.class);
    }
}
