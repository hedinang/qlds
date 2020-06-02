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
import vn.byt.qlds.sync.model.ES.request.TransferHouseHoldRequest;
import vn.byt.qlds.sync.model.entity.TransferHouseHold;

import java.util.List;
import java.util.Map;

@Service
public class TransferHouseHoldService extends CrudService<TransferHouseHold, Integer> {
    @Value("${urlES}")
    public String url;

    @Autowired
    QldsRestTemplate qldsRestTemplate;

    @RabbitListener(queues = "TransferHouseHold")
    public void rabbitListener(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<TransferHouseHold> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<TransferHouseHold>>() {
        }.getType());
        syncOne(esMessageSync.data);
        System.out.println("Create new TransferHouseHold with id = " + esMessageSync.data.getId());
    }

    public void syncOne(TransferHouseHold transferHouseHold) {
        TransferHouseHoldRequest request = convert(transferHouseHold);
        qldsRestTemplate.putForObject(url + "/transfer-house-hold/_doc/" + transferHouseHold.getId(), new Gson().toJson(request), String.class);
    }

    public void syncAll(String dbName) {
        List<TransferHouseHold> transferHouseHolds = this.getAll(dbName);
        for (TransferHouseHold transferHouseHold : transferHouseHolds) {
            syncOne(transferHouseHold);
            System.out.println("Sync TransferHouseHold id = " + transferHouseHold.getId());
        }
        System.out.println("Sync TransferHouseHold Done!");
    }

    private TransferHouseHoldRequest convert(TransferHouseHold transferHouseHold) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(transferHouseHold, Map.class);
        map.put("timeCreated", transferHouseHold.getTimeCreated() != null ? transferHouseHold.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", transferHouseHold.getTimeLastUpdated() != null ? transferHouseHold.getTimeLastUpdated().getTime() : null);
        map.put("startDate", transferHouseHold.getStartDate() != null ? transferHouseHold.getStartDate().getTime() : null);
        return objectMapper.convertValue(map, TransferHouseHoldRequest.class);
    }
}
