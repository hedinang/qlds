package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.reason_change.ReasonChange;
import vn.byt.qlds.model.reason_change.ReasonChangeRequest;
import vn.byt.qlds.model.reason_change.ReasonChangeResponse;
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
public class ReasonChangeClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public ReasonChangeClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/reason-change";
    }

    public Optional<ReasonChangeResponse> createReasonChange(long userId, ReasonChangeRequest request) {
        long currentTime = System.currentTimeMillis();
        ReasonChange reasonChange = new ReasonChange(request);
        reasonChange.setIsDeleted(false);
        reasonChange.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        reasonChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        reasonChange.setUserCreated(userId);
        reasonChange.setUserLastUpdated(userId);
        ReasonChange result = restTemplate.postForObject(this.apiEndpointCommon, reasonChange, ReasonChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<ReasonChangeResponse> getReasonChange(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        ReasonChange result = restTemplate.getForObject(url, ReasonChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public ReasonChange findReasonChangeByCode(String code) {
        Map<String, Object> request = new HashMap<>();
        request.put("changeTypeCode", code);
        List<ReasonChange> reasonChanges = getAll(request);
        if (reasonChanges != null && !reasonChanges.isEmpty()) {
            return reasonChanges.get(0);
        } else return null;
    }

    public Optional<ReasonChangeResponse> updateReasonChange(long userId, ReasonChange reasonChange, ReasonChangeRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + reasonChange.getId();
        reasonChange.createFromRequest(request);
        reasonChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        reasonChange.setUserLastUpdated(userId);
        ReasonChange result = restTemplate.putForObject(url, reasonChange, ReasonChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));

    }

    public boolean deleteReasonChange(long userId, ReasonChange reasonChange) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + reasonChange.getId();
        reasonChange.setIsDeleted(true);
        reasonChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        reasonChange.setUserLastUpdated(userId);
        restTemplate.putForObject(url, reasonChange, ReasonChange.class);
        return true;
    }

    public List<ReasonChangeResponse> getAllReasonChange(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        List<ReasonChange> reasonChanges = restTemplate.postForObject(url, query, ConvertList.ReasonChangeList.class);
        assert reasonChanges != null;
        return reasonChanges
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<ReasonChange> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.ReasonChangeList.class);
    }

    public PageResponse<ReasonChangeResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<ReasonChange> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<ReasonChange> reasonChanges = mapper.convertValue(response.getList(), new TypeReference<List<ReasonChange>>() {
        });
        List<ReasonChangeResponse> reasonChangeResponses = reasonChanges
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<ReasonChangeResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(reasonChangeResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
