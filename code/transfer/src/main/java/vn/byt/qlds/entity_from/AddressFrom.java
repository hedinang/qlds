package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "address")
@IdClass(AddressFromPK.class)
public class AddressFrom {
    private Integer fieldWorkerId;
    private Integer addressId;
    private String addressName;
    private String levels;
    private String parent;
    private String regionId;
    private String notes;
    private String fullAddress;
    private Boolean exportStatus;
    private Integer userId;
    private Integer addressIdOld;
    private Timestamp dateUpdate;

    @Basic
    @Column(name = "FieldWorker_ID")
    public Integer getFieldWorkerId() {
        return fieldWorkerId;
    }

    public void setFieldWorkerId(Integer fieldWorkerId) {
        this.fieldWorkerId = fieldWorkerId;
    }

    @Id
    @Column(name = "Address_ID")
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Basic
    @Column(name = "Address_Name")
    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Basic
    @Column(name = "Levels")
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    @Basic
    @Column(name = "Parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
    @Column(name = "Notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "Full_Address")
    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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
    @Column(name = "AddressID_Old")
    public Integer getAddressIdOld() {
        return addressIdOld;
    }

    public void setAddressIdOld(Integer addressIdOld) {
        this.addressIdOld = addressIdOld;
    }

    @Basic
    @Column(name = "Date_Update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressFrom that = (AddressFrom) o;
        return fieldWorkerId == that.fieldWorkerId &&
                addressId == that.addressId &&
                exportStatus == that.exportStatus &&
                Objects.equals(addressName, that.addressName) &&
                Objects.equals(levels, that.levels) &&
                Objects.equals(parent, that.parent) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(fullAddress, that.fullAddress) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(addressIdOld, that.addressIdOld) &&
                Objects.equals(dateUpdate, that.dateUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldWorkerId, addressId, addressName, levels, parent, regionId, notes, fullAddress, exportStatus, userId, addressIdOld, dateUpdate);
    }
}
