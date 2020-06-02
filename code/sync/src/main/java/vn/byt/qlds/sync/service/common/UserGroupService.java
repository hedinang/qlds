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
import vn.byt.qlds.sync.model.ES.request.UserGroupCategoryRequest;
import vn.byt.qlds.sync.model.entity.UserGroupCategory;

import java.util.List;
import java.util.Map;

@Service
public class UserGroupService extends CrudService<UserGroupCategory, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "UserGroupCategory")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<UserGroupCategory> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<UserGroupCategory>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new UserGroupCategory with id = " + esMessageSync.data.getId());
    }

    public void syncOne(UserGroupCategory userGroupCategory) {
        UserGroupCategoryRequest request = convert(userGroupCategory);
        qldsRestTemplate.putForObject(url + "/user-group-category/_doc/" + userGroupCategory.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<UserGroupCategory> userGroupCategories = this.getAll(dbName);
        for (UserGroupCategory userGroupCategory : userGroupCategories) {
            syncOne(userGroupCategory);
            System.out.println("Sync UserGroupCategory id = " + userGroupCategory.getId());
        }
        System.out.println("Sync UserGroupCategory Done!");
    }

    private UserGroupCategoryRequest convert(UserGroupCategory userGroupCategory) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(userGroupCategory, Map.class);
        map.put("timeCreated", userGroupCategory.getTimeCreated() != null ? userGroupCategory.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", userGroupCategory.getTimeLastUpdated() != null ? userGroupCategory.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, UserGroupCategoryRequest.class);
    }
}
