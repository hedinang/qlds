package vn.byt.qlds.ministry.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "danh_sach_tach_ho", schema = "common", catalog = "")
public class SeparationHouseHold {
    private int id;
    private Integer personId;
    private Integer houseHoldIdOld;
    private Integer addressIdOld;
    private Integer addressIdNew;
    private String regionIdOld;
    private String regionIdNew;
    private Integer status;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private String description;
    private Integer houseHoldIdNew;
    private String houseHoldCodeNew;
    private Timestamp startDate;
    private String householdNumber;
    private Boolean isBigHouseHold;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "person_id", nullable = true)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "houseHold_id_old", nullable = true)
    public Integer getHouseHoldIdOld() {
        return houseHoldIdOld;
    }

    public void setHouseHoldIdOld(Integer houseHoldIdOld) {
        this.houseHoldIdOld = houseHoldIdOld;
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
    @Column(name = "address_id_new", nullable = true)
    public Integer getAddressIdNew() {
        return addressIdNew;
    }

    public void setAddressIdNew(Integer addressIdNew) {
        this.addressIdNew = addressIdNew;
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

    @Basic
    @Column(name = "houseHold_id_new", nullable = true)
    public Integer getHouseHoldIdNew() {
        return houseHoldIdNew;
    }

    public void setHouseHoldIdNew(Integer houseHoldIdNew) {
        this.houseHoldIdNew = houseHoldIdNew;
    }

    @Basic
    @Column(name = "houseHold_code_new", nullable = true, length = 10)
    public String getHouseHoldCodeNew() {
        return houseHoldCodeNew;
    }

    public void setHouseHoldCodeNew(String houseHoldCodeNew) {
        this.houseHoldCodeNew = houseHoldCodeNew;
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
    @Column(name = "household_number", nullable = true, length = 100)
    public String getHouseholdNumber() {
        return householdNumber;
    }

    public void setHouseholdNumber(String householdNumber) {
        this.householdNumber = householdNumber;
    }

    @Basic
    @Column(name = "is_Big_HouseHold", nullable = true)
    public Boolean getIsBigHouseHold() {
        return isBigHouseHold;
    }

    public void setIsBigHouseHold(Boolean bigHouseHold) {
        isBigHouseHold = bigHouseHold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeparationHouseHold that = (SeparationHouseHold) o;
        return id == that.id &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(houseHoldIdOld, that.houseHoldIdOld) &&
                Objects.equals(addressIdOld, that.addressIdOld) &&
                Objects.equals(addressIdNew, that.addressIdNew) &&
                Objects.equals(regionIdOld, that.regionIdOld) &&
                Objects.equals(regionIdNew, that.regionIdNew) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(description, that.description) &&
                Objects.equals(houseHoldIdNew, that.houseHoldIdNew) &&
                Objects.equals(houseHoldCodeNew, that.houseHoldCodeNew) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(householdNumber, that.householdNumber) &&
                Objects.equals(isBigHouseHold, that.isBigHouseHold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId, houseHoldIdOld, addressIdOld, addressIdNew, regionIdOld, regionIdNew, status, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated, description, houseHoldIdNew, houseHoldCodeNew, startDate, householdNumber, isBigHouseHold);
    }
}
