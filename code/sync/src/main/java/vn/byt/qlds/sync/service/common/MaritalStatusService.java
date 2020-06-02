package vn.byt.qlds.sync.service.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.sync.configuration.QldsRestTemplate;
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.MaritalStatusRequest;
import vn.byt.qlds.sync.model.entity.MaritalStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MaritalStatusService extends CrudService<MaritalStatus, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "MaritalStatus")
    public void receiveMaritalStatus(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<MaritalStatus> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<MaritalStatus>>() {
        }.getType());
        MaritalStatus maritalStatus = esMessageSync.data;
        MaritalStatusRequest request = convert(maritalStatus);
        qldsRestTemplate.putForObject(url + "/marital-status/_doc/" + maritalStatus.getMaritalCode(), new Gson().toJson(request), String.class);
        System.out.println("Create new MaritalStatus with id = " + maritalStatus.getMaritalCode());

    }

    public MaritalStatusRequest searchAndMaritalStatus(Integer maritalStatusId) throws JsonProcessingException {
        if (maritalStatusId == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("maritalCode", maritalStatusId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/marital-status/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), MaritalStatusRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void syncAllMaritalStatus() {
        List<MaritalStatus> maritalStatusList = this.getAll("common");
        int i = 0;
        for (MaritalStatus maritalStatus : maritalStatusList) {
            MaritalStatusRequest request = convert(maritalStatus);
            qldsRestTemplate.putForObject(url + "/marital-status/_doc/" + maritalStatus.getMaritalCode(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("\rMarital-status synchronization progress : " + i * 100 / maritalStatusList.size() + "%");
        }
    }

    private MaritalStatusRequest convert(MaritalStatus maritalStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(maritalStatus, Map.class);
        map.put("timeCreated", maritalStatus.getTimeCreated() != null ? maritalStatus.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", maritalStatus.getTimeLastUpdated() != null ? maritalStatus.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, MaritalStatusRequest.class);
    }

}
