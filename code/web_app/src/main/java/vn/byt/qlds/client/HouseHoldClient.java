package vn.byt.qlds.client;

import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.address.AddressResponse;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.house.HouseHoldRequest;
import vn.byt.qlds.model._province.house.HouseHoldResponse;
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
public class HouseHoldClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    AddressClient addressClient;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointProvince;

    public HouseHoldClient(@Value("${apiEndpointProvince}") String apiEndpointCommon) {
        this.apiEndpointProvince = apiEndpointCommon + "/house-hold";
    }

    public Optional<HouseHoldResponse> createHouseHold(String db, long userId, HouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        HouseHold houseHold = new HouseHold(request);
        houseHold.setIsDeleted(false);
        houseHold.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setUserCreated(userId);
        houseHold.setUserLastUpdated(userId);
        HouseHold result = tenantRestTemplate.postForObject(db, this.apiEndpointProvince, houseHold, HouseHold.class);
        if (result != null) {
            /*tạo thành công house hold update số hộ address */
            Address address = addressClient
                    .getAddress(db, result.getAddressId())
                    .orElse(new AddressResponse())
                    .address;
            if (address != null) {
                Map<String, Object> requestCounter = new HashMap<>();
                requestCounter.put("addressId", result.getAddressId());
                Integer countHouseHold = count(requestCounter);
                address.setNotes(String.valueOf(countHouseHold));
                addressClient.updateAddress(db, userId, address);
            }
        }
        return Optional.ofNullable(toResponseService.transfer(result));
    }


    public HouseHold createHouseHold(String db, long userId, HouseHoldRequest request, boolean isTransfer) {
        long currentTime = System.currentTimeMillis();
        HouseHold houseHold = new HouseHold(request);
        houseHold.setIsDeleted(false);
        houseHold.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setUserCreated(userId);
        houseHold.setUserLastUpdated(userId);
        HouseHold result = tenantRestTemplate.postForObject(db, this.apiEndpointProvince, houseHold, HouseHold.class);
        if (result != null) {
            /*tạo thành công house hold update số hộ address */
            Address address = addressClient
                    .getAddress(db, result.getAddressId())
                    .orElse(new AddressResponse())
                    .address;
            if (address != null) {
                Map<String, Object> requestCounter = new HashMap<>();
                requestCounter.put("addressId", result.getAddressId());
                Integer countHouseHold = count(requestCounter);
                address.setNotes(String.valueOf(countHouseHold));
                addressClient.updateAddress(db, userId, address);
            }
        }
        return result;
    }

    /*dùng trong việc chuyển hộ sang 1 vùng mới*/
    public HouseHold createHouseHold(String db, long userId, HouseHold houseHold) {
        long currentTime = System.currentTimeMillis();
        houseHold.setIsDeleted(false);
        houseHold.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setUserCreated(userId);
        houseHold.setUserLastUpdated(userId);
        HouseHold result = tenantRestTemplate.postForObject(db, this.apiEndpointProvince, houseHold, HouseHold.class);
        if (result != null) {
            /*tạo thành công house hold update số hộ address */
            Address address = addressClient
                    .getAddress(db, result.getAddressId())
                    .orElse(new AddressResponse())
                    .address;
            if (address != null) {
                Map<String, Object> requestCounter = new HashMap<>();
                requestCounter.put("addressId", result.getAddressId());
                Integer countHouseHold = count(requestCounter);
                address.setNotes(String.valueOf(countHouseHold));
                addressClient.updateAddress(db, userId, address);
            }
        }
        return result;
    }

    public Optional<HouseHoldResponse> getHouseHold(String db, Integer id) {
        if (id == null) return Optional.empty();
        String url = this.apiEndpointProvince + "/" + id;
        HouseHold result = tenantRestTemplate.getForObject(db, url, HouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public HouseHold findHouseHoldById(String regionId, Integer id) {
        if (regionId == null || id == null) return null;
        Map<String, Object> request = new HashMap<>();
        request.put("regionId", regionId);
        request.put("houseHoldId", id);
        List<HouseHold> houseHolds = getAll(request);
        if (houseHolds != null && !houseHolds.isEmpty()) return houseHolds.get(0);
        else return null;
    }

    public Optional<HouseHoldResponse> updateHouseHold(String db, long userId, HouseHold houseHold, HouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + houseHold.getHouseHoldId();
        houseHold.createFromRequest(request);
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setUserLastUpdated(userId);
        HouseHold result = tenantRestTemplate.putForObject(db, url, houseHold, HouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public HouseHold updateHouseHold(String db, long userId, HouseHold houseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + houseHold.getHouseHoldId();
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        houseHold.setUserLastUpdated(userId);
        return tenantRestTemplate.putForObject(db, url, houseHold, HouseHold.class);
    }

    public boolean deleteHouseHold(String db, long userId, HouseHold houseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + houseHold.getHouseHoldId();
        houseHold.setIsDeleted(true);
        houseHold.setUserLastUpdated(userId);
        houseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        HouseHold result = tenantRestTemplate.putForObject(db, url, houseHold, HouseHold.class);
        if (result != null) {
            /*tạo thành công house hold update số hộ address */
            Address address = addressClient
                    .getAddress(db, result.getAddressId())
                    .orElse(new AddressResponse())
                    .address;
            if (address != null) {
                Map<String, Object> requestCounter = new HashMap<>();
                requestCounter.put("addressId", result.getAddressId());
                Integer countHouseHold = count(requestCounter);
                address.setNotes(String.valueOf(countHouseHold));
                addressClient.updateAddress(db, result.getAddressId(), address);
            }
            return true;
        } else return false;
    }

    public int count(Map<String, Object> request) {
        String url = this.apiEndpointProvince + "/count";
        Integer count = tenantRestTemplate.postForObject(url, request, Integer.class);
        if (count != null) return count;
        return 0;
    }

    public List<HouseHoldResponse> getAllHouseHold(Map<String, Object> request) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointProvince + "/all";
        List<HouseHold> houseHolds = mapper.convertValue(
                tenantRestTemplate.postForObject(url, request, List.class),
                new TypeReference<List<HouseHold>>() {
                });
        return houseHolds
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<HouseHold> getAll(Map<String, Object> request) {
        String url = this.apiEndpointProvince + "/all";
        return tenantRestTemplate.postForObject(url, request, ConvertList.HouseHoldList.class);
    }

    public PageResponse<HouseHoldResponse> getPage(Map<String, Object> pageRequest) {
        String url = this.apiEndpointProvince + "/search-page";
        PageResponse<HouseHold> response = tenantRestTemplate.postForObject(url, pageRequest, PageResponse.class);
        ObjectMapper mapper = new ObjectMapper();
        List<HouseHold> houseHolds = mapper.convertValue(response.getList(), new TypeReference<List<HouseHold>>() {
        });
        List<HouseHoldResponse> houseHoldResponses = houseHolds
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<HouseHoldResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(houseHoldResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }

}
