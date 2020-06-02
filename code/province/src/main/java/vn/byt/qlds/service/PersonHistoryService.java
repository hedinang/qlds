package vn.byt.qlds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.es.ConfigSource;
import vn.byt.qlds.core.es.ElasticSearchService;
import vn.byt.qlds.core.sql.CrudService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.PersonHistory;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersonHistoryService extends CrudService<PersonHistory, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    private static final String index = "person-history";

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request, ConfigSource.exclude_source_person_history);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request, ConfigSource.exclude_source_person_history);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private LinkedHashMap<String, Object> convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long dateUpdate = (Long) tmp.get("dateUpdate");
        Long changeDate = (Long) tmp.get("changeDate");
        Long dieDate = (Long) tmp.get("dieDate");
        Long goDate = (Long) tmp.get("goDate");
        Long comeDate = (Long) tmp.get("comeDate");
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");

        tmp.put("dateUpdate", StringUtils.convertLongToTime(dateUpdate));
        tmp.put("changeDate", StringUtils.convertLongToTime(changeDate));
        tmp.put("dieDate", StringUtils.convertLongToTime(dieDate));
        tmp.put("goDate", StringUtils.convertLongToTime(goDate));
        tmp.put("comeDate", StringUtils.convertLongToTime(comeDate));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        tmp.remove("updateInAge");
        return tmp;
    }
}
