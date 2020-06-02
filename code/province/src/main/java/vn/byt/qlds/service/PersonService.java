package vn.byt.qlds.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.es.ConfigSource;
import vn.byt.qlds.core.es.ElasticSearchService;
import vn.byt.qlds.core.sql.CrudService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.Person;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersonService extends CrudService<Person, Integer> {
    @Autowired
    PersonHistoryService personHistoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ElasticSearchService elasticSearchService;
    private static final String index = "person";

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request, ConfigSource.exclude_source_person);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request, ConfigSource.exclude_source_person);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private LinkedHashMap<String, Object> convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long changeDate = (Long) tmp.get("changeDate");
        Long startDate = (Long) tmp.get("startDate");
        Long endDate = (Long) tmp.get("endDate");
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        String dateOfBirth = StringUtils.convertLongToDateString((Long) tmp.get("dateOfBirth"), "dd/MM/yyyy");
        tmp.put("dateOfBirth", dateOfBirth);
        tmp.put("changeDate", StringUtils.convertLongToTime(changeDate));
        tmp.put("startDate", StringUtils.convertLongToTime(startDate));
        tmp.put("endDate", StringUtils.convertLongToTime(endDate));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        tmp.remove("provinceId");
        tmp.remove("districtId");
        tmp.remove("addressId");
        tmp.remove("fullName");
        return tmp;
    }

}