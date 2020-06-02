package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.disability.DisabilityCategory;
import vn.byt.qlds.model.disability.DisabilityRequest;
import vn.byt.qlds.model.disability.DisabilityResponse;
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
public class DisabilityCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public DisabilityCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/disability-category";
    }

    public Optional<DisabilityResponse> createDisabilityCategory(long userId, DisabilityRequest request) {
        long currentTime = System.currentTimeMillis();
        DisabilityCategory DisabilityCategory = new DisabilityCategory(request);
        DisabilityCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        DisabilityCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        DisabilityCategory.setUserCreated(userId);
        DisabilityCategory.setUserLastUpdated(userId);
        DisabilityCategory result = restTemplate.postForObject(this.apiEndpointCommon, DisabilityCategory, DisabilityCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<DisabilityResponse> getDisabilityCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        DisabilityCategory result = restTemplate.getForObject(url, DisabilityCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<DisabilityCategory> getDisabilityByCode(String code) {
        Map<String, Object> request = new HashMap<>();
        request.put("code", code);
        String url = this.apiEndpointCommon + "/all" ;
        List<DisabilityCategory> disabilityCategories = restTemplate.postForObject(url, request, ConvertList.InvalidList.class);
        if (disabilityCategories!=null && !disabilityCategories.isEmpty()){
            return Optional.ofNullable(disabilityCategories.get(0));
        }else{
            return Optional.empty();
        }
    }

    public Optional<DisabilityResponse> updateDisabilityCategory(long userId, DisabilityCategory disabilityCategory, DisabilityRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + disabilityCategory.getId();
        disabilityCategory.createFromRequest(request);
        disabilityCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        disabilityCategory.setUserLastUpdated(userId);
        DisabilityCategory result = restTemplate.putForObject(url, disabilityCategory, DisabilityCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteDisabilityCategory(long userId, DisabilityCategory disabilityCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + disabilityCategory.getId();
        disabilityCategory.setIsDeleted(true);
        disabilityCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        disabilityCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, disabilityCategory, DisabilityCategory.class);
        return true;
    }

    public List<DisabilityResponse> getAllDisabilityCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<DisabilityCategory> disabilityCategories = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<DisabilityCategory>>() {
        });
        return disabilityCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<DisabilityResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<DisabilityCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<DisabilityCategory> disabilityCategories = mapper.convertValue(response.getList(), new TypeReference<List<DisabilityCategory>>() {
        });
        List<DisabilityResponse> disabilityResponses = disabilityCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<DisabilityResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(disabilityResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
