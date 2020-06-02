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
import vn.byt.qlds.sync.model.ES.request.SeparationHouseHoldRequest;
import vn.byt.qlds.sync.model.ES.request.TransferHouseHoldRequest;
import vn.byt.qlds.sync.model.entity.SeparationHouseHold;
import vn.byt.qlds.sync.model.entity.TransferHouseHold;

import java.util.List;
import java.util.Map;

@Service
public class SeparationHHService extends CrudService<SeparationHouseHold, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "SeparationHouseHold")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<SeparationHouseHold> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<SeparationHouseHold>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new SeparationHouseHold with id = " + esMessageSync.data.getId());
    }

    public void syncOne(SeparationHouseHold transferHouseHold) {
        SeparationHouseHoldRequest request = convert(transferHouseHold);
        qldsRestTemplate.putForObject(url + "/separation-house-hold/_doc/" + transferHouseHold.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<SeparationHouseHold> separationHouseHolds = this.getAll(dbName);
        for (SeparationHouseHold separationHouseHold : separationHouseHolds) {
            syncOne(separationHouseHold);
            System.out.println("Sync SeparationHouseHold id = " + separationHouseHold.getId());
        }
        System.out.println("Sync SeparationHouseHold Done!");
    }

    private SeparationHouseHoldRequest convert(SeparationHouseHold separationHouseHold) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(separationHouseHold, Map.class);
        map.put("timeCreated", separationHouseHold.getTimeCreated() != null ? separationHouseHold.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", separationHouseHold.getTimeLastUpdated() != null ? separationHouseHold.getTimeLastUpdated().getTime() : null);
        map.put("startDate", separationHouseHold.getStartDate() != null ? separationHouseHold.getStartDate().getTime() : null);
        return objectMapper.convertValue(map, SeparationHouseHoldRequest.class);
    }
}
