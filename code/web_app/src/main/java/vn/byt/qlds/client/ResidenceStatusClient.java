package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.residence.ResidenceStatus;
import vn.byt.qlds.model.residence.ResidenceStatusRequest;
import vn.byt.qlds.model.residence.ResidenceStatusResponse;
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
public class ResidenceStatusClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public ResidenceStatusClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/residence-status";
    }

    public Optional<ResidenceStatusResponse> createResidenceStatus(long userId, ResidenceStatusRequest request) {
        long currentTime = System.currentTimeMillis();
        ResidenceStatus residenceStatus = new ResidenceStatus(request);
        residenceStatus.setIsDeleted(false);
        residenceStatus.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        residenceStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        residenceStatus.setUserCreated(userId);
        residenceStatus.setUserLastUpdated(userId);
        ResidenceStatus result = restTemplate.postForObject(this.apiEndpointCommon, residenceStatus, ResidenceStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<ResidenceStatusResponse> getResidenceStatus(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        ResidenceStatus result = restTemplate.getForObject(url, ResidenceStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<ResidenceStatusResponse> updateResidenceStatus(long userId, ResidenceStatus residenceStatus, ResidenceStatusRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + residenceStatus.getResidenceCode();
        residenceStatus.createFromRequest(request);
        residenceStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        residenceStatus.setUserLastUpdated(userId);
        ResidenceStatus result = restTemplate.putForObject(url, residenceStatus, ResidenceStatus.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteResidenceStatus(long userId, ResidenceStatus residenceStatus) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + residenceStatus.getResidenceCode();
        residenceStatus.setIsDeleted(true);
        residenceStatus.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        residenceStatus.setUserLastUpdated(userId);
        restTemplate.putForObject(url, residenceStatus, ResidenceStatus.class);
        return true;
    }

    public List<ResidenceStatusResponse> getAllResidenceStatus(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<ResidenceStatus> residenceStatuses = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<ResidenceStatus>>() {
        });
        return residenceStatuses
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<ResidenceStatus> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.ResidenceList.class);
    }

    public PageResponse<ResidenceStatusResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<ResidenceStatus> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<ResidenceStatus> residenceStatuses = mapper.convertValue(response.getList(), new TypeReference<List<ResidenceStatus>>() {
        });
        List<ResidenceStatusResponse> residenceStatusResponses = residenceStatuses
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<ResidenceStatusResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(residenceStatusResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
