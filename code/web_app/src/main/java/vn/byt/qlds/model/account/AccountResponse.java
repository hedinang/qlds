package vn.byt.qlds.model.account;

import vn.byt.qlds.model.group.UserGroupCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    public Account account;
    public List<UserGroupCategoryResponse> userGroupResponse = new ArrayList<>();
    public String unitName;
    public Integer level;
    public String parent;
}
