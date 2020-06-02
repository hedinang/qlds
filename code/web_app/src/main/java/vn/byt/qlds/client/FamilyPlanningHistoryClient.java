package vn.byt.qlds.client;

import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistory;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistoryRequest;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistoryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FamilyPlanningHistoryClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointProvince;

    public FamilyPlanningHistoryClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointProvince = apiEndpointProvince + "/family-planning-history";
    }

    public Optional<FamilyPlanningHistoryResponse> createFamilyPlanningHistory(String db, long userId, FamilyPlanningHistoryRequest request) {
        long currentTime = System.currentTimeMillis();
        FamilyPlanningHistory familyPlanningHistory = new FamilyPlanningHistory(request);
        familyPlanningHistory.setIsDeleted(false);
        familyPlanningHistory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        familyPlanningHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        familyPlanningHistory.setUserCreated(userId);
        familyPlanningHistory.setUserLastUpdated(userId);
        FamilyPlanningHistory result = tenantRestTemplate.postForObject(
                db,
                this.apiEndpointProvince,
                familyPlanningHistory,
                FamilyPlanningHistory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<FamilyPlanningHistoryResponse> getFamilyPlanningHistory(String db, Integer id) {
        String url = this.apiEndpointProvince + "/" + id;
        FamilyPlanningHistory result = tenantRestTemplate.getForObject(db, url, FamilyPlanningHistory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<FamilyPlanningHistory> findFamilyPlanById(String db, Integer id) {
        String url = this.apiEndpointProvince + "/" + id;
        FamilyPlanningHistory result = tenantRestTemplate.getForObject(db, url, FamilyPlanningHistory.class);
        return Optional.ofNullable(result);
    }

    public Optional<FamilyPlanningHistoryResponse> updateFamilyPlanningHistory(
            String db,
            long userId,
            FamilyPlanningHistory familyPlanningHistory,
            FamilyPlanningHistoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + familyPlanningHistory.getFpHistoryId();
        familyPlanningHistory.createFromRequest(request);
        familyPlanningHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        familyPlanningHistory.setUserLastUpdated(userId);
        FamilyPlanningHistory result = tenantRestTemplate.putForObject(db, url, familyPlanningHistory, FamilyPlanningHistory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteFamilyPlanningHistory(String db, long userId, FamilyPlanningHistory familyPlanningHistory) {
        String url = this.apiEndpointProvince + "/" + familyPlanningHistory.getFpHistoryId();
        long currentTime = System.currentTimeMillis();
        familyPlanningHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        familyPlanningHistory.setUserLastUpdated(userId);
        familyPlanningHistory.setIsDeleted(true);
        FamilyPlanningHistory unit = tenantRestTemplate.putForObject(db, url, familyPlanningHistory, FamilyPlanningHistory.class);
        return unit != null;
    }


    public PageResponse<FamilyPlanningHistoryResponse> getPage(String db, Map<String, Object> request) {
        String url = this.apiEndpointProvince + "/" + "search-page";
        PageResponse<FamilyPlanningHistory> familyPlanningHistoryPages = tenantRestTemplate.postForObject(db, url, request, PageResponse.class);
        PageResponse<FamilyPlanningHistoryResponse> result = new PageResponse<>();
        result.setPage(familyPlanningHistoryPages.getPage());
        ObjectMapper mapper = new ObjectMapper();
        List<FamilyPlanningHistory> familyPlanningHistoryList = mapper.convertValue(
                familyPlanningHistoryPages.getList(),
                new TypeReference<List<FamilyPlanningHistory>>() {
                });
        List<FamilyPlanningHistoryResponse> list = familyPlanningHistoryList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        result.setList(list);
        return result;

    }

    public List<FamilyPlanningHistoryResponse> getAllFamilyPlanningHistory(String db, Map<String, Object> request) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointProvince + "/all";
        List<FamilyPlanningHistory> familyPlanningHistoryList = mapper.convertValue(
                tenantRestTemplate.postForObject(db, url, request, List.class),
                new TypeReference<List<FamilyPlanningHistory>>() {
                });
        return familyPlanningHistoryList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

}
