package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "household")
public class HouseholdTo {
    private Integer id;
    private Integer householdId;
    private String householdCode;
    private Integer addressId;
    private String regionId;
    private String householdNumber;
    private Boolean exportStatus;
    private Integer userId;
    private String householdOldId;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean isBigHousehold;
    private String notes;
    private String householdStatus;
    private Boolean isChecked;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "household_id")
    public Integer getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Integer householdId) {
        this.householdId = householdId;
    }

    @Basic
    @Column(name = "household_code")
    public String getHouseholdCode() {
        return householdCode;
    }

    public void setHouseholdCode(String householdCode) {
        this.householdCode = householdCode;
    }

    @Basic
    @Column(name = "address_id")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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
    @Column(name = "household_number")
    public String getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        this.householdNumber = householdNumber;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "household_old_id")
    public String getHouseholdOldId() {
        return householdOldId;
    }

    public void setHouseholdOldId(String householdOldId) {
        this.householdOldId = householdOldId;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "is_big_household")
    public Boolean getIsBigHousehold() {
        return isBigHousehold;
    }

    public void setIsBigHousehold(Boolean isBigHousehold) {
        this.isBigHousehold = isBigHousehold;
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
    @Column(name = "household_status")
    public String getHouseholdStatus() {
        return householdStatus;
    }

    public void setHouseholdStatus(String householdStatus) {
        this.householdStatus = householdStatus;
    }

    @Basic
    @Column(name = "is_checked")
    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
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
        HouseholdTo that = (HouseholdTo) o;
        return id.equals(that.id) &&
                addressId.equals(that.addressId) &&
                Objects.equals(householdId, that.householdId) &&
                Objects.equals(householdCode, that.householdCode) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(householdNumber, that.householdNumber) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(householdOldId, that.householdOldId) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(isBigHousehold, that.isBigHousehold) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(householdStatus, that.householdStatus) &&
                Objects.equals(isChecked, that.isChecked) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, householdId, householdCode, addressId, regionId, householdNumber, exportStatus, userId, householdOldId, startDate, endDate, isBigHousehold, notes, householdStatus, isChecked, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
