package vn.byt.qlds.sync.service.province;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.byt.qlds.sync.configuration.QldsRestTemplate;
import vn.byt.qlds.sync.core.ES.BulkServices;
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.CollaboratorRequest;
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.Collaborator;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class CollaboratorService extends CrudService<Collaborator, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Value("${urlES}")
    public String url;
    @Autowired
    BulkServices bulkServices;
    private static final String INDEX = "collaborator";
    private static final String TYPE = "_doc";

    @RabbitListener(queues = "Collaborator")
    public void receiveCollaborator(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<Collaborator> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Collaborator>>() {
        }.getType());
        String id = syncOneCollcaborator(esMessageSync.data);
        System.out.println("\nCreate new Collaborator with id = " + id);
    }

    public void syncAllCollaborator(String dbName) {
        List<Collaborator> collaboratorList = this.getAll(dbName);
        BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
        collaboratorList.forEach(collaborator -> {
            String id = dbName + "_" + collaborator.getId();
            Map<String, Object> personHealthyRequest = convert(collaborator);
            IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, id)
                    .source(personHealthyRequest, XContentType.JSON);
            bulkProcessor.add(indexRequest);
        });
        try {
            boolean terminated = bulkProcessor.awaitClose(60L, TimeUnit.SECONDS);
            bulkProcessor.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("\rCollaborator completed");
    }

    private String syncOneCollcaborator(Collaborator collaborator) {
        Map<String, Object> request = convert(collaborator);
        qldsRestTemplate.putForObject(url + "/collaborator/_doc/" + collaborator.getId(), new Gson().toJson(request), String.class);
        return String.valueOf(collaborator.getId());
    }

    private Map<String, Object> convert(Collaborator collaborator) {
        UnitCategoryRequest commune = unitCategoryService.searchAndUnitCategory(collaborator.getRegionId());
        UnitCategoryRequest district = commune != null ? unitCategoryService.searchAndUnitCategory(commune.parent) : null;

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(collaborator, Map.class);
        map.put("timeCreated", collaborator.getTimeCreated() != null ? collaborator.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", collaborator.getTimeLastUpdated() != null ? collaborator.getTimeLastUpdated().getTime() : null);
        map.put("hireDate", collaborator.getHireDate() != null ? collaborator.getHireDate().getTime() : null);
        map.put("endDate", collaborator.getEndDate() != null ? collaborator.getEndDate().getTime() : null);
        map.put("districtId", commune != null ? commune.parent : null);
        map.put("provinceId", district != null ? district.parent : null);
        map.put("active", collaborator.getIsActive());
        map.put("fullName", collaborator.getLastName() + " " + collaborator.getFirstName());
        return map;
    }
}
