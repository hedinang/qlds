package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "region_change")
public class RegionChange {
    private int id;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "change_date")
    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
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
    @Column(name = "region_name")
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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
    @Column(name = "parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "change_type")
    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    @Basic
    @Column(name = "change_desc")
    public String getChangeDesc() {
        return changeDesc;
    }

    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
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
        RegionChange that = (RegionChange) o;
        return id == that.id &&
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
