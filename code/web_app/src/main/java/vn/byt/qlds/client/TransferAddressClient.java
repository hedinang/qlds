package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model.transfer.TransferAddress;
import vn.byt.qlds.model.transfer.TransferAddressRequest;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.service.UnitCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TransferAddressClient {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    AddressClient addressClient;
    private String apiEndpointCommon;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    HouseHoldClient houseHoldClient;

    public TransferAddressClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/transfer-address";
    }

    public Optional<TransferAddress> create(long userId, TransferAddressRequest request) {
        long currentTime = System.currentTimeMillis();
        TransferAddress transferAddress = new TransferAddress(request);
        transferAddress.setStatus(Config.PENDING);
        transferAddress.setIsDeleted(false);
        transferAddress.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        transferAddress.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferAddress.setUserCreated(userId);
        transferAddress.setUserLastUpdated(userId);
        TransferAddress result = qldsRestTemplate.postForObject(
                this.apiEndpointCommon,
                transferAddress,
                TransferAddress.class);
        return Optional.ofNullable(result);
    }

    public Optional<TransferAddress> findById(String id) {
        String apiEndpoint = this.apiEndpointCommon + "/" + id;
        TransferAddress result = qldsRestTemplate.getForObject(apiEndpoint, TransferAddress.class);
        return Optional.ofNullable(result);
    }

    public Optional<TransferAddress> update(long userId, TransferAddress transferAddress, TransferAddressRequest request) {
        long currentTime = System.currentTimeMillis();
        transferAddress.creatFromRequest(request);
        String apiEndpoint = this.apiEndpointCommon + "/" + transferAddress.getId();
        transferAddress.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        transferAddress.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferAddress.setUserLastUpdated(userId);
        TransferAddress result = qldsRestTemplate.putForObject(apiEndpoint, transferAddress, TransferAddress.class);
        return Optional.ofNullable(result);
    }

    public List<TransferAddress> getAll(Map<String, Object> query) {
        return qldsRestTemplate.postForObject(this.apiEndpointCommon + "/all", query, List.class);
    }

    public PageResponse<TransferAddress> getPage(Map<String, Object> query) {
        return qldsRestTemplate.postForObject(this.apiEndpointCommon + "/search-page", query, PageResponse.class);
    }

    public boolean accepted(String db, long userId, TransferAddress transferAddress) {
        boolean resultTransfer = acceptTransferAddressOutSite(db, userId, transferAddress);
        if (resultTransfer) {
            long currentTime = System.currentTimeMillis();
            String url = this.apiEndpointCommon + "/" + transferAddress.getId();
            transferAddress.setStatus(Config.ACCEPTED);
            transferAddress.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            transferAddress.setUserLastUpdated(userId);
            TransferHouseHold unit = qldsRestTemplate.putForObject(url, transferAddress, TransferHouseHold.class);
            return unit != null;
        } else return false;
    }

    public boolean rejected(long userId, TransferAddress transferAddress) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferAddress.getId();
        transferAddress.setStatus(Config.REJECTED);
        transferAddress.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferAddress.setUserLastUpdated(userId);
        TransferHouseHold unit = qldsRestTemplate.putForObject(url, transferAddress, TransferHouseHold.class);
        return unit != null;
    }

    private boolean acceptTransferAddressOutSite(String db, long userId, TransferAddress transferAddress) {
        // điều kiện đia chỉ cũ có tình/tp huyện/quận  = địa chỉ mới, và xã/phường khác xã/phường mới, tùy theo phạm vi
        String provinceOfRegionOld = unitCategoryService.getProvinceByRegionCode(transferAddress.getRegionOld()).getName();
        String dbOfAddressOld = StringUtils.convertNameProvinceToDbName(provinceOfRegionOld);
        int level = transferAddress.getLevel();
        Address oldAddress = addressClient
                .findAddressById(dbOfAddressOld, transferAddress.getAddressOld())
                .orElse(null);
        if (oldAddress == null) return false;

        /*chuyển xã khác cùng db (chuyển trong vùng)*/
        if (dbOfAddressOld.equals(db)) {
            oldAddress.setRegionId(transferAddress.getRegionNew());
            Address result = addressClient.updateAddress(db, userId, oldAddress).address;
            return result != null;
        } else {
            /*chuyển sang xã khác khác db, tạo trong db mới xóa trong db cũ*/
            oldAddress.setRegionId(transferAddress.getRegionNew());
            Address newAddress = addressClient.createAddress(db, userId, oldAddress);
            if (newAddress!=null){
                /*xóa db cũ */
                boolean resultDel = addressClient.deleteAddress(dbOfAddressOld, userId, oldAddress);
                /*xóa thành công thì chuyển hộ sang db mới người dân trong từng hộ sẽ thêm thủ công */
                /*danh sách hộ trong oldAddress*/
                if (resultDel){
                    List<HouseHold> houseHolds = getHouseHoldByAddressId(
                            transferAddress.getRegionOld(),
                            transferAddress.getAddressOld());
                    houseHolds.forEach(houseHold->{
                        houseHold.setAddressId(newAddress.getId());
                        houseHoldClient.createHouseHold(db,userId, houseHold);
                    });
                    houseHolds.forEach(houseHold -> houseHoldClient.deleteHouseHold(dbOfAddressOld, userId, houseHold));
                    return true;
                }else return false;
            }else return false;
        }
    }

    private List<HouseHold> getHouseHoldByAddressId(String regionId, Integer addressId){
        Map<String, Object> queryHouseHold = new HashMap<>();
        queryHouseHold.put("regionId", regionId);
        queryHouseHold.put("addressId", addressId);
        return houseHoldClient.getAll(queryHouseHold);
    }
}
