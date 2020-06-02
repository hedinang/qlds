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
import vn.byt.qlds.sync.model.ES.request.NationCategoryRequest;
import vn.byt.qlds.sync.model.entity.MaritalStatus;
import vn.byt.qlds.sync.model.entity.NationCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NationCategoryService extends CrudService<NationCategory, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    public NationCategoryRequest searchAndNationCategory(Integer id) throws JsonProcessingException {
        if (id == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/nation-category/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), NationCategoryRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "NationCategory")
    public void receiveNationCategory(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<NationCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<NationCategory>>() {
        }.getType());
        NationCategory nationCategory = esMessageSync.data;
        NationCategoryRequest nationCategoryRequest = convert(nationCategory);
        qldsRestTemplate.putForObject(url + "/nation-category/_doc/" + nationCategory.getId(), new Gson().toJson(nationCategoryRequest), String.class);
        System.out.println("Create new NationCategory with id = " + nationCategory.getId());

    }

    public void syncAllNationCategory() {
        List<NationCategory> nationCategoryList = this.getAll("common");
        int i = 0;
        for (NationCategory nationCategory : nationCategoryList) {
            NationCategoryRequest nationCategoryRequest = convert(nationCategory);
            qldsRestTemplate.putForObject(url + "/nation-category/_doc/" + nationCategory.getId(), new Gson().toJson(nationCategoryRequest), String.class);
            i++;
            System.out.println("\rNation-category synchronization progress : " + i * 100 / nationCategoryList.size() + "%");
        }
    }

    private NationCategoryRequest convert(NationCategory nationCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(nationCategory, Map.class);
        map.put("timeCreated", nationCategory.getTimeCreated() != null ? nationCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", nationCategory.getTimeLastUpdated() != null ? nationCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, NationCategoryRequest.class);
    }
}
