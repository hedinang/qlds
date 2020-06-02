package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "family_planning")
public class FamilyPlanningTo {
    private int id;
    private int personalId;
    private String contraDate;
    private String contraceptiveCode;
    private boolean exportStatus;
    private String regionId;
    private Timestamp dateUpdate;
    private Integer userId;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "personal_id")
    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
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
    @Column(name = "export_status")
    public boolean isExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(boolean exportStatus) {
        this.exportStatus = exportStatus;
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
    @Column(name = "date_update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
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
        FamilyPlanningTo that = (FamilyPlanningTo) o;
        return id == that.id &&
                personalId == that.personalId &&
                exportStatus == that.exportStatus &&
                Objects.equals(contraDate, that.contraDate) &&
                Objects.equals(contraceptiveCode, that.contraceptiveCode) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalId, contraDate, contraceptiveCode, exportStatus, regionId, dateUpdate, userId, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
