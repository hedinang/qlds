package vn.byt.qlds.sync.service.common;

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
import vn.byt.qlds.sync.model.ES.request.PermissionRequest;
import vn.byt.qlds.sync.model.entity.PermissionCategory;

import java.util.List;
import java.util.Map;

@Component
public class PermissionService extends CrudService<PermissionCategory, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "PermissionCategory")
    public void receiveGender(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<PermissionCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<PermissionCategory>>() {
        }.getType());
        String id = syncOnePermission(esMessageSync.data);
        System.out.println("Create new Permission with id = " + id);
    }

    public void syncAll() {
        List<PermissionCategory> permissionCategories = this.getAll("common");
        int i = 0;
        for (PermissionCategory gender : permissionCategories) {
            syncOnePermission(gender);
            i++;
            System.out.println("PermissionCategory synchronization progress : " + i * 100 / permissionCategories.size() + "%");
        }
    }

    private String syncOnePermission(PermissionCategory permissionCategory) {

        String jsonBody = new Gson().toJson(convert(permissionCategory));
        qldsRestTemplate.putForObject(url + "/permission/_doc/" + permissionCategory.getId(), jsonBody, String.class);
        return String.valueOf(permissionCategory.getId());
    }

    private PermissionRequest convert(PermissionCategory gender) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(gender, Map.class);
        map.put("timeCreated", gender.getTimeCreated() != null ? gender.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", gender.getTimeLastUpdated() != null ? gender.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, PermissionRequest.class);
    }

}
