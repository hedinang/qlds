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
import vn.byt.qlds.sync.core.utils.StringUtils;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.GenderRequest;
import vn.byt.qlds.sync.model.ES.request.TechnicalCategoryRequest;
import vn.byt.qlds.sync.model.entity.Person;
import vn.byt.qlds.sync.model.entity.TechnicalCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TechnicalService extends CrudService<TechnicalCategory, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "TechnicalCategory")
    public void receiveEducation(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<TechnicalCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<TechnicalCategory>>() {
        }.getType());
        TechnicalCategoryRequest technicalCategoryRequest = convert(esMessageSync.data);
        qldsRestTemplate.putForObject(url + "/technical/_doc/" + esMessageSync.data.getId(), new Gson().toJson(technicalCategoryRequest), String.class);
        System.out.println("Create new TechnicalCategory with id = " + esMessageSync.data.getId());

    }

    public TechnicalCategoryRequest searchAndTechnicalCategory(Integer id) throws JsonProcessingException {
        if (id == null) return null;
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/technical/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        try {
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), TechnicalCategoryRequest.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void syncAllTechnicalCategory() {
        List<TechnicalCategory> technicalCategoryList = this.getAll("common");
        int i = 0;
        for (TechnicalCategory technicalCategory : technicalCategoryList) {
            TechnicalCategoryRequest technicalCategoryRequest = convert(technicalCategory);
            qldsRestTemplate.putForObject(url + "/technical/_doc/" + technicalCategory.getId(), new Gson().toJson(technicalCategoryRequest), String.class);
            i++;
            System.out.println("Technical synchronization progress : " + i * 100 / technicalCategoryList.size() + "%");
        }
    }

    public TechnicalCategoryRequest convert(TechnicalCategory technicalCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(technicalCategory, Map.class);
        map.put("timeCreated", technicalCategory.getTimeCreated() != null ? technicalCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", technicalCategory.getTimeLastUpdated() != null ? technicalCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, TechnicalCategoryRequest.class);
    }

}
