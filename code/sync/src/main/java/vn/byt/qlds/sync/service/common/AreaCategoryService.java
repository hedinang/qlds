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
import vn.byt.qlds.sync.model.ES.request.AreaRequest;
import vn.byt.qlds.sync.model.entity.AreaCategory;

import java.util.List;
import java.util.Map;

@Service
public class AreaCategoryService extends CrudService<AreaCategory, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "AreaCategory")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<AreaCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<AreaCategory>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new Area with id = " + esMessageSync.data.getId());
    }

    public void syncOne(AreaCategory areaCategory) {
        AreaRequest request = convert(areaCategory);
        qldsRestTemplate.putForObject(url + "/area-category/_doc/" + areaCategory.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<AreaCategory> areaCategories = this.getAll(dbName);
        for (AreaCategory areaCategory : areaCategories) {
            syncOne(areaCategory);
            System.out.println("Sync area id = " + areaCategory.getId());
        }
        System.out.println("Sync Area Done!");
    }

    private AreaRequest convert(AreaCategory areaCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(areaCategory, Map.class);
        map.put("timeCreated", areaCategory.getTimeCreated() != null ? areaCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", areaCategory.getTimeLastUpdated() != null ? areaCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, AreaRequest.class);
    }
}
