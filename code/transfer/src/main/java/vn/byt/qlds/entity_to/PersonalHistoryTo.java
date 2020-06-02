package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "personal_history")
public class PersonalHistoryTo {
    private Integer id;
    private Integer changeId;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private Timestamp changeDate;
    private String changeTypeCode;
    private String source;
    private String destination;
    private String status;
    private String notes;
    private Integer userId;
    private Boolean exportStatus;
    private Timestamp dieDate;
    private Timestamp goDate;
    private Timestamp comeDate;
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
    @Column(name = "change_id")
    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
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
    @Column(name = "change_date")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    @Basic
    @Column(name = "change_type_code")
    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
    @Column(name = "die_date")
    public Timestamp getDieDate() {
        return dieDate;
    }

    public void setDieDate(Timestamp dieDate) {
        this.dieDate = dieDate;
    }

    @Basic
    @Column(name = "go_date")
    public Timestamp getGoDate() {
        return goDate;
    }

    public void setGoDate(Timestamp goDate) {
        this.goDate = goDate;
    }

    @Basic
    @Column(name = "come_date")
    public Timestamp getComeDate() {
        return comeDate;
    }

    public void setComeDate(Timestamp comeDate) {
        this.comeDate = comeDate;
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
        PersonalHistoryTo that = (PersonalHistoryTo) o;
        return id == that.id &&
                changeId == that.changeId &&
                personalId == that.personalId &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(changeTypeCode, that.changeTypeCode) &&
                Objects.equals(source, that.source) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(status, that.status) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(dieDate, that.dieDate) &&
                Objects.equals(goDate, that.goDate) &&
                Objects.equals(comeDate, that.comeDate) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, changeId, regionId, personalId, dateUpdate, changeDate, changeTypeCode, source, destination, status, notes, userId, exportStatus, dieDate, goDate, comeDate, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
