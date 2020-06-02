package vn.byt.qlds.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(name = "family_planning_history")
public class FamilyPlanningHistory {
    private Integer id;
    private Integer fpHistoryId;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private String contraDate;
    private String contraceptiveCode;
    private Integer userId;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private Integer contraceptiveId;

    @Id
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "fphistory_id", nullable = false)
    public Integer getFpHistoryId() {
        return fpHistoryId;
    }

    public void setFpHistoryId(Integer fpHistoryId) {
        this.fpHistoryId = fpHistoryId;
    }

    @Basic
    @Column(name = "region_id", nullable = false, length = 15)
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "personal_id")
    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    @Basic
    @Column(name = "date_update", nullable = true)
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Basic
    @Column(name = "contra_date", nullable = false, length = 10)
    public String getContraDate() {
        return contraDate;
    }

    public void setContraDate(String contraDate) {
        this.contraDate = contraDate;
    }

    @Basic
    @Column(name = "contraceptive_code", nullable = false, length = 10)
    public String getContraceptiveCode() {
        return contraceptiveCode;
    }

    public void setContraceptiveCode(String contraceptiveCode) {
        this.contraceptiveCode = contraceptiveCode;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "export_status", nullable = true)
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "is_deleted", nullable = true)
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_created", nullable = true)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated", nullable = true)
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    @Basic
    @Column(name = "user_created", nullable = true)
    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    @Basic
    @Column(name = "user_last_updated", nullable = true)
    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    @Basic
    @Column(name = "Contraceptive_Id", nullable = true)
    public Integer getContraceptiveId() {
        return contraceptiveId;
    }

    public void setContraceptiveId(Integer contraceptiveId) {
        this.contraceptiveId = contraceptiveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyPlanningHistory that = (FamilyPlanningHistory) o;
        return fpHistoryId == that.fpHistoryId &&
                personalId == that.personalId &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(contraDate, that.contraDate) &&
                Objects.equals(contraceptiveCode, that.contraceptiveCode) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(contraceptiveId, that.contraceptiveId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpHistoryId, regionId, personalId, dateUpdate, contraDate, contraceptiveCode, userId, exportStatus, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated, contraceptiveId);
    }
}
