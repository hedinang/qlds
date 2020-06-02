package vn.byt.qlds.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.es.ElasticSearchService;
import vn.byt.qlds.core.sql.CrudService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.Collaborator;
import vn.byt.qlds.model.response.PageResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CollaboratorService extends CrudService<Collaborator, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "collaborator";

    ObjectMapper objectMapper = new ObjectMapper();

    public Collaborator findOne(Integer id) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("id", id);
        List<Collaborator> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return objectMapper.convertValue(esResponse.get(0), Collaborator.class);
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

    private Collaborator convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("isActive", tmp.get("active"));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        tmp.remove("districtId");
        tmp.remove("provinceId");
        tmp.remove("active");
        tmp.remove("fullName");
        return objectMapper.convertValue(tmp, Collaborator.class);
    }
}
