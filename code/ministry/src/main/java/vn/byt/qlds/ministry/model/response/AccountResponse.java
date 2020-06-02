package vn.byt.qlds.ministry.model.response;

import java.sql.Timestamp;
import java.util.List;

public class AccountResponse {
    private Integer id;
    private String userName;
    private String password;
    private String regionId;
    private List<String> role;
    private List<String> permission;
    private String nameDisplay;
    private String email;
    private String dbName;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    public AccountResponse(Integer id, String userName, String password, String regionId, List<String> role, List<String> permission, String nameDisplay, String email, String dbName, Boolean isDeleted, Long userCreated, Long userLastUpdated, Timestamp timeCreated, Timestamp timeUpdated) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.regionId = regionId;
        this.role = role;
        this.permission = permission;
        this.nameDisplay = nameDisplay;
        this.email = email;
        this.dbName = dbName;
        this.isDeleted = isDeleted;
        this.userCreated = userCreated;
        this.userLastUpdated = userLastUpdated;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    public AccountResponse() {
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
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
}
