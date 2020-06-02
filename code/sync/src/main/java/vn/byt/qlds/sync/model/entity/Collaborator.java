package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "collaborator")
public class Collaborator {
    private Integer id;
    private Integer collaboratorId;
    private String ma;
    private String firstName;
    private String lastName;
    private Timestamp hireDate;
    private String regionId;
    private String sexId;
    private Boolean isCadre;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Long stt;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Timestamp endDate;

    @Id
    @Column(name = "id", nullable = false)
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
    @Column(name = "ma", nullable = true, length = 255)
    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 10)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 30)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "hire_date", nullable = true)
    public Timestamp getHireDate() {
        return hireDate;
    }

    public void setHireDate(Timestamp hireDate) {
        this.hireDate = hireDate;
    }

    @Basic
    @Column(name = "region_id", nullable = false, length = 15)
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "sex_id", nullable = true, length = 1)
    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }

    @Basic
    @Column(name = "is_cadre", nullable = true)
    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean cadre) {
        isCadre = cadre;
    }

    @Basic
    @Column(name = "export_status", nullable = true)
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
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
    @Column(name = "stt", nullable = true)
    public Long getStt() {
        return stt;
    }

    public void setStt(Long index) {
        this.stt = index;
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
    @Column(name = "is_active", nullable = true)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collaborator that = (Collaborator) o;
        return id.equals(that.id) &&
                collaboratorId.equals(that.collaboratorId) &&
                Objects.equals(ma, that.ma) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(sexId, that.sexId) &&
                Objects.equals(isCadre, that.isCadre) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ma, firstName, lastName, hireDate, regionId, sexId, isCadre, exportStatus, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated, isActive, endDate);
    }

}
