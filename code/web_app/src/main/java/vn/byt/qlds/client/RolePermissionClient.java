package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.group.RolePermission;
import vn.byt.qlds.model.group.RolePermissionRequest;
import vn.byt.qlds.model.group.UserGroupCategory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolePermissionClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    UserGroupCategoryClient userGroupCategoryClient;
    @Autowired
    PermissionCategoryClient permissionCategoryClient;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public RolePermissionClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/role-permission";
    }

    private RolePermission createRolePermission(long userId, RolePermissionRequest request) {
        long currentTime = System.currentTimeMillis();
        RolePermission rolePermission = new RolePermission(request);
        rolePermission.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        rolePermission.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        rolePermission.setUserCreated(userId);
        rolePermission.setUserLastUpdated(userId);
        return restTemplate.postForObject(this.apiEndpointCommon, rolePermission, RolePermission.class);
    }

    public List<RolePermission> createRolePermission(long userId, Integer roleId, List<Integer> permissionIds) {
        Set<Integer> permissionsSet = new HashSet<>(permissionIds);
        return permissionsSet
                .stream()
                .map(permId -> {
                    RolePermissionRequest rolePermissionRequest = new RolePermissionRequest(roleId, permId, false);
                    return createRolePermission(userId, rolePermissionRequest);
                })
                .collect(Collectors.toList());
    }

    public RolePermission updateRolePermissionById(long userId, Integer id, RolePermissionRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        RolePermission rolePermission = restTemplate.getForObject(url, RolePermission.class);
        if (rolePermission != null) {
            rolePermission.createFromRequest(request);
            rolePermission.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            rolePermission.setUserLastUpdated(userId);
            return restTemplate.putForObject(url, rolePermission, RolePermission.class);
        } else {
            //todo throw
            return null;
        }
    }

    public List<RolePermission> updateRolePermissionByRoleId(long userId, Integer roleId, List<Integer> newPermissionIds) {
        Set<Integer> filterPermIds = new HashSet<>(newPermissionIds);
        deleteRolePermissionByRoleId(userId, roleId);
        return filterPermIds
                .stream()
                .map(permId -> createRolePermission(
                        userId,
                        new RolePermissionRequest(roleId, permId, false))
                ).collect(Collectors.toList());
    }

    public List<RolePermission> getRolePermissionByRoleId(Integer roleId) {
        String url = this.apiEndpointCommon + "?roleId=" + roleId;
        ObjectMapper mapper = new ObjectMapper();
        List<RolePermission> rolePermissions = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<RolePermission>>() {
        });
        return rolePermissions;
    }

    public List<UserGroupCategory> getRoleByPermissionId(Integer permissionId) {
        return new ArrayList<>();
    }

    public RolePermission getRolePermissionById(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        RolePermission result = restTemplate.getForObject(url, RolePermission.class);
        return result;
    }

    public boolean deleteRolePermissionById(long userId, Integer id) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        RolePermission rolePermission = restTemplate.getForObject(url, RolePermission.class);
        if (rolePermission != null) {
            rolePermission.setIsDeleted(true);
            rolePermission.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            rolePermission.setUserLastUpdated(userId);
            restTemplate.putForObject(url, rolePermission, RolePermission.class);
            return true;
        } else {
            //todo throw not found
            return false;
        }
    }

    public void deleteRolePermissionByRoleId(long userId, Integer roleId) {
        List<RolePermission> rolePermissions = getRolePermissionByRoleId(roleId);
        rolePermissions.forEach(rolePermission -> {
            deleteRolePermissionById(userId, rolePermission.getId());
        });
    }
}
