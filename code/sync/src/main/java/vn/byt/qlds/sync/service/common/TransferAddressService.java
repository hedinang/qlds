
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
import vn.byt.qlds.sync.core.ES.ElasticSearchService;
import vn.byt.qlds.sync.core.sql.CrudService;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.model.ES.request.TransferAddressRequest;
import vn.byt.qlds.sync.model.entity.TransferAddress;

import java.util.List;
import java.util.Map;

@Service
public class TransferAddressService extends CrudService<TransferAddress, Integer> {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    @Value("${urlES}")
    public String url;

    @RabbitListener(queues = "TransferAddress")
    public void receiveTransferAddress(Message message) {
        String json = new String(message.getBody());
        ESMessageSync<TransferAddress> esMessageSync = new Gson().fromJson(json, new TypeToken<ESMessageSync<TransferAddress>>() {
        }.getType());
        TransferAddressRequest transferAddressRequest = convert(esMessageSync.data);
        qldsRestTemplate.putForObject(url + "/transfer-address/_doc/" + esMessageSync.data.getId(), new Gson().toJson(transferAddressRequest), String.class);
        System.out.println("Create new Transfer Address with id = " + esMessageSync.data.getId());

    }

    public void syncAll() {
        List<TransferAddress> transferAddresses = this.getAll("common");
        int i = 0;
        for (TransferAddress transferAddress : transferAddresses) {
            TransferAddressRequest request = convert(transferAddress);
            qldsRestTemplate.putForObject(url + "/transfer-address/_doc/" + transferAddress.getId(), new Gson().toJson(request), String.class);
            i++;
            System.out.println("Transfer Address synchronization progress : " + i * 100 / transferAddresses.size() + "%");
        }
        System.out.println("Transfer Address Sync Done!");
    }

    public TransferAddressRequest convert(TransferAddress transferAddress) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(transferAddress, Map.class);
        map.put("timeCreated", transferAddress.getTimeCreated() != null ? transferAddress.getTimeCreated().getTime() : null);
        map.put("timeLastUpdated", transferAddress.getTimeLastUpdated() != null ? transferAddress.getTimeLastUpdated().getTime() : null);
        return objectMapper.convertValue(map, TransferAddressRequest.class);
    }

}
