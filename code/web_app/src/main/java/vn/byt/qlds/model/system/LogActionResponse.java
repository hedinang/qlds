package vn.byt.qlds.model.system;

import java.sql.Timestamp;

public class LogActionResponse {
    private long id;
    private Timestamp timeCreated;
    private Long userCreated;
    private String userName;
    private String description;
    private String action;

    public LogActionResponse() {
    }

    public LogActionResponse(long id, Timestamp timeCreated, Long userCreated, String userName, String description, String action) {
        this.id = id;
        this.timeCreated = timeCreated;
        this.userCreated = userCreated;
        this.userName = userName;
        this.description = description;
        this.action = action;
    }

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
