package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.address.AddressRequest;
import vn.byt.qlds.model._province.address.AddressResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AddressClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointProvince;

    public AddressClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointProvince = apiEndpointProvince + "/address";
    }

    public Optional<AddressResponse> createAddress(String db, long userId, AddressRequest request) {
        long currentTime = System.currentTimeMillis();
        Address address = new Address(request);
        address.setIsDeleted(false);
        address.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        address.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        address.setUserCreated(userId);
        address.setUserLastUpdated(userId);
        Address result = tenantRestTemplate.postForObject(db, this.apiEndpointProvince, address, Address.class);
        return Optional.ofNullable(toResponseService.transfer(db, result));
    }

    public Address createAddress(String db, long userId, Address address) {
        long currentTime = System.currentTimeMillis();
        address.setIsDeleted(false);
        address.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        address.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        address.setUserCreated(userId);
        address.setUserLastUpdated(userId);
        return tenantRestTemplate.postForObject(db, this.apiEndpointProvince, address, Address.class);
    }

    public Optional<AddressResponse> getAddress(String db, Integer id) {
        if (id == null) return Optional.empty();
        String url = this.apiEndpointProvince + "/" + id;
        Address result = tenantRestTemplate.getForObject(db, url, Address.class);
        return Optional.ofNullable(toResponseService.transfer(db, result));
    }

    public Optional<Address> findAddressById(String regionId, Integer id) {
        if (regionId == null || id == null) return Optional.empty();
        Map<String, Object> request = new HashMap<>();
        request.put("regionId", regionId);
        request.put("id", id);
        List<Address> addresses = getAll(request);
        if (addresses!=null && !addresses.isEmpty()){
            return Optional.ofNullable(addresses.get(0));
        }
        return Optional.empty();
    }

    public Optional<AddressResponse> updateAddress(String db, long userId, Address address, AddressRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + address.getId();
        address.createFromRequest(request);
        address.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        address.setUserLastUpdated(userId);
        Address result = tenantRestTemplate.putForObject(db, url, address, Address.class);
        return Optional.ofNullable(toResponseService.transfer(db, result));
    }

    public AddressResponse updateAddress(String db, long userId, Address address) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + address.getId();
        address.setUserCreated(address.getUserCreated());
        address.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        address.setUserLastUpdated(userId);
        Address result = tenantRestTemplate.putForObject(db, url, address, Address.class);
        return toResponseService.transfer(db, result);
    }

    public boolean deleteAddress(String db, long userId, Address address) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + address.getId();
        address.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        address.setUserLastUpdated(userId);
        address.setIsDeleted(true);
        Address result = tenantRestTemplate.putForObject(db, url, address, Address.class);
        return result != null;
    }

    public List<AddressResponse> getAll(String db, Map<String, Object> query) {
        String url = this.apiEndpointProvince + "/all";
        List<Address> addresses = tenantRestTemplate.postForObject(db, url, query, ConvertList.AddressList.class);
        return addresses.stream().map(address -> toResponseService.transfer(db, address)).collect(Collectors.toList());
    }

    public List<Address> getAll(Map<String, Object> query) {
        String url = this.apiEndpointProvince + "/all";
        return qldsRestTemplate.postForObject(url, query, ConvertList.AddressList.class);
    }

    public PageResponse<AddressResponse> getPage(String db, Map<String, Object> query) {
        String url = this.apiEndpointProvince + "/" + "search-page";
        PageResponse<Address> addressPages = tenantRestTemplate.postForObject(db, url, query, PageResponse.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Address> addressList = mapper.convertValue(addressPages.getList(), new TypeReference<List<Address>>() {
        });
        List<AddressResponse> list = addressList.stream().map(address -> toResponseService.transfer(db, address)).collect(Collectors.toList());
        PageResponse<AddressResponse> response = new PageResponse<>();
        response.setPage(addressPages.getPage());
        response.setTotal(addressPages.getTotal());
        response.setList(list);
        return response;
    }
}
