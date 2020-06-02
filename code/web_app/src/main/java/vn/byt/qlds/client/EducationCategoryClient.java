package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.education.EducationCategory;
import vn.byt.qlds.model.education.EducationCategoryRequest;
import vn.byt.qlds.model.education.EducationCategoryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/*trình độ học vấn*/
@Component
public class EducationCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public EducationCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/education-category";
    }

    public Optional<EducationCategoryResponse> createLevelCategory(long userId, EducationCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        EducationCategory educationCategory = new EducationCategory(request);
        educationCategory.setIsDeleted(false);
        educationCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        educationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        educationCategory.setUserCreated(userId);
        educationCategory.setUserLastUpdated(userId);
        EducationCategory result = restTemplate.postForObject(this.apiEndpointCommon, educationCategory, EducationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<EducationCategoryResponse> getLevelCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        EducationCategory result = restTemplate.getForObject(url, EducationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<EducationCategoryResponse> updateLevelCategory(long userId, EducationCategory educationCategory, EducationCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + educationCategory.getId();
        educationCategory.createFromRequest(request);
        educationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        educationCategory.setUserLastUpdated(userId);
        EducationCategory result = restTemplate.putForObject(url, educationCategory, EducationCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteLevelCategory(long userId, EducationCategory educationCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + educationCategory.getId();
        educationCategory.setIsDeleted(true);
        educationCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        educationCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, educationCategory, EducationCategory.class);
        return true;
    }

    public List<EducationCategoryResponse> getAllLevelCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<EducationCategory> contraceptives = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<EducationCategory>>() {
        });
        return contraceptives
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<EducationCategory> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.EducationList.class);
    }

    public PageResponse<EducationCategoryResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<EducationCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<EducationCategory> deadCategories = mapper.convertValue(response.getList(), new TypeReference<List<EducationCategory>>() {
        });
        List<EducationCategoryResponse> contraceptiveResponses = deadCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<EducationCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(contraceptiveResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
