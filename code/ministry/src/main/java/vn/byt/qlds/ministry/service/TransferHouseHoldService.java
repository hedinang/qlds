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
import vn.byt.qlds.ministry.model.TransferHouseHold;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TransferHouseHoldService extends CrudService<TransferHouseHold, Integer> {

    @Autowired
    ElasticSearchUtils elasticSearchUtils;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    protected String url;
    private final static String index = "transfer-house-hold";
    ObjectMapper objectMapper = new ObjectMapper();


    public TransferHouseHold findOne(Integer id) throws IOException {
        Map<String, Object> query = new HashMap<>();
        query.put("id", id);
        List<TransferHouseHold> esResponse = findAll(query);
        if (esResponse.isEmpty()) return null;
        else return objectMapper.convertValue(esResponse.get(0), TransferHouseHold.class);
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

    private TransferHouseHold convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        Long startDate = (Long) tmp.get("startDate");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        tmp.put("startDate", StringUtils.convertLongToTimestamp(startDate));
        return objectMapper.convertValue(tmp, TransferHouseHold.class);
    }

}
