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
import vn.byt.qlds.sync.model.ES.request.RegionChangeTypeRequest;
import vn.byt.qlds.sync.model.entity.RegionChangeType;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegionChangeTypeService extends CrudService<RegionChangeType, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;

    @Value("${urlES}")
    public String url;


    public RegionChangeTypeRequest searchAndRegionChangeType(String id) throws JsonProcessingException {
        Map<String, Object> mCondition = new HashMap<>();
        mCondition.put("changeTypeCode", id);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/region-change-type/_search", elasticSearchService.searchAndBuilder(mCondition).toString(), String.class));
        return new Gson().fromJson(String.valueOf(jsonNode.path("hits").path("hits").get(0).path("_source")), RegionChangeTypeRequest.class);
    }

    @RabbitListener(queues = "RegionChangeType")
    public void receiveRegionChangeType(Message message) throws JsonProcessingException, ParseException {
        String json = new String(message.getBody());
        ESMessageSync<RegionChangeType> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<RegionChangeType>>() {
        }.getType());

        RegionChangeType regionChangeType = esMessageSync.data;
        RegionChangeTypeRequest request = convert(regionChangeType);
        qldsRestTemplate.putForObject(url + "/region-change-type/_doc/" + regionChangeType.getChangeTypeCode(), new Gson().toJson(request), String.class);
        System.out.println("Create new RegionChangeType with id = " + regionChangeType.getChangeTypeCode());

    }

    public void syncAllRegionChangeType() {
        List<RegionChangeType> regionChangeTypeList = this.getAll("common");
        int i = 0;
        for (RegionChangeType regionChangeType : regionChangeTypeList) {
            RegionChangeTypeRequest request = convert(regionChangeType);
            qldsRestTemplate.putForObject(url + "/region-change-type/_doc/" + regionChangeType.getChangeTypeCode(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("Region-change-type synchronization progress : " + i * 100 / regionChangeTypeList.size() + "%");
        }
    }

    private RegionChangeTypeRequest convert(RegionChangeType regionChangeType) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(regionChangeType, Map.class);
        map.put("timeCreated", regionChangeType.getTimeCreated() != null ? regionChangeType.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", regionChangeType.getTimeLastUpdated() != null ? regionChangeType.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, RegionChangeTypeRequest.class);
    }
}
