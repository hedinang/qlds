package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.ethnic.NationCategory;
import vn.byt.qlds.model.ethnic.NationCategoryRequest;
import vn.byt.qlds.model.ethnic.NationCategoryResponse;
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
public class NationCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public NationCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/nation-category";
    }

    public Optional<NationCategoryResponse> createNationCategory(long userId, NationCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        NationCategory nationCategory = new NationCategory(request);
        nationCategory.setIsDeleted(false);
        nationCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        nationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationCategory.setUserCreated(userId);
        nationCategory.setUserLastUpdated(userId);
        NationCategory result = restTemplate.postForObject(this.apiEndpointCommon, nationCategory, NationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<NationCategoryResponse> getNationCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        NationCategory result = restTemplate.getForObject(url, NationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<NationCategoryResponse> updateNationCategory(long userId, NationCategory nationCategory, NationCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + nationCategory.getId();
        nationCategory.createFromRequest(request);
        nationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationCategory.setUserLastUpdated(userId);
        NationCategory result = restTemplate.putForObject(url, nationCategory, NationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteNationCategory(long userId, NationCategory nationCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + nationCategory.getId();
        nationCategory.setIsDeleted(true);
        nationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, nationCategory, NationCategory.class);
        return true;
    }

    public List<NationCategoryResponse> getAllNationCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<NationCategory> nationCategories = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<NationCategory>>() {
        });
        return nationCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<NationCategory> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.EthnicList.class);
    }

    public PageResponse<NationCategoryResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<NationCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<NationCategory> nationCategories = mapper.convertValue(response.getList(), new TypeReference<List<NationCategory>>() {
        });
        List<NationCategoryResponse> nationCategoryResponses = nationCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<NationCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(nationCategoryResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
