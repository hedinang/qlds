package vn.byt.qlds.model.group;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RolePermissionRequest {
    public Integer roleId;
    public Integer permissionId;
    public Boolean isDeleted;
}
