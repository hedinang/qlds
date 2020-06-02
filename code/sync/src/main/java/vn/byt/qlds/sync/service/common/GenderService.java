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
import vn.byt.qlds.sync.model.ES.request.GenderRequest;
import vn.byt.qlds.sync.model.ES.request.RelationshipRequest;
import vn.byt.qlds.sync.model.entity.Gender;
import vn.byt.qlds.sync.model.entity.Person;
import vn.byt.qlds.sync.model.entity.PersonHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenderService extends CrudService<Gender, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "Gender")
    public void receiveGender(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<Gender> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Gender>>() {
        }.getType());
        String id = syncOneGender(esMessageSync.data);
        System.out.println("Create new Gender with id = " + id);
    }

    public GenderRequest searchAndGender(Integer genderId) throws JsonProcessingException {
        if (genderId == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("genderId", genderId);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/gender/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), GenderRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void syncAllGender() {
        List<Gender> genderList = this.getAll("common");
        int i = 0;
        for (Gender gender : genderList) {
            syncOneGender(gender);
            i++;
            System.out.println("Gender synchronization progress : " + i * 100 / genderList.size() + "%");
        }
    }

    private String syncOneGender(Gender gender) {
        String jsonBody = new Gson().toJson(convert(gender));
        qldsRestTemplate.putForObject(url + "/gender/_doc/" + gender.getId(), jsonBody, String.class);
        return String.valueOf(gender.getId());
    }

    private GenderRequest convert(Gender gender) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(gender, Map.class);
        map.put("timeCreated", gender.getTimeCreated() != null ? gender.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", gender.getTimeLastUpdated() != null ? gender.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, GenderRequest.class);
    }

}
