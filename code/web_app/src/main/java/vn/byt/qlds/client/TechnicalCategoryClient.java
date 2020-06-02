package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.technical.TechnicalCategory;
import vn.byt.qlds.model.technical.TechnicalCategoryRequest;
import vn.byt.qlds.model.technical.TechnicalCategoryResponse;
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
public class TechnicalCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public TechnicalCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/technical-category";
    }

    public Optional<TechnicalCategoryResponse> createTechnicalCategory(long userId, TechnicalCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        TechnicalCategory technicalCategory = new TechnicalCategory(request);
        technicalCategory.setIsDeleted(false);
        technicalCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        technicalCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        technicalCategory.setUserCreated(userId);
        technicalCategory.setUserLastUpdated(userId);
        TechnicalCategory result = restTemplate.postForObject(this.apiEndpointCommon, technicalCategory, TechnicalCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TechnicalCategoryResponse> getTechnicalCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        TechnicalCategory result = restTemplate.getForObject(url, TechnicalCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<TechnicalCategoryResponse> updateTechnicalCategory(long userId, TechnicalCategory technicalCategory, TechnicalCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + technicalCategory.getId();
        technicalCategory.createFromRequest(request);
        technicalCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        technicalCategory.setUserLastUpdated(userId);
        TechnicalCategory result = restTemplate.putForObject(url, technicalCategory, TechnicalCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteTechnicalCategory(long userId, TechnicalCategory technicalCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + technicalCategory.getId();
        technicalCategory.setIsDeleted(true);
        technicalCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        technicalCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, technicalCategory, TechnicalCategory.class);
        return true;
    }

    public List<TechnicalCategoryResponse> getAllTechnicalCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<TechnicalCategory> technicalCategories = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<TechnicalCategory>>() {
        });
        return technicalCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<TechnicalCategory> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.TechnicalList.class);
    }

    public PageResponse<TechnicalCategoryResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<TechnicalCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<TechnicalCategory> technicalCategories = mapper.convertValue(response.getList(), new TypeReference<List<TechnicalCategory>>() {
        });
        List<TechnicalCategoryResponse> technicalCategoryResponses = technicalCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<TechnicalCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(technicalCategoryResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
