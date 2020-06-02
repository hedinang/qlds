package vn.byt.qlds.model._province.family_plan;

import java.sql.Timestamp;

public class FamilyPlanning {
    private Integer personalId;
    private String contraDate;
    private String contraceptiveCode;
    private Boolean exportStatus;
    private String regionId;
    private Timestamp dateUpdate;
    private Integer userId;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getContraDate() {
        return contraDate;
    }

    public void setContraDate(String contraDate) {
        this.contraDate = contraDate;
    }

    public String getContraceptiveCode() {
        return contraceptiveCode;
    }

    public void setContraceptiveCode(String contraceptiveCode) {
        this.contraceptiveCode = contraceptiveCode;
    }

    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
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
}
