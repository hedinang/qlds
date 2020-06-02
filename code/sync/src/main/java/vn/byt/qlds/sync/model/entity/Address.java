package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address implements Serializable {
    private Integer id;
    private Integer collaboratorId;
    private String name;
    private String levels;
    private String parent;
    private String regionId;
    private String notes;
    private String fullName;
    private Boolean exportStatus;
    private Integer userId;
    private Integer addressIdOld;
    private Timestamp dateUpdate;
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

    @Basic
    @Column(name = "collaborator_id")
    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "levels")
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
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
    @Column(name = "region_id")
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
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
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    @Column(name = "address_id_old")
    public Integer getAddressIdOld() {
        return addressIdOld;
    }

    public void setAddressIdOld(Integer addressIdOld) {
        this.addressIdOld = addressIdOld;
    }

    @Basic
    @Column(name = "date_update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
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
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(collaboratorId, address.collaboratorId) &&
                Objects.equals(name, address.name) &&
                Objects.equals(levels, address.levels) &&
                Objects.equals(parent, address.parent) &&
                Objects.equals(regionId, address.regionId) &&
                Objects.equals(notes, address.notes) &&
                Objects.equals(fullName, address.fullName) &&
                Objects.equals(exportStatus, address.exportStatus) &&
                Objects.equals(userId, address.userId) &&
                Objects.equals(addressIdOld, address.addressIdOld) &&
                Objects.equals(dateUpdate, address.dateUpdate) &&
                Objects.equals(isDeleted, address.isDeleted) &&
                Objects.equals(timeCreated, address.timeCreated) &&
                Objects.equals(timeLastUpdated, address.timeLastUpdated) &&
                Objects.equals(userCreated, address.userCreated) &&
                Objects.equals(userLastUpdated, address.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, collaboratorId, name, levels, parent, regionId, notes, fullName, exportStatus, userId, addressIdOld, dateUpdate, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
