package vn.byt.qlds.sync.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.byt.qlds.sync.configuration.QldsRestTemplate;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.UnitLevelCategoryRequest;
import vn.byt.qlds.sync.model.entity.UnitLevelCategory;

import java.util.List;
import java.util.Map;

@Service
public class UnitLevelCategoryService extends CrudService<UnitLevelCategory, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "UnitLevelCategory")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<UnitLevelCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<UnitLevelCategory>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new UnitLevelCategory with id = " + esMessageSync.data.getId());
    }

    public void syncOne(UnitLevelCategory unitLevelCategory) {
        UnitLevelCategoryRequest request = convert(unitLevelCategory);
        qldsRestTemplate.putForObject(url + "/unit-level-category/_doc/" + unitLevelCategory.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<UnitLevelCategory> unitLevelCategories = this.getAll(dbName);
        for (UnitLevelCategory unitLevelCategory : unitLevelCategories) {
            syncOne(unitLevelCategory);
            System.out.println("Sync UnitLevelCategory id = " + unitLevelCategory.getId());
        }
        System.out.println("Sync UnitLevelCategory Done!");
    }

    private UnitLevelCategoryRequest convert(UnitLevelCategory unitLevelCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(unitLevelCategory, Map.class);
        map.put("timeCreated", unitLevelCategory.getTimeCreated() != null ? unitLevelCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", unitLevelCategory.getTimeLastUpdated() != null ? unitLevelCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, UnitLevelCategoryRequest.class);
    }
}
