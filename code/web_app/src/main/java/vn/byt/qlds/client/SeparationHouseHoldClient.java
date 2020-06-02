package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.house.HouseHoldRequest;
import vn.byt.qlds.model._province.person.Person;
import vn.byt.qlds.model._province.person.PersonHistoryRequest;
import vn.byt.qlds.model._province.person.PersonResponse;
import vn.byt.qlds.model.separation_house.SeparationHouseHold;
import vn.byt.qlds.model.separation_house.SeparationHouseHoldRequest;
import vn.byt.qlds.model.separation_house.SeparationHouseHoldResponse;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.service.UnitCategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static vn.byt.qlds.core.utils.Config.*;

@Component
public class SeparationHouseHoldClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
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
    PersonClient personClient;
    @Autowired
    PersonHistoryClient personHistoryClient;
    @Autowired
    UnitCategoryService unitCategoryService;

    public SeparationHouseHoldClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/separation-house-hold";
    }

    public Optional<SeparationHouseHoldResponse> create(long userId, SeparationHouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        SeparationHouseHold separationHouseHold = new SeparationHouseHold(request);
        separationHouseHold.setStatus(PENDING);
        separationHouseHold.setIsDeleted(false);
        separationHouseHold.setUserCreated(userId);
        separationHouseHold.setUserLastUpdated(userId);
        separationHouseHold.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        separationHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        SeparationHouseHold result = qldsRestTemplate
                .postForObject(this.apiEndpointCommon, separationHouseHold, SeparationHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<SeparationHouseHoldResponse> findById(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        SeparationHouseHold result = qldsRestTemplate.getForObject(url, SeparationHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<SeparationHouseHold> findById(Integer id, boolean isTransfer) {
        String url = this.apiEndpointCommon + "/" + id;
        SeparationHouseHold result = qldsRestTemplate.getForObject(url, SeparationHouseHold.class);
        return Optional.ofNullable(result);
    }

    public Optional<SeparationHouseHoldResponse> update(long userId, SeparationHouseHold separationHouseHold, SeparationHouseHoldRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + separationHouseHold.getId();
        separationHouseHold.createFromRequest(request);
        separationHouseHold.setUserLastUpdated(userId);
        separationHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        SeparationHouseHold result = qldsRestTemplate.putForObject(url, request, SeparationHouseHold.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean delete(long userId, SeparationHouseHold separationHouseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + separationHouseHold.getId();
        separationHouseHold.setIsDeleted(true);
        separationHouseHold.setUserLastUpdated(userId);
        separationHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        SeparationHouseHold result = qldsRestTemplate.putForObject(url, separationHouseHold, SeparationHouseHold.class);
        return result != null;
    }

    public List<SeparationHouseHoldResponse> getAll(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<SeparationHouseHold> separationHouseHoldList = mapper.convertValue(tenantRestTemplate.postForObject(url, query, List.class), new TypeReference<List<SeparationHouseHold>>() {
        });
        return separationHouseHoldList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<SeparationHouseHoldResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<SeparationHouseHold> response = tenantRestTemplate.postForObject(url, query, PageResponse.class);
        List<SeparationHouseHold> separationHouseHoldList = mapper.convertValue(response.getList(), new TypeReference<List<SeparationHouseHold>>() {
        });
        List<SeparationHouseHoldResponse> separationHouseHoldResponse = separationHouseHoldList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<SeparationHouseHoldResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(separationHouseHoldResponse);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }

    @SneakyThrows
    public boolean acceptedOutSite(String db, long userId, SeparationHouseHold separationHouseHold) {
        /*TH1: chuyển người dân sang 1 hộ có sẵn, hộ này nằm trong phạm vi hoặc ngoài phạm vi.
        trong phạm vi tức là cùng address, từ cấp xã đổ đi có thể cùng db hoặc khác db
        * TH2: chuyển người dân sang hộ chưa có sẵn tạo hộ mới=>  */
        String provinceOfRegionOld = unitCategoryService
                .getProvinceByRegionCode(separationHouseHold.getRegionIdOld())
                .getName();
        String dbOld = StringUtils.convertNameProvinceToDbName(provinceOfRegionOld);

        HouseHold houseHold;
        Person person = personClient.findPersonById(
                separationHouseHold.getRegionIdOld(),
                separationHouseHold.getPersonId());
        if (person == null) return false;

        if (separationHouseHold.getHouseHoldIdNew() == null) {
            /*tạo hộ mới*/
            HouseHoldRequest houseHoldRequest = new HouseHoldRequest(
                    separationHouseHold.getHouseHoldCodeNew(),
                    separationHouseHold.getHouseholdNumber(),
                    separationHouseHold.getAddressIdNew(),
                    separationHouseHold.getRegionIdNew(),
                    separationHouseHold.getIsBigHouseHold(),
                    separationHouseHold.getStartDate(),
                    null,
                    null
            );
            houseHold = houseHoldClient.createHouseHold(db, userId, houseHoldRequest, false);

        } else {
            /*khác null chuyển người dân sang hộ mới đã hồ tại*/
            /*check house hold*/
            houseHold = houseHoldClient.findHouseHoldById(separationHouseHold.getRegionIdNew(), separationHouseHold.getHouseHoldIdNew());
        }

        if (dbOld.equals(db)) {
            if (houseHold != null) {
                /*update person */
                person.setHouseHoldId(houseHold.getHouseHoldId());
                person.setRegionId(separationHouseHold.getRegionIdNew());
                Person resultUpdatePerson = personClient
                        .updatePerson(db, userId, person, String.valueOf(CHUYEN_DEN));
                return resultUpdatePerson != null;
            } else return false;
        } else {
            if (houseHold != null) {
                person.setHouseHoldId(houseHold.getHouseHoldId());
                person.setRegionId(separationHouseHold.getRegionIdNew());
                Person resultCreatePerson = personClient
                        .createPerson(db, userId, person, String.valueOf(CHUYEN_DI))
                        .orElse(new PersonResponse()).getPerson();
                if (resultCreatePerson != null)
                    /*tạo lịch sử người dân*/ {
                    PersonHistoryRequest historyRequest = new PersonHistoryRequest();
                    historyRequest.changeTypeCode = String.valueOf(CHUYEN_DI);
                    historyRequest.personalId = person.getPersonalId();
                    historyRequest.regionId = person.getRegionId();
                    historyRequest.destination = resultCreatePerson.getRegionId();
                    wait(1000);
                    personHistoryClient.createPersonHistory(db, userId, historyRequest);
                }
                /*xóa người dân cũ*/
                personClient.deletePerson(dbOld, userId, person);
                return resultCreatePerson != null;
            } else return false;
        }
    }


    public boolean accepted(String db, long userId, SeparationHouseHold separationHouseHold) {
        boolean resultTransfer = acceptedOutSite(db, userId, separationHouseHold);
        if (resultTransfer) {
            long currentTime = System.currentTimeMillis();
            String url = this.apiEndpointCommon + "/" + separationHouseHold.getId();
            separationHouseHold.setStatus(Config.ACCEPTED);
            separationHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            separationHouseHold.setUserLastUpdated(userId);
            TransferHouseHold unit = qldsRestTemplate.putForObject(url, separationHouseHold, TransferHouseHold.class);
            return unit != null;
        } else return false;

    }

    public boolean rejected(long userId, SeparationHouseHold separationHouseHold) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + separationHouseHold.getId();
        separationHouseHold.setStatus(Config.REJECTED);
        separationHouseHold.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        separationHouseHold.setUserLastUpdated(userId);
        TransferHouseHold result = qldsRestTemplate.putForObject(url, separationHouseHold, TransferHouseHold.class);
        return result != null;
    }

}
