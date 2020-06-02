package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.model.transfer.TransferPerson;
import vn.byt.qlds.model.transfer.TransferPersonRequest;
import vn.byt.qlds.model.transfer.TransferPersonResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TransferPersonClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    @Autowired
    public TransferPersonClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/transfer-person";
    }

    public Optional<TransferPersonResponse> createTransferPerson(long userId, TransferPersonRequest request) {
        long currentTime = System.currentTimeMillis();
        TransferPerson transferPerson = new TransferPerson(request);
        transferPerson.setStatus(Config.PENDING);
        transferPerson.setIsDeleted(false);
        transferPerson.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        transferPerson.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferPerson.setUserCreated(userId);
        transferPerson.setUserLastUpdated(userId);
        TransferPerson result = tenantRestTemplate.postForObject(this.apiEndpointCommon, transferPerson, TransferPerson.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TransferPersonResponse> getTransferPerson(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        TransferPerson result = qldsRestTemplate.getForObject(url, TransferPerson.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TransferPersonResponse> updateTransferPerson(long userId, TransferPerson transferPerson, TransferPersonRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferPerson.getId();
        transferPerson.createFromRequest(request);
        transferPerson.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferPerson.setUserLastUpdated(userId);
        TransferPerson result = qldsRestTemplate.putForObject(url, request, TransferPerson.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteTransferPerson(long userId, TransferPerson transferPerson) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferPerson.getId();
        transferPerson.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferPerson.setUserLastUpdated(userId);
        transferPerson.setIsDeleted(true);
        TransferPerson result = qldsRestTemplate.putForObject(url, transferPerson, TransferPerson.class);
        return result != null;
    }

    public List<TransferPersonResponse> getAllTransferPerson() {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<TransferPerson> transferPeople = mapper.convertValue(qldsRestTemplate.getForObject(url, List.class), new TypeReference<List<TransferPerson>>() {
        });
        return transferPeople.stream().map(toResponseService::transfer).collect(Collectors.toList());
    }

    public PageResponse<TransferPersonResponse> getPage(PageRequest pageRequest) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<TransferPerson> transferPersonPages = tenantRestTemplate.postForObject(url, pageRequest, PageResponse.class);
        ObjectMapper mapper = new ObjectMapper();
        List<TransferPerson> transferPersonList = mapper.convertValue(transferPersonPages.getList(), new TypeReference<List<TransferPerson>>() {
        });
        List<TransferPersonResponse> list = transferPersonList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<TransferPersonResponse> response = new PageResponse<>();
        response.setPage(transferPersonPages.getPage());
        response.setList(list);
        response.setLimit(transferPersonPages.getLimit());
        return response;

    }

    public boolean accepted(String db, long userId, TransferPerson transferPerson) {





        return false;
    }

    public boolean rejected(long userId, TransferPerson transferPerson) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + transferPerson.getId();
        transferPerson.setStatus(Config.REJECTED);
        transferPerson.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        transferPerson.setUserLastUpdated(userId);
        TransferHouseHold result = qldsRestTemplate.putForObject(url, transferPerson, TransferHouseHold.class);
        return result != null;
    }

}
