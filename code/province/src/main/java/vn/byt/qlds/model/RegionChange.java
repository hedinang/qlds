package vn.byt.qlds.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "dia_chi_hanh_chinh_thay_doi")
public class RegionChange implements Serializable {
    private Integer id;
    private String changeDate;
    private String regionId;
    private String regionName;
    private String regionOld;
    private String parent;
    private String changeType;
    private String changeDesc;
    private String status;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ChangeDate")
    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
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
    @Column(name = "Region_Name")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "Region_Old")
    public String getRegionOld() {
        return regionOld;
    }

    public void setRegionOld(String regionOld) {
        this.regionOld = regionOld;
    }

    @Basic
    @Column(name = "Parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "ChangeType")
    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    @Basic
    @Column(name = "ChangeDesc")
    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
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
    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_created")
    @CreationTimestamp
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated")
    @UpdateTimestamp
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
        RegionChange that = (RegionChange) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(regionName, that.regionName) &&
                Objects.equals(regionOld, that.regionOld) &&
                Objects.equals(parent, that.parent) &&
                Objects.equals(changeType, that.changeType) &&
                Objects.equals(changeDesc, that.changeDesc) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, changeDate, regionId, regionName, regionOld, parent, changeType, changeDesc, status, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
