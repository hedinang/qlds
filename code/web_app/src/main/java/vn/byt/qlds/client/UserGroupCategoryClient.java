package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.group.UserGroupCategory;
import vn.byt.qlds.model.group.UserGroupCategoryRequest;
import vn.byt.qlds.model.group.UserGroupCategoryResponse;
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
public class UserGroupCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    RolePermissionClient rolePermissionClient;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public UserGroupCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/user-group-category";
    }

    public Optional<UserGroupCategoryResponse> createUserGroupCategory(long userId, UserGroupCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        UserGroupCategory userGroupCategory = new UserGroupCategory(request);
        userGroupCategory.setIsDeleted(false);
        userGroupCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        userGroupCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        userGroupCategory.setUserCreated(userId);
        userGroupCategory.setUserLastUpdated(userId);
        UserGroupCategory result = restTemplate.postForObject(this.apiEndpointCommon, userGroupCategory, UserGroupCategory.class);
        if (result != null) {
            /*tạo thành công, ==> create role-permission*/
            rolePermissionClient.createRolePermission(userId, result.getId(), request.permission);
            return Optional.ofNullable(toResponseService.transfer(result));
        } else {
            return Optional.empty();
        }
    }

    public Optional<UserGroupCategoryResponse> updateUserGroupCategory(long userId, UserGroupCategory userGroupCategory, UserGroupCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + userGroupCategory.getId();
        userGroupCategory.createFromRequest(request);
        userGroupCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        userGroupCategory.setUserLastUpdated(userId);
        UserGroupCategory result = restTemplate.putForObject(url, userGroupCategory, UserGroupCategory.class);
        if (result != null) {
            /*thành công update role permission*/
            rolePermissionClient.updateRolePermissionByRoleId(userId, result.getId(), request.permission);
        }
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UserGroupCategoryResponse> getUserGroupCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        UserGroupCategory result = restTemplate.getForObject(url, UserGroupCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteUserGroupCategory(long userId, UserGroupCategory userGroupCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + userGroupCategory.getId();
        userGroupCategory.setIsDeleted(true);
        userGroupCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        userGroupCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, userGroupCategory, UserGroupCategory.class);
        rolePermissionClient.deleteRolePermissionByRoleId(userId, userGroupCategory.getId());
        return true;
    }

    public List<UserGroupCategoryResponse> getAllUserGroupCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<UserGroupCategory> userGroupCategories = mapper.convertValue(
                restTemplate.postForObject(url, query, List.class),
                new TypeReference<List<UserGroupCategory>>() {
                });
        List<UserGroupCategoryResponse> responses = userGroupCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        return responses;
    }

    public List<UserGroupCategory> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.UserGroupCategoryList.class);
    }

    public PageResponse<UserGroupCategoryResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<UserGroupCategory> userGroupCategoryPages = restTemplate.postForObject(url, query, PageResponse.class);

        ObjectMapper mapper = new ObjectMapper();
        List<UserGroupCategory> userGroupCategoryList = mapper.convertValue(
                userGroupCategoryPages.getList(),
                new TypeReference<List<UserGroupCategory>>() {
                });

        List<UserGroupCategoryResponse> userGroupCategoryResponses = userGroupCategoryList
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<UserGroupCategoryResponse> pageResponse = new PageResponse<>();
        pageResponse.setPage(userGroupCategoryPages.getPage());
        pageResponse.setTotal(userGroupCategoryPages.getTotal());
        pageResponse.setList(userGroupCategoryResponses);
        return pageResponse;
    }

}
