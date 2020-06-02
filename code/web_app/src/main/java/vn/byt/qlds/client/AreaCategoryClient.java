package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.model.area.AreaCategory;
import vn.byt.qlds.model.area.AreaCategoryRequest;
import vn.byt.qlds.model.area.AreaCategoryResponse;
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
public class AreaCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public AreaCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/area-category";
    }

    public Optional<AreaCategoryResponse> createAreaCategory(long userId, AreaCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        AreaCategory areaCategory = new AreaCategory(request);
        areaCategory.setIsDeleted(false);
        areaCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        areaCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        areaCategory.setUserCreated(userId);
        areaCategory.setUserLastUpdated(userId);
        AreaCategory result = restTemplate.postForObject(this.apiEndpointCommon, areaCategory, AreaCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<AreaCategoryResponse> getAreaCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        AreaCategory result = restTemplate.getForObject(url, AreaCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public AreaCategoryResponse updateAreaCategory(long userId, int id, AreaCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        AreaCategory areaCategory = restTemplate.getForObject(url, AreaCategory.class);
        if (areaCategory != null) {
            areaCategory.createFromRequest(request);
            areaCategory.setIsDeleted(false);
            areaCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            areaCategory.setUserLastUpdated(userId);
            AreaCategory result = restTemplate.putForObject(url, areaCategory, AreaCategory.class);
            if (result == null) throw new RuntimeException("Cập nhật vùng thất bại vui lòng thử lại");
            return toResponseService.transfer(result);
        } else {
            throw new NotFoundException("Không tìm thấy vùng có id = " + id);
        }
    }

    public boolean deleteAreaCategory(long userId, Integer id) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        AreaCategory areaCategory = restTemplate.getForObject(url, AreaCategory.class);
        if (areaCategory != null) {
            areaCategory.setIsDeleted(true);
            areaCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            areaCategory.setUserLastUpdated(userId);
            restTemplate.putForObject(url, areaCategory, AreaCategory.class);
            return true;
        } else {
            throw new NotFoundException("Không tìm thấy vùng có id = " + id);
        }
    }

    public List<AreaCategoryResponse> getAllAreaCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<AreaCategory> areaCategories = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<AreaCategory>>() {
        });
        return areaCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<AreaCategoryResponse> getPageAreaCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<AreaCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<AreaCategory> areaCategories = mapper.convertValue(response.getList(), new TypeReference<List<AreaCategory>>() {
        });
        List<AreaCategoryResponse> areaCategoryResponses = areaCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<AreaCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(areaCategoryResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }

    public List<AreaCategory> findAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.AreaList.class);
    }
}
