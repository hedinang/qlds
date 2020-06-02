package vn.byt.qlds.service;

import vn.byt.qlds.client.AddressClient;
import vn.byt.qlds.model._province.address.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressClient addressClient;
    @Autowired
    UnitCategoryService unitCategoryService;
    public String generateDetail(Integer addressId, String regionCode){
        String addressName = addressClient.findAddressById(regionCode, addressId).orElse(new Address()).getName();
        return addressName + ", " + unitCategoryService.generateFullName(regionCode);
    }

}
