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
import vn.byt.qlds.sync.model.ES.request.EducationRequest;
import vn.byt.qlds.sync.model.entity.EducationCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EducationService extends CrudService<EducationCategory, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "EducationCategory")
    public void receiveEducation(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<EducationCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<EducationCategory>>() {
        }.getType());
        EducationCategory educationCategory = esMessageSync.data;
        EducationRequest request = convert(educationCategory);
        qldsRestTemplate.putForObject(url + "/education/_doc/" + educationCategory.getId(), new Gson().toJson(request), String.class);
        System.out.println("Create new EducationCategory with id = " + educationCategory.getId());
    }

    public EducationRequest searchAndEducation(Integer id) throws JsonProcessingException {
        if (id ==null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/education/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), EducationRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void syncAllEducation() {
        List<EducationCategory> educationCategoryList = this.getAll("common");
        int i = 0;
        for (EducationCategory educationCategory : educationCategoryList) {
            EducationRequest request = convert(educationCategory);
            qldsRestTemplate.putForObject(url + "/education/_doc/" + educationCategory.getId(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("\rEducation synchronization progress : " + i * 100 / educationCategoryList.size() + "%");
        }
    }

    private EducationRequest convert(EducationCategory collaborator) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(collaborator, Map.class);
        map.put("timeCreated", collaborator.getTimeCreated() != null ? collaborator.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", collaborator.getTimeLastUpdated() != null ? collaborator.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, EducationRequest.class);
    }
}
