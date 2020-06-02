package vn.byt.qlds.sync.service.province;

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
import vn.byt.qlds.sync.model.ES.request.UnitCategoryRequest;
import vn.byt.qlds.sync.model.entity.Address;
import vn.byt.qlds.sync.service.common.UnitCategoryService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AddressService extends CrudService<Address, Integer> {

    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    BulkServices bulkServices;
    @Value("${urlES}")
    public String url;
    private static final String INDEX = "address";
    private static final String TYPE = "_doc";
    @RabbitListener(queues = "Address")
    public void receiveAddress(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<Address> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<Address>>() {
        }.getType());
        String id = syncOneAddress(esMessageSync.getDbName(), esMessageSync.data);
        System.out.println("\nCreate new Address with id = " + id);

    }

    public void syncAllAddress(String dbName) {
        List<Address> addressList = this.getAll(dbName);
        BulkProcessor bulkProcessor = bulkServices.createBulkProcessor();
        addressList.forEach(address -> {
            String id = dbName + "_" + address.getId();
            Map<String, Object> personHealthyRequest = convert(address);
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
        System.out.print("\rAddress completed");
    }

    private String syncOneAddress(String db, Address address) {
        assert address != null;
        assert db != null;
        Map<String, Object> request = convert(address);
        String id = db +"_"+ address.getId();
        qldsRestTemplate.putForObject(url + "/address/_doc/" + id, new Gson().toJson(request), String.class);
        return String.valueOf(address.getId());
    }

    private Map<String, Object> convert(Address address) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(address, Map.class);
        UnitCategoryRequest commune = unitCategoryService.searchAndUnitCategory(address.getRegionId());
        UnitCategoryRequest district = commune != null ? unitCategoryService.searchAndUnitCategory(commune.parent) : null;
        map.put("timeCreated", address.getTimeCreated() != null ? address.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", address.getTimeLastUpdated() != null ? address.getTimeLastUpdated().getTime() : null);
        map.put("districtId", commune != null ? commune.parent : null);
        map.put("provinceId", district != null ? district.parent : null);
        return map;
    }
}
