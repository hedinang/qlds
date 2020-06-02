package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "change_history")
public class ChangeFrom {
    private Integer id;
    private Integer changeId;
    private Timestamp changeDate;
    private String source;
    private String destination;
    private String status;
    private String changeTypeCode;
    private Integer personalId;
    private String notes;
    private String regionId;
    private Timestamp dateUpdate;
    private Integer userId;
    private Boolean exportStatus;
    private Timestamp dieDate;
    private Timestamp goDate;
    private Timestamp comeDate;
    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Change_ID")
    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    @Basic
    @Column(name = "Change_Date")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    @Basic
    @Column(name = "Source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "Destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "Status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ChangeType_Code")
    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    @Basic
    @Column(name = "Personal_ID")
    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    @Basic
    @Column(name = "Notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "Region_ID")
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "Date_Update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Basic
    @Column(name = "User_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Export_Status")
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "Die_date")
    public Timestamp getDieDate() {
        return dieDate;
    }

    public void setDieDate(Timestamp dieDate) {
        this.dieDate = dieDate;
    }

    @Basic
    @Column(name = "Go_date")
    public Timestamp getGoDate() {
        return goDate;
    }

    public void setGoDate(Timestamp goDate) {
        this.goDate = goDate;
    }

    @Basic
    @Column(name = "Come_date")
    public Timestamp getComeDate() {
        return comeDate;
    }

    public void setComeDate(Timestamp comeDate) {
        this.comeDate = comeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChangeFrom that = (ChangeFrom) o;
        return changeId == that.changeId &&
                id == that.id &&
                personalId == that.personalId &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(source, that.source) &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(status, that.status) &&
                Objects.equals(changeTypeCode, that.changeTypeCode) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(dieDate, that.dieDate) &&
                Objects.equals(goDate, that.goDate) &&
                Objects.equals(comeDate, that.comeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(changeId, changeDate, source, destination, status, changeTypeCode, personalId, notes, regionId, dateUpdate, userId, exportStatus, dieDate, goDate, comeDate);
    }
}
