package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.unit.UnitLevelCategory;
import vn.byt.qlds.model.unit.UnitLevelCategoryRequest;
import vn.byt.qlds.model.unit.UnitLevelCategoryResponse;
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
public class UnitLevelCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public UnitLevelCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon, @Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointCommon = apiEndpointCommon + "/unit-level-category";
    }

    public Optional<UnitLevelCategoryResponse> createUnitLevelCategory(long userId, UnitLevelCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        UnitLevelCategory unitLevelCategory = new UnitLevelCategory(request);
        unitLevelCategory.setIsDeleted(false);
        unitLevelCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        unitLevelCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitLevelCategory.setUserCreated(userId);
        unitLevelCategory.setUserLastUpdated(userId);
        UnitLevelCategory result = restTemplate.postForObject(this.apiEndpointCommon, unitLevelCategory, UnitLevelCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UnitLevelCategoryResponse> getUnitLevelCategory(Integer id) {
        if (id == null) return Optional.empty();
        String url = this.apiEndpointCommon + "/" + id;
        UnitLevelCategory result = restTemplate.getForObject(url, UnitLevelCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UnitLevelCategoryResponse> updateUnitLevelCategory(long userId, UnitLevelCategory unitLevelCategory, UnitLevelCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + unitLevelCategory.getId();
        unitLevelCategory.createFromRequest(request);
        unitLevelCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitLevelCategory.setUserLastUpdated(userId);
        UnitLevelCategory result = restTemplate.putForObject(url, unitLevelCategory, UnitLevelCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));

    }

    public boolean deleteUnitLevelCategory(long userId, UnitLevelCategory unitLevelCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + unitLevelCategory.getId();
        unitLevelCategory.setIsDeleted(true);
        unitLevelCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitLevelCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, unitLevelCategory, UnitLevelCategory.class);
        return true;
    }

    public List<UnitLevelCategoryResponse> getAllUnitLevelCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<UnitLevelCategory> list = restTemplate.postForObject(url, query, List.class);
        List<UnitLevelCategory> unitLevelCategories = mapper.convertValue(list, new TypeReference<List<UnitLevelCategory>>() {
        });
        return unitLevelCategories.stream().map(toResponseService::transfer).collect(Collectors.toList());
    }

    public List<UnitLevelCategory> findAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.UnitLevelList.class);
    }

    public PageResponse<UnitLevelCategoryResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        ObjectMapper mapper = new ObjectMapper();
        PageResponse<UnitLevelCategory> unitLevelCategoryPages = restTemplate.postForObject(url, query, PageResponse.class);
        List<UnitLevelCategory> listUnitLevelCategories = mapper.convertValue(unitLevelCategoryPages.getList(), new TypeReference<List<UnitLevelCategory>>() {
        });
        List<UnitLevelCategoryResponse> unitLevelCategoryResponses = listUnitLevelCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<UnitLevelCategoryResponse> result = new PageResponse<>();
        result.setPage(unitLevelCategoryPages.getPage());
        result.setTotal(unitLevelCategoryPages.getTotal());
        result.setList(unitLevelCategoryResponses);
        result.setLimit(unitLevelCategoryPages.getLimit());
        return result;

    }
}
