package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "household")
@IdClass(HouseholdFromPK.class)
public class HouseholdFrom {
    private int houseHoldId;
    private String houseHoldCode;
    private int addressId;
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

    @Id
    @Column(name = "HouseHold_ID")
    public int getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(int houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "HouseHold_Code")
    public String getHouseHoldCode() {
        return houseHoldCode;
    }

    public void setHouseHoldCode(String houseHoldCode) {
        this.houseHoldCode = houseHoldCode;
    }

    @Basic
    @Column(name = "Address_ID")
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Id
    @Column(name = "Region_ID")
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "HouseHold_Number")
    public String getHouseHoldNumber() {
        return houseHoldNumber;
    }

    public void setHouseHoldNumber(String houseHoldNumber) {
        this.houseHoldNumber = houseHoldNumber;
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
    @Column(name = "USER_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "HouseHoldID_Old")
    public String getHouseHoldIdOld() {
        return houseHoldIdOld;
    }

    public void setHouseHoldIdOld(String houseHoldIdOld) {
        this.houseHoldIdOld = houseHoldIdOld;
    }

    @Basic
    @Column(name = "Start_Date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "End_Date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "IsBigHouseHold")
    public Boolean getIsBigHouseHold() {
        return isBigHouseHold;
    }

    public void setIsBigHouseHold(Boolean isBigHouseHold) {
        this.isBigHouseHold = isBigHouseHold;
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
    @Column(name = "HouseHold_Status")
    public String getHouseHoldStatus() {
        return houseHoldStatus;
    }

    public void setHouseHoldStatus(String houseHoldStatus) {
        this.houseHoldStatus = houseHoldStatus;
    }

    @Basic
    @Column(name = "IsChecked")
    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdFrom that = (HouseholdFrom) o;
        return houseHoldId == that.houseHoldId &&
                addressId == that.addressId &&
                exportStatus == that.exportStatus &&
                Objects.equals(houseHoldCode, that.houseHoldCode) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(houseHoldNumber, that.houseHoldNumber) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(houseHoldIdOld, that.houseHoldIdOld) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(isBigHouseHold, that.isBigHouseHold) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(houseHoldStatus, that.houseHoldStatus) &&
                Objects.equals(isChecked, that.isChecked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseHoldId, houseHoldCode, addressId, regionId, houseHoldNumber, exportStatus, userId, houseHoldIdOld, startDate, endDate, isBigHouseHold, notes, houseHoldStatus, isChecked);
    }
}
