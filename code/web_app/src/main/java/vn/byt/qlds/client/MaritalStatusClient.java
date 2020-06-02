package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.marial.MaritalStatus;
import vn.byt.qlds.model.marial.MaritalStatusRequest;
import vn.byt.qlds.model.marial.MaritalStatusResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MaritalStatusClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public MaritalStatusClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/marital-status";
    }

    public Optional<MaritalStatusResponse> createMaritalStatus(long userId, MaritalStatusRequest request) {
        long currentTime = System.currentTimeMillis();
        MaritalStatus maritalStatus = new MaritalStatus(request);
        maritalStatus.setIsDeleted(false);
        maritalStatus.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        maritalStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        maritalStatus.setUserCreated(userId);
        maritalStatus.setUserLastUpdated(userId);
        MaritalStatus result = restTemplate.postForObject(this.apiEndpointCommon, maritalStatus, MaritalStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<MaritalStatusResponse> getMaritalStatus(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        MaritalStatus result = restTemplate.getForObject(url, MaritalStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<MaritalStatusResponse> updateMaritalStatus(long userId, MaritalStatus maritalStatus, MaritalStatusRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + maritalStatus.getMaritalCode();
        maritalStatus.createFromRequest(request);
        maritalStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        maritalStatus.setUserLastUpdated(userId);
        MaritalStatus result = restTemplate.putForObject(url, maritalStatus, MaritalStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteMaritalStatus(long userId, MaritalStatus maritalStatus) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + maritalStatus.getMaritalCode();
        maritalStatus.setIsDeleted(true);
        maritalStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        maritalStatus.setUserLastUpdated(userId);
        MaritalStatus result = restTemplate.putForObject(url, maritalStatus, MaritalStatus.class);
        return result != null;

    }

    public List<MaritalStatusResponse> getAllMaritalStatus(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<MaritalStatus> maritalStatuses = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<MaritalStatus>>() {
        });
        return maritalStatuses
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<MaritalStatus> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
       return restTemplate.postForObject(url, query, ConvertList.MaritalStatusList.class);
    }
    public PageResponse<MaritalStatusResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<MaritalStatus> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<MaritalStatus> maritalStatuses = mapper.convertValue(response.getList(), new TypeReference<List<MaritalStatus>>() {
        });
        List<MaritalStatusResponse> maritalStatusResponses = maritalStatuses
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<MaritalStatusResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(maritalStatusResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
