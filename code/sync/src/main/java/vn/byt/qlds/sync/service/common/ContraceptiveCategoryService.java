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
import vn.byt.qlds.sync.model.ES.request.ContraceptiveCategoryRequest;
import vn.byt.qlds.sync.model.ES.request.FamilyPlanningHistoryRequest;
import vn.byt.qlds.sync.model.entity.Address;
import vn.byt.qlds.sync.model.entity.ContraceptiveCategory;
import vn.byt.qlds.sync.model.entity.FamilyPlanningHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ContraceptiveCategoryService extends CrudService<ContraceptiveCategory, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;

    @Value("${urlES}")
    public String url;


    public ContraceptiveCategoryRequest searchAndContraceptiveCategory(int id) throws JsonProcessingException {
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/contraceptive-category/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), ContraceptiveCategoryRequest.class);
    }

    @RabbitListener(queues = "ContraceptiveCategory")
    public void receiveContraceptiveCategory(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<ContraceptiveCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<ContraceptiveCategory>>() {
        }.getType());
        ContraceptiveCategory contraceptiveCategory = esMessageSync.data;
        ContraceptiveCategoryRequest request = convert(contraceptiveCategory);
        qldsRestTemplate.putForObject(url + "/contraceptive-category/_doc/" + contraceptiveCategory.getId(), new Gson().toJson(request), String.class);
        System.out.println("Create new ContraceptiveCategory with id = " + contraceptiveCategory.getId());

    }

    public void syncAllContraceptiveCategory() {
        List<ContraceptiveCategory> contraceptiveCategoryList = this.getAll("common");
        int i = 0;
        for (ContraceptiveCategory contraceptiveCategory : contraceptiveCategoryList) {
            ContraceptiveCategoryRequest request = convert(contraceptiveCategory);
            qldsRestTemplate.putForObject(url + "/contraceptive-category/_doc/" + contraceptiveCategory.getId(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("\rContraceptive-category synchronization progress : " + i * 100 / contraceptiveCategoryList.size() + "%");
        }
    }

    private ContraceptiveCategoryRequest convert(ContraceptiveCategory contraceptiveCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(contraceptiveCategory, Map.class);
        map.put("timeCreated", contraceptiveCategory.getTimeCreated() != null ? contraceptiveCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", contraceptiveCategory.getTimeLastUpdated() != null ? contraceptiveCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, ContraceptiveCategoryRequest.class);
    }
}
