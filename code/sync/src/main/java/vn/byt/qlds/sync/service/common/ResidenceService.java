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
import vn.byt.qlds.sync.model.ES.request.ResidenceRequest;
import vn.byt.qlds.sync.model.entity.Relationship;
import vn.byt.qlds.sync.model.entity.ResidenceStatus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResidenceService extends CrudService<ResidenceStatus, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    public ResidenceRequest searchAndResidence(Integer id) throws JsonProcessingException {
        if (id == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("residenceCode", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/residence/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), ResidenceRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "ResidenceStatus")
    public void receiveResidenceStatus(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<ResidenceStatus> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<ResidenceStatus>>() {
        }.getType());

        ResidenceStatus residenceStatus = esMessageSync.data;
        ResidenceRequest request = convert(residenceStatus);

        qldsRestTemplate.putForObject(url + "/residence/_doc/" + residenceStatus.getResidenceCode(), new Gson().toJson(request), String.class);
        System.out.println("Create new ResidenceStatus with id = " + residenceStatus.getResidenceCode());
    }

    public void syncAllResidence() {
        List<ResidenceStatus> residenceStatusList = this.getAll("common");
        int i = 0;
        for (ResidenceStatus residenceStatus : residenceStatusList) {
            ResidenceRequest request = convert(residenceStatus);
            qldsRestTemplate.putForObject(url + "/residence/_doc/" + residenceStatus.getResidenceCode(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("ResidenceStatus synchronization progress : " + i * 100 / residenceStatusList.size() + "%");
        }
    }

    private ResidenceRequest convert(ResidenceStatus residenceStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(residenceStatus, Map.class);
        map.put("timeCreated", residenceStatus.getTimeCreated() != null ? residenceStatus.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", residenceStatus.getTimeLastUpdated() != null ? residenceStatus.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, ResidenceRequest.class);
    }

}
