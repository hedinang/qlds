package vn.byt.qlds.model.account;

import java.util.List;

public class AccountRequest {
    public String userName;
    public String password;
    public List<Integer> roleIds;
    public String regionId;
    public String nameDisplay;
    public String email;
    public String dbName;
    public Boolean isActive;
}
