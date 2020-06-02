package vn.byt.qlds.model.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    private Integer id;
    private Integer roleId;
    private Integer permissionId;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public RolePermission(RolePermissionRequest request) {
        createFromRequest(request);
    }

    public RolePermission createFromRequest(RolePermissionRequest request) {
        this.roleId = request.roleId;
        this.permissionId = request.permissionId;
        this.isDeleted = request.isDeleted;
        return this;
    }
}
