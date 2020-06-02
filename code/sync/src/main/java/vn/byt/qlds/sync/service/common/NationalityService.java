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
import vn.byt.qlds.sync.model.ES.request.NationalityRequest;
import vn.byt.qlds.sync.model.entity.AreaCategory;
import vn.byt.qlds.sync.model.entity.Nationality;

import java.util.List;
import java.util.Map;

@Service
public class NationalityService extends CrudService<Nationality, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "Nationality")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<Nationality> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Nationality>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new Nationality with id = " + esMessageSync.data.getNationalityCode());
    }

    public void syncOne(Nationality nationality) {
        NationalityRequest request = convert(nationality);
        qldsRestTemplate.putForObject(url + "/nationality/_doc/" + nationality.getNationalityCode(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<Nationality> nationalities = this.getAll(dbName);
        for (Nationality nationality : nationalities) {
            syncOne(nationality);
            System.out.println("Sync Nationality id = " + nationality.getNationalityCode());
        }
        System.out.println("Sync Nationality Done!");
    }

    private NationalityRequest convert(Nationality nationality) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(nationality, Map.class);
        map.put("timeCreated", nationality.getTimeCreated() != null ? nationality.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", nationality.getTimeLastUpdated() != null ? nationality.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, NationalityRequest.class);
    }
}
