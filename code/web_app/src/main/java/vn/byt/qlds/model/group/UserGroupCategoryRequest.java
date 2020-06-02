package vn.byt.qlds.model.group;

import java.util.List;

public class UserGroupCategoryRequest {
    public String groupName;
    public Integer levelId;
    public List<Integer> permission;
    public Boolean isActive;
    public String note;
}
