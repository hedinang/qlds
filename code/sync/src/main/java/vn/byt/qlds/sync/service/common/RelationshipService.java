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
import vn.byt.qlds.sync.model.ES.request.RelationshipRequest;
import vn.byt.qlds.sync.model.entity.Relationship;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RelationshipService extends CrudService<Relationship, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    public RelationshipRequest searchAndRelationship(Integer id) throws JsonProcessingException {
        if (id == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/relationship/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), RelationshipRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "Relationship")
    public void receiveRelationship(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<Relationship> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Relationship>>() {
        }.getType());

        Relationship relationship = esMessageSync.data;
        RelationshipRequest request = convert(relationship);
        qldsRestTemplate.putForObject(url + "/relationship/_doc/" + relationship.getId(), new Gson().toJson(request), String.class);
        System.out.println("Create new Relationship with id = " + relationship.getId());
    }

    public void syncAllRelationship() {
        List<Relationship> relationshipList = this.getAll("common");
        int i = 0;
        for (Relationship relationship : relationshipList) {
            RelationshipRequest request = convert(relationship);
            qldsRestTemplate.putForObject(url + "/relationship/_doc/" + relationship.getId(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("Relationship synchronization progress : " + i * 100 / relationshipList.size() + "%");
        }
    }

    private RelationshipRequest convert(Relationship relationship) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(relationship, Map.class);
        map.put("timeCreated", relationship.getTimeCreated() != null ? relationship.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", relationship.getTimeLastUpdated() != null ? relationship.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, RelationshipRequest.class);
    }

}
