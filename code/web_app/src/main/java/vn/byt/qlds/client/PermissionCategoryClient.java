package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.permission.PermissionCategory;
import vn.byt.qlds.model.permission.PermissionCategoryRequest;
import vn.byt.qlds.model.permission.PermissionCategoryResponse;
import vn.byt.qlds.model.permission.PermissionTreeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PermissionCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public PermissionCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/permission-category";
    }

    public Optional<PermissionCategoryResponse> createPermissionCategory(long userId, PermissionCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        PermissionCategory permissionCategory = new PermissionCategory(request);
        permissionCategory.setIsDeleted(false);
        permissionCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        permissionCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        permissionCategory.setUserCreated(userId);
        permissionCategory.setUserLastUpdated(userId);
        PermissionCategory result = restTemplate.postForObject(this.apiEndpointCommon, permissionCategory, PermissionCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<PermissionCategoryResponse> getPermissionCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        PermissionCategory result = restTemplate.getForObject(url, PermissionCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<PermissionCategoryResponse> updatePermissionCategory(long userId, PermissionCategory permissionCategory, PermissionCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + permissionCategory.getId();
        permissionCategory.createFromRequest(request);
        permissionCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        permissionCategory.setUserLastUpdated(userId);
        PermissionCategory result = restTemplate.putForObject(url, permissionCategory, PermissionCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deletePermissionCategory(long userId, PermissionCategory permissionCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + permissionCategory.getId();
        permissionCategory.setIsDeleted(true);
        permissionCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        permissionCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, permissionCategory, PermissionCategory.class);
        return true;

    }

    public List<PermissionCategoryResponse> getAllPermissionCategory(@RequestBody Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<PermissionCategory> permissionCategories = mapper.convertValue(
                restTemplate.postForObject(url, query, List.class),
                new TypeReference<List<PermissionCategory>>() {
                });
        return permissionCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<PermissionCategoryResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<PermissionCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<PermissionCategory> permissionCategories = mapper.convertValue(response.getList(), new TypeReference<List<PermissionCategory>>() {
        });
        List<PermissionCategoryResponse> permissionCategoryResponses = permissionCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<PermissionCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(permissionCategoryResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }

    public List<PermissionTreeResponse> getParentAndPermissions(){
        List<PermissionTreeResponse> response = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> queryParent = new HashMap<>();
        queryParent.put("isParent", true);
        String url = this.apiEndpointCommon + "/all";
        List<PermissionCategory> permissionCategories = mapper.convertValue(
                restTemplate.postForObject(url, queryParent, List.class),
                new TypeReference<List<PermissionCategory>>() {
                });

        permissionCategories.forEach(permissionParent -> {
            PermissionTreeResponse permissionTreeResponse = new PermissionTreeResponse();
            permissionTreeResponse.id = permissionParent.getId();
            permissionTreeResponse.code = permissionParent.getCode();
            permissionTreeResponse.name = permissionParent.getName();
            permissionTreeResponse.isActive = permissionParent.getIsActive();
            Map<String, Object> queryChild = new HashMap<>();
            queryChild.put("parent", permissionParent.getId());
            permissionTreeResponse.permissions = mapper.convertValue(
                    restTemplate.postForObject(url, queryChild, List.class),
                    new TypeReference<List<PermissionCategory>>() {
                    });
            response.add(permissionTreeResponse);
        });
        return response;
    }
}
