package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "transfer_house_hold")
public class TransferHouseHold {
    private int id;
    private Integer houseHoldId;
    private String houseHoldName;
    private Integer addressIdOld;
    private String addressOld;
    private Integer addressIdNew;
    private String addressNew;
    private String regionIdOld;
    private String regionIdNew;
    private Integer status;
    private Timestamp startDate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "house_hold_id", nullable = true)
    public Integer getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(Integer houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "house_hold_name", nullable = true, length = 255)
    public String getHouseHoldName() {
        return houseHoldName;
    }

    public void setHouseHoldName(String houseHoldName) {
        this.houseHoldName = houseHoldName;
    }

    @Basic
    @Column(name = "address_id_old", nullable = true)
    public Integer getAddressIdOld() {
        return addressIdOld;
    }

    public void setAddressIdOld(Integer addressIdOld) {
        this.addressIdOld = addressIdOld;
    }

    @Basic
    @Column(name = "address_old", nullable = true, length = 255)
    public String getAddressOld() {
        return addressOld;
    }

    public void setAddressOld(String addressOld) {
        this.addressOld = addressOld;
    }

    @Basic
    @Column(name = "address_id_new", nullable = true)
    public Integer getAddressIdNew() {
        return addressIdNew;
    }

    public void setAddressIdNew(Integer addressIdNew) {
        this.addressIdNew = addressIdNew;
    }

    @Basic
    @Column(name = "address_new", nullable = true, length = 255)
    public String getAddressNew() {
        return addressNew;
    }

    public void setAddressNew(String addressNew) {
        this.addressNew = addressNew;
    }

    @Basic
    @Column(name = "region_id_old", nullable = true, length = 255)
    public String getRegionIdOld() {
        return regionIdOld;
    }

    public void setRegionIdOld(String regionIdOld) {
        this.regionIdOld = regionIdOld;
    }

    @Basic
    @Column(name = "region_id_new", nullable = true, length = 255)
    public String getRegionIdNew() {
        return regionIdNew;
    }

    public void setRegionIdNew(String regionIdNew) {
        this.regionIdNew = regionIdNew;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferHouseHold that = (TransferHouseHold) o;
        return id == that.id &&
                Objects.equals(houseHoldId, that.houseHoldId) &&
                Objects.equals(houseHoldName, that.houseHoldName) &&
                Objects.equals(addressIdOld, that.addressIdOld) &&
                Objects.equals(addressOld, that.addressOld) &&
                Objects.equals(addressIdNew, that.addressIdNew) &&
                Objects.equals(addressNew, that.addressNew) &&
                Objects.equals(regionIdOld, that.regionIdOld) &&
                Objects.equals(regionIdNew, that.regionIdNew) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, houseHoldId, houseHoldName, addressIdOld, addressOld, addressIdNew, addressNew, regionIdOld, regionIdNew, status, startDate, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated, description);
    }
}
