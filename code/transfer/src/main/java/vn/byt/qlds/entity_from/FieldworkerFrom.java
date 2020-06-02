package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "fieldworker")
@IdClass(FieldworkerFromPK.class)
public class FieldworkerFrom {
    private Integer fieldWorkerId;
    private String firstName;
    private String lastName;
    private Timestamp hireDate;
    private String regionId;
    private String sexId;
    private Boolean isCadre;
    private Boolean exportStatus;
    private Integer userId;
    private Timestamp dateUpdate;

    @Id
    @Column(name = "FieldWorker_ID")
    public Integer getFieldWorkerId() {
        return fieldWorkerId;
    }

    public void setFieldWorkerId(Integer fieldWorkerId) {
        this.fieldWorkerId = fieldWorkerId;
    }

    @Basic
    @Column(name = "First_Name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "Last_Name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "Hire_Date")
    public Timestamp getHireDate() {
        return hireDate;
    }

    public void setHireDate(Timestamp hireDate) {
        this.hireDate = hireDate;
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
    @Column(name = "Sex_ID")
    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
    }

    @Basic
    @Column(name = "IsCadre")
    public Boolean getIsCadre() {
        return isCadre;
    }

    public void setIsCadre(Boolean isCadre) {
        this.isCadre = isCadre;
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
        FieldworkerFrom that = (FieldworkerFrom) o;
        return fieldWorkerId == that.fieldWorkerId &&
                exportStatus == that.exportStatus &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(hireDate, that.hireDate) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(sexId, that.sexId) &&
                Objects.equals(isCadre, that.isCadre) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dateUpdate, that.dateUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldWorkerId, firstName, lastName, hireDate, regionId, sexId, isCadre, exportStatus, userId, dateUpdate);
    }
}
