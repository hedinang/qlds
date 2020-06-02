package vn.byt.qlds.services.address;

import org.springframework.stereotype.Component;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_to.AddressTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AddressToService extends CrudService<AddressTo, Integer> {
    public Map<String, Integer> getMappingToID(String idSession){
        Map<String, Integer> map = new HashMap<>();
        List<AddressTo> addressTos = getAll(idSession);
        addressTos.forEach(addressTo -> {
            String key = addressTo.getRegionId() + addressTo.getAddressId();
            map.put(key, addressTo.getId());
        });
        return map;
    }
}
