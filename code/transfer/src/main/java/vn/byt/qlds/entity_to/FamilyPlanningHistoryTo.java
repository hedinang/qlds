package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "family_planning_history")
public class FamilyPlanningHistoryTo {
    private Integer id;
    private Integer fphistoryId;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private String contraDate;
    private String contraceptiveCode;
    private Integer contraceptiveId;
    private Integer userId;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fphistory_id")
    public Integer getFphistoryId() {
        return fphistoryId;
    }

    public void setFphistoryId(Integer fphistoryId) {
        this.fphistoryId = fphistoryId;
    }

    @Basic
    @Column(name = "region_id")
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
    @Column(name = "date_update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Basic
    @Column(name = "contra_date")
    public String getContraDate() {
        return contraDate;
    }

    public void setContraDate(String contraDate) {
        this.contraDate = contraDate;
    }

    @Basic
    @Column(name = "contraceptive_code")
    public String getContraceptiveCode() {
        return contraceptiveCode;
    }

    public void setContraceptiveCode(String contraceptiveCode) {
        this.contraceptiveCode = contraceptiveCode;
    }

    @Basic
    @Column(name = "contraceptive_id")
    public Integer getContraceptiveId() {
        return contraceptiveId;
    }

    public void setContraceptiveId(Integer contraceptiveId) {
        this.contraceptiveId = contraceptiveId;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "export_status")
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_created")
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated")
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    @Basic
    @Column(name = "user_created")
    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    @Basic
    @Column(name = "user_last_updated")
    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyPlanningHistoryTo that = (FamilyPlanningHistoryTo) o;
        return id == that.id &&
                fphistoryId == that.fphistoryId &&
                personalId == that.personalId &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(contraDate, that.contraDate) &&
                Objects.equals(contraceptiveCode, that.contraceptiveCode) &&
                Objects.equals(contraceptiveId, that.contraceptiveId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fphistoryId, regionId, personalId, dateUpdate, contraDate, contraceptiveCode,contraceptiveId, userId, exportStatus, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
