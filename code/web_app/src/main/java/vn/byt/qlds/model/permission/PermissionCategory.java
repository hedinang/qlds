package vn.byt.qlds.model.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionCategory {
    private Integer id;
    private String code;
    private String name;
    private Integer parent;
    private Boolean isParent;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public PermissionCategory(PermissionCategoryRequest request) {
        createFromRequest(request);
    }

    public PermissionCategory createFromRequest(PermissionCategoryRequest request) {
        this.name = request.name;
        this.code = request.code;
        this.isActive = request.isActive;
        this.parent = request.parent != null ? request.parent : 0;
        this.isParent = request.parent == null;
        return this;
    }
}
