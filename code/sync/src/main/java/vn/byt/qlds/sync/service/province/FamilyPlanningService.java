package vn.byt.qlds.sync.service.province;

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
import vn.byt.qlds.sync.core.utils.StringUtils;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.*;
import vn.byt.qlds.sync.model.entity.FamilyPlanning;
import vn.byt.qlds.sync.model.entity.FamilyPlanningHistory;
import vn.byt.qlds.sync.service.common.ContraceptiveCategoryService;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FamilyPlanningService extends CrudService<FamilyPlanning, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    AddressService addressService;
    @Autowired
    PersonService personService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    ContraceptiveCategoryService contraceptiveCategoryService;

    @Value("${urlES}")
    public String url;


    @RabbitListener(queues = "FamilyPlanning")
    public void receivePerson(Message message) throws ParseException {
        String json = new String(message.getBody());
        ESMessageSync<FamilyPlanning> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<FamilyPlanning>>() {
        }.getType());
        FamilyPlanning familyPlanning = esMessageSync.data;
        FamilyPlanRequest request = convert(familyPlanning);
        String id = familyPlanning.getRegionId() + familyPlanning.getPersonalId();
        qldsRestTemplate.putForObject(url + "/family-planning/_doc/" + id, new Gson().toJson(request), String.class);
        System.out.println("\nCreate new FamilyPlanning with id = " + id);
    }

    public void syncAllFamilyPlanningHistory(String dbName) throws ParseException {
        List<FamilyPlanning> familyPlannings = this.getAll(dbName);
        int i = 0;
        for (FamilyPlanning familyPlanning : familyPlannings) {
            FamilyPlanRequest request = convert(familyPlanning);
            String id = familyPlanning.getRegionId() + familyPlanning.getPersonalId();
            qldsRestTemplate.putForObject(url + "/family-planning/_doc/" + id, new Gson().toJson(request), String.class);
            i++;
            System.out.println("\rFamily-planning synchronization progress : " + i * 100 / familyPlannings.size() + "%");
        }
    }

    private FamilyPlanRequest convert(FamilyPlanning familyPlanning) throws ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(familyPlanning, Map.class);
        map.put("dateUpdate", familyPlanning.getDateUpdate() != null ? familyPlanning.getDateUpdate().getTime() : null);
        Long contraDate = StringUtils.convertDateToLong(familyPlanning.getContraDate(), "dd/MM/yyyy");
        map.put("contraDate", contraDate);
        map.put("timeCreated", familyPlanning.getTimeCreated() != null ? familyPlanning.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", familyPlanning.getTimeLastUpdated() != null ? familyPlanning.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, FamilyPlanRequest.class);
    }

}
