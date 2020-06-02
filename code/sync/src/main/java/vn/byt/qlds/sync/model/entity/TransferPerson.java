package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "danh_sach_chuyen_ngoai_vung")
public class TransferPerson {
    private int id;
    private String personId;
    private String personName;
    private String oldAddress;
    private String newAddress;
    private String oldRegionId;
    private String newRegionId;
    private Integer houseHoldId;
    private String houseHoldName;
    private Timestamp timeRequireSeparate;
    private Integer status;
    private String description;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "person_id", nullable = true, length = 255)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "person_name", nullable = true, length = 255)
    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Basic
    @Column(name = "old_address", nullable = true, length = 255)
    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    @Basic
    @Column(name = "new_address", nullable = true, length = 255)
    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }

    @Basic
    @Column(name = "old_region_id", nullable = true)
    public String getOldRegionId() {
        return oldRegionId;
    }

    public void setOldRegionId(String oldRegionId) {
        this.oldRegionId = oldRegionId;
    }

    @Basic
    @Column(name = "new_region_id", nullable = true)
    public String getNewRegionId() {
        return newRegionId;
    }

    public void setNewRegionId(String newRegionId) {
        this.newRegionId = newRegionId;
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
    @Column(name = "time_require_separate", nullable = true)
    public Timestamp getTimeRequireSeparate() {
        return timeRequireSeparate;
    }

    public void setTimeRequireSeparate(Timestamp timeRequireSeparate) {
        this.timeRequireSeparate = timeRequireSeparate;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "time_created", nullable = true)
    @CreationTimestamp
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated", nullable = true)
    @UpdateTimestamp
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferPerson that = (TransferPerson) o;
        return id == that.id &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(personName, that.personName) &&
                Objects.equals(oldAddress, that.oldAddress) &&
                Objects.equals(newAddress, that.newAddress) &&
                Objects.equals(oldRegionId, that.oldRegionId) &&
                Objects.equals(newRegionId, that.newRegionId) &&
                Objects.equals(timeRequireSeparate, that.timeRequireSeparate) &&
                Objects.equals(status, that.status) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, personName, oldAddress, newAddress, oldRegionId, newRegionId, timeRequireSeparate, status, description, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}
