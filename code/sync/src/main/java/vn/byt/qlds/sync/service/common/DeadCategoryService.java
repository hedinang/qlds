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
import vn.byt.qlds.sync.model.ES.request.DeadCategoryRequest;
import vn.byt.qlds.sync.model.entity.AreaCategory;
import vn.byt.qlds.sync.model.entity.DeadCategory;

import java.util.List;
import java.util.Map;

@Service
public class DeadCategoryService extends CrudService<DeadCategory, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "DeadCategory")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<DeadCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<DeadCategory>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new DeadCategory with id = " + esMessageSync.data.getId());
    }

    public void syncOne(DeadCategory deadCategory) {
        DeadCategoryRequest request = convert(deadCategory);
        qldsRestTemplate.putForObject(url + "/dead-category/_doc/" + deadCategory.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<DeadCategory> deadCategories = this.getAll(dbName);
        for (DeadCategory deadCategory : deadCategories) {
            syncOne(deadCategory);
            System.out.println("Sync DeadCategory id = " + deadCategory.getId());
        }
        System.out.println("Sync DeadCategory Done!");
    }

    private DeadCategoryRequest convert(DeadCategory deadCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(deadCategory, Map.class);
        map.put("timeCreated", deadCategory.getTimeCreated() != null ? deadCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", deadCategory.getTimeLastUpdated() != null ? deadCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, DeadCategoryRequest.class);
    }
}
