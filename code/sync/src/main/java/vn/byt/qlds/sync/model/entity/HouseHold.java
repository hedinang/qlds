package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "household")
public class HouseHold {
    private Integer id;
    private Integer houseHoldId;
    private String houseHoldCode;
    private Integer addressId;
    private String regionId;
    private String houseHoldNumber;
    private Boolean exportStatus;
    private Integer userId;
    private String houseHoldIdOld;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean isBigHouseHold;
    private String notes;
    private String houseHoldStatus;
    private Boolean isChecked;
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

    @Column(name = "household_id", nullable = false)
    public Integer getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(Integer houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "houseHold_code", nullable = false, length = 10)
    public String getHouseHoldCode() {
        return houseHoldCode;
    }

    public void setHouseHoldCode(String houseHoldCode) {
        this.houseHoldCode = houseHoldCode;
    }

    @Basic
    @Column(name = "address_id", nullable = false)
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
    @Column(name = "household_number", nullable = true, length = 100)
    public String getHouseHoldNumber() {
        return houseHoldNumber;
    }

    public void setHouseHoldNumber(String houseHoldNumber) {
        this.houseHoldNumber = houseHoldNumber;
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
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "household_old_id", nullable = true, length = 10)
    public String getHouseHoldIdOld() {
        return houseHoldIdOld;
    }

    public void setHouseHoldIdOld(String houseHoldIdOld) {
        this.houseHoldIdOld = houseHoldIdOld;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "is_big_household", nullable = true)
    public Boolean getIsBigHouseHold() {
        return isBigHouseHold;
    }

    public void setIsBigHouseHold(Boolean isBigHouseHold) {
        this.isBigHouseHold = isBigHouseHold;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "household_status", nullable = true, length = 1)
    public String getHouseHoldStatus() {
        return houseHoldStatus;
    }

    public void setHouseHoldStatus(String houseHoldStatus) {
        this.houseHoldStatus = houseHoldStatus;
    }

    @Basic
    @Column(name = "is_checked", nullable = true)
    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseHold houseHold = (HouseHold) o;
        return houseHoldId.equals(houseHold.houseHoldId) &&
                addressId.equals(houseHold.addressId) &&
                Objects.equals(houseHoldCode, houseHold.houseHoldCode) &&
                Objects.equals(regionId, houseHold.regionId) &&
                Objects.equals(houseHoldNumber, houseHold.houseHoldNumber) &&
                Objects.equals(exportStatus, houseHold.exportStatus) &&
                Objects.equals(userId, houseHold.userId) &&
                Objects.equals(houseHoldIdOld, houseHold.houseHoldIdOld) &&
                Objects.equals(startDate, houseHold.startDate) &&
                Objects.equals(endDate, houseHold.endDate) &&
                Objects.equals(isBigHouseHold, houseHold.isBigHouseHold) &&
                Objects.equals(notes, houseHold.notes) &&
                Objects.equals(houseHoldStatus, houseHold.houseHoldStatus) &&
                Objects.equals(isChecked, houseHold.isChecked) &&
                Objects.equals(isDeleted, houseHold.isDeleted) &&
                Objects.equals(timeCreated, houseHold.timeCreated) &&
                Objects.equals(timeLastUpdated, houseHold.timeLastUpdated) &&
                Objects.equals(userCreated, houseHold.userCreated) &&
                Objects.equals(userLastUpdated, houseHold.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseHoldId, houseHoldCode, addressId, regionId, houseHoldNumber, exportStatus, userId, houseHoldIdOld, startDate, endDate, isBigHouseHold, notes, houseHoldStatus, isChecked, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
