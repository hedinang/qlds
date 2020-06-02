package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "collaborator")
public class CollaboratorTo {
    private Integer id;
    private Integer collaboratorId;
    private String firstName;
    private String lastName;
    private Timestamp hireDate;
    private String regionId;
    private String sexId;
    private Boolean isCadre;
    private Boolean exportStatus;
    private Integer userId;
    private Timestamp dateUpdate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdate;
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
    @Column(name = "collaborator_id")
    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    @Basic
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "hire_date")
    public Timestamp getHireDate() {
        return hireDate;
    }

    public void setHireDate(Timestamp hireDate) {
        this.hireDate = hireDate;
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
    @Column(name = "sex_id")
    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }

    @Basic
    @Column(name = "is_cadre")
    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
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
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_update")
    public Timestamp getTimeLastUpdate() {
        return timeLastUpdate;
    }

    public void setTimeLastUpdate(Timestamp timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
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
        CollaboratorTo that = (CollaboratorTo) o;
        return id == that.id &&
                collaboratorId == that.collaboratorId &&
                exportStatus == that.exportStatus &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(sexId, that.sexId) &&
                Objects.equals(isCadre, that.isCadre) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdate, that.timeLastUpdate) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, collaboratorId, firstName, lastName, hireDate, regionId, sexId, isCadre, exportStatus, userId, dateUpdate, isDeleted, timeCreated, timeLastUpdate, userCreated, userLastUpdated);
    }
}
