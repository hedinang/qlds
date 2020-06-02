package vn.byt.qlds.model.group;

import vn.byt.qlds.model.permission.PermissionCategory;
import vn.byt.qlds.model.unit.UnitLevelCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserGroupCategoryResponse {
    public UserGroupCategory userGroupCategory;
    public UnitLevelCategory unitLevelCategory;
    public List<PermissionCategory> permissionCategories;
}
