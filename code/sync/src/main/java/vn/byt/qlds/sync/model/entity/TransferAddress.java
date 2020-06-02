package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "transfer_address")
public class TransferAddress {
    private int id;
    private Integer addressOld;
    private String regionOld;
    private String regionNew;
    private Integer level;
    private Integer status;
    private Boolean isOutside;
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
    @Column(name = "address_old")
    public Integer getAddressOld() {
        return addressOld;
    }

    public void setAddressOld(Integer addressOld) {
        this.addressOld = addressOld;
    }

    @Basic
    @Column(name = "region_old")
    public String getRegionOld() {
        return regionOld;
    }

    public void setRegionOld(String regionOld) {
        this.regionOld = regionOld;
    }

    @Basic
    @Column(name = "region_new")
    public String getRegionNew() {
        return regionNew;
    }

    public void setRegionNew(String regionNew) {
        this.regionNew = regionNew;
    }

    @Basic
    @Column(name = "level")
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "is_outside")
    public Boolean getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Boolean outside) {
        isOutside = outside;
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
        TransferAddress that = (TransferAddress) o;
        return id == that.id &&
                Objects.equals(addressOld, that.addressOld) &&
                Objects.equals(regionOld, that.regionOld) &&
                Objects.equals(regionNew, that.regionNew) &&
                Objects.equals(level, that.level) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isOutside, that.isOutside) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressOld, regionOld, regionNew, level, status, isOutside, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
