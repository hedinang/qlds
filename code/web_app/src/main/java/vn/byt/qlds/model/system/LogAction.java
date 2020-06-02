package vn.byt.qlds.model.system;

import java.io.Serializable;
import java.sql.Timestamp;

public class LogAction implements Serializable {
    private long id;
    private Timestamp timeCreated;
    private Long userCreated;
    private String userName;
    private String description;
    private Boolean isDeleted;
    private String action;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
