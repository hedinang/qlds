package vn.byt.qlds.ministry.model.request;

import java.util.List;

public class AccountRequest {

    private String userName;
    private String password;
    private List<Integer> role;
    private Integer regionId;
    private String nameDisplay;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getRole() {
        return role;
    }

    public void setRole(List<Integer> role) {
        this.role = role;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountRequest(String userName, String password, List<Integer> role, Integer regionId, String nameDisplay, String email) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.regionId = regionId;
        this.nameDisplay = nameDisplay;
        this.email = email;
    }
}
