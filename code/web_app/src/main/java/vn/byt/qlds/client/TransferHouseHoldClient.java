package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.person.Person;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.model.transfer.TransferHouseHoldRequest;
import vn.byt.qlds.model.transfer.TransferHouseHoldResponse;
import vn.byt.qlds.service.AddressService;
import vn.byt.qlds.service.UnitCategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TransferHouseHoldClient {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    PersonClient personClient;
    private String apiEndpointCommon;
    @Value("${apiEndpointCommon}")
    private String apiCommon;
    @Value("${apiEndpointProvince}")
    private String apiProvince;
    @Autowired
    TransferToResponseService toResponseService;
    @Autowired
    HouseHoldClient houseHoldClient;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    AddressService addressService;

    public TransferHouseHoldClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/transfer-house-hold";
    }

    public Optional<TransferHouseHoldResponse> createTransferHouseHold(long userId, TransferHouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        TransferHouseHold transferHouseHold = new TransferHouseHold(request);
        String addressOldName = addressService.generateDetail(request.addressIdOld, request.regionIdOld);
        String addressNewName = addressService.generateDetail(request.addressIdNew, request.regionIdNew);
        transferHouseHold.setStatus(Config.PENDING);
        transferHouseHold.setIsDeleted(false);
        transferHouseHold.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        transferHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferHouseHold.setUserCreated(userId);
        transferHouseHold.setUserLastUpdated(userId);
        transferHouseHold.setAddressOld(addressOldName);
        transferHouseHold.setAddressNew(addressNewName);
        TransferHouseHold result = qldsRestTemplate.postForObject(this.apiEndpointCommon, transferHouseHold, TransferHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TransferHouseHoldResponse> getTransferHouseHold(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        TransferHouseHold result = qldsRestTemplate.getForObject(url, TransferHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TransferHouseHold> findOneById(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        TransferHouseHold result = qldsRestTemplate.getForObject(url, TransferHouseHold.class);
        return Optional.ofNullable(result);
    }

    public Optional<TransferHouseHoldResponse> updateTransferHouseHold(long userId, TransferHouseHold transferHouseHold, TransferHouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferHouseHold.getId();
        transferHouseHold.createFromRequest(request);
        transferHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferHouseHold.setUserLastUpdated(userId);
        TransferHouseHold result = qldsRestTemplate.putForObject(url, transferHouseHold, TransferHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteTransferHouseHold(long userId, TransferHouseHold transferHouseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferHouseHold.getId();
        transferHouseHold.setIsDeleted(true);
        transferHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferHouseHold.setUserLastUpdated(userId);
        TransferHouseHold unit = qldsRestTemplate.putForObject(url, transferHouseHold, TransferHouseHold.class);
        return unit != null;
    }

    public List<TransferHouseHoldResponse> getAllTransferHouseHold(@RequestBody Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        List<TransferHouseHold> transferHouseHolds = qldsRestTemplate.postForObject(url, query, ConvertList.TransferHouseHoldList.class);
        if (transferHouseHolds != null) {
            return transferHouseHolds
                    .stream()
                    .map(toResponseService::transfer)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public PageResponse<TransferHouseHoldResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<TransferHouseHold> transferHouseHoldPages = qldsRestTemplate.postForObject(url, query, PageResponse.class);
        PageResponse<TransferHouseHoldResponse> result = new PageResponse<>();
        ObjectMapper mapper = new ObjectMapper();
        List<TransferHouseHold> transferHouseHolds = mapper.convertValue(transferHouseHoldPages.getList(), new TypeReference<List<TransferHouseHold>>() {
        });
        List<TransferHouseHoldResponse> transferHouseHoldResponses = transferHouseHolds
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        result.setList(transferHouseHoldResponses);
        result.setLimit(transferHouseHoldPages.getLimit());
        result.setTotal(transferHouseHoldPages.getTotal());
        result.setPage(transferHouseHoldPages.getPage());
        return result;

    }

    /*chuyển trong xã update address_id*/
    /*chuyển ngoài xã cần đợi cho ctv xã đó chấp nhận*/
    private boolean acceptTransferHouseHoldOutSide(String db, long userId, TransferHouseHold transferHouseHold) {
        //1. kiểm tra xem ctv có quản lý region_id cũ hay không
        String provinceOfRegionOld = unitCategoryService.getProvinceByRegionCode(transferHouseHold.getRegionIdOld()).getName();
        String dbOfHouseHoldOld = StringUtils.convertNameProvinceToDbName(provinceOfRegionOld);
        /* nếu dbOfHouseHoldOld == db thì chuyển cùng 1 db nếu không chuyển khác db*/
        HouseHold oldHouseHold = houseHoldClient.findHouseHoldById(
                transferHouseHold.getRegionIdOld(),
                transferHouseHold.getHouseHoldId());
        if (dbOfHouseHoldOld.equals(db)) {
            // chuyển ngoài nhưng cùng huyện tỉnh
            if (oldHouseHold != null) {
                oldHouseHold.setAddressId(transferHouseHold.getAddressIdNew());
                oldHouseHold.setRegionId(transferHouseHold.getRegionIdNew());
                HouseHold result = houseHoldClient.updateHouseHold(db, userId, oldHouseHold);
                return result != null;
            } else {
                return false;
            }
        } else {
            // chuyển khác tỉnh, xóa trong tỉnh cũ, thêm trong tỉnh mới và personal
            if (oldHouseHold != null) {
                oldHouseHold.setAddressId(transferHouseHold.getAddressIdNew());
                oldHouseHold.setRegionId(transferHouseHold.getRegionIdNew());
                HouseHold newHouseHold = houseHoldClient.createHouseHold(db, userId, oldHouseHold);
                /*xóa house hold trong db cũ*/
                if (newHouseHold != null) {
                    houseHoldClient.deleteHouseHold(dbOfHouseHoldOld, userId, oldHouseHold);
                    /*thêm hộ danh sách người dân vào hộ*/
                    /*điều kiện có household_id = transfer*/
                    List<Person> personList = getPersonByHouseHoldId(
                            transferHouseHold.getRegionIdOld(),
                            transferHouseHold.getHouseHoldId());
                    /*thêm vào db mới*/
                    personList.forEach(person -> {
                        /*lý do thêm là do chuyển đến*/
                        person.setHouseHoldId(newHouseHold.getHouseHoldId());
                        personClient.createPerson(db, userId, person, String.valueOf(Config.CHUYEN_DEN));
                    });
                    /*xóa trong db cũ*/
                    personList.forEach(person -> {
                        personClient.deletePerson(dbOfHouseHoldOld, userId, person);
                    });
                    return true;
                } else return false;
            } else {
                return false;
            }
        }
    }

    private List<Person> getPersonByHouseHoldId(String regionId, Integer houseHoldId) {
        Map<String, Object> personQuery = new HashMap<>();
        personQuery.put("regionId", regionId);
        personQuery.put("houseHoldId", houseHoldId);
        return personClient.getAll(personQuery);
    }

    public boolean accepted(String db, long userId, TransferHouseHold transferHouseHold) {
        boolean resultTransfer = acceptTransferHouseHoldOutSide(db, userId, transferHouseHold);
        if (resultTransfer) {
            long currentTime = System.currentTimeMillis();
            String url = this.apiEndpointCommon + "/" + transferHouseHold.getId();
            transferHouseHold.setStatus(Config.ACCEPTED);
            transferHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            transferHouseHold.setUserLastUpdated(userId);
            TransferHouseHold unit = qldsRestTemplate.putForObject(url, transferHouseHold, TransferHouseHold.class);
            return unit != null;
        } else return false;
    }

    public boolean rejected(long userId, TransferHouseHold transferHouseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferHouseHold.getId();
        transferHouseHold.setStatus(Config.REJECTED);
        transferHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferHouseHold.setUserLastUpdated(userId);
        TransferHouseHold result = qldsRestTemplate.putForObject(url, transferHouseHold, TransferHouseHold.class);
        return result != null;
    }
}
