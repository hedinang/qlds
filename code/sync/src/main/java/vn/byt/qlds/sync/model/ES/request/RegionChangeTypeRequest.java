package vn.byt.qlds.sync.model.ES.request;

public class RegionChangeTypeRequest {
    private String changeTypeCode;
    private String changeTypeDesc;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;

    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    public String getChangeTypeDesc() {
        return changeTypeDesc;
    }

    public void setChangeTypeDesc(String changeTypeDesc) {
        this.changeTypeDesc = changeTypeDesc;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
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

    public Long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Long getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Long timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }
}
