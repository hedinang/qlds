package vn.byt.qlds.sync.service.province;

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
import vn.byt.qlds.sync.model.ES.request.OperationstatisticinforRequest;
import vn.byt.qlds.sync.model.entity.Operationstatisticinfor;

import java.util.List;
import java.util.Map;

@Component
public class OperationstatisticinforService extends CrudService<Operationstatisticinfor, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;

    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "Operationstatisticinfor")
    public void receiveOperationstatisticinfor(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<Operationstatisticinfor> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Operationstatisticinfor>>() {
        }.getType());
        Operationstatisticinfor ope = esMessageSync.data;
        OperationstatisticinforRequest request = convert(ope);
        String id = ope.getRegionId() + ope.getRegionId();
        qldsRestTemplate.putForObject(url + "/statistic/_doc/" + id, new Gson().toJson(request), String.class);
        System.out.println("Create new Operationstatisticinfor with id = " + id);

    }

    public void syncAllStatistic(String dbName) {
        List<Operationstatisticinfor> operationstatisticinforList = this.getAll(dbName);
        int i = 0;
        for (Operationstatisticinfor ope : operationstatisticinforList) {
            OperationstatisticinforRequest request = convert(ope);
            String id = ope.getRegionId() + ope.getRegionId();
            qldsRestTemplate.putForObject(url + "/statistic/_doc/" + id, new Gson().toJson(request), String.class);
            i++;
            System.out.println("Statistic synchronization progress : " + i * 100 / operationstatisticinforList.size() + "%");
        }
    }


    private OperationstatisticinforRequest convert(Operationstatisticinfor operationstatisticinfor) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(operationstatisticinfor, Map.class);
        map.put("timeCreated", operationstatisticinfor.getTimeCreated() != null ? operationstatisticinfor.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", operationstatisticinfor.getTimeLastUpdated() != null ? operationstatisticinfor.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, OperationstatisticinforRequest.class);
    }
}
