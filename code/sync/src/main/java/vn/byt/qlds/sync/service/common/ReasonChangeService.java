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
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.ReasonChangeRequest;
import vn.byt.qlds.sync.model.entity.ReasonChange;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReasonChangeService extends CrudService<ReasonChange, String> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;

    @Value("${urlES}")
    public String url;


    public ReasonChangeRequest searchAndReasonChange(String changeTypeCode) {
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("changeTypeCode", changeTypeCode);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/reason-change/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
            return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), ReasonChangeRequest.class);

        } catch (Exception e) {
            return null;
        }
    }

    @RabbitListener(queues = "ReasonChange")
    public void receiveReasonChange(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<ReasonChange> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<ReasonChange>>() {
        }.getType());

        ReasonChange reasonChange = esMessageSync.data;
        ReasonChangeRequest request = convert(reasonChange);
        qldsRestTemplate.putForObject(url + "/reason-change/_doc/" + reasonChange.getId(), new Gson().toJson(request), String.class);
        System.out.println("Create new ReasonChange with id = " + reasonChange.getChangeTypeCode());
    }


    public void syncAllReasonChange() {
        List<ReasonChange> reasonChangeList = this.getAll("common");
        int i = 0;
        for (ReasonChange reasonChange : reasonChangeList) {
            ReasonChangeRequest request = convert(reasonChange);
            qldsRestTemplate.putForObject(url + "/reason-change/_doc/" + reasonChange.getId(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("Reason-change synchronization progress : " + i * 100 / reasonChangeList.size() + "%");
        }
    }

    private ReasonChangeRequest convert(ReasonChange reasonChange) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(reasonChange, Map.class);
        map.put("timeCreated", reasonChange.getTimeCreated() != null ? reasonChange.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", reasonChange.getTimeLastUpdated() != null ? reasonChange.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, ReasonChangeRequest.class);
    }
}
