package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "familyplanning")
@IdClass(FamilyplanningFromPK.class)
public class FamilyplanningFrom {
    private Integer personalId;
    private String contraDate;
    private String contraceptiveCode;
    private Boolean exportStatus;
    private String regionId;
    private Timestamp dateUpdate;
    private Integer userId;

    @Id
    @Column(name = "Personal_ID")
    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    @Basic
    @Column(name = "Contra_Date")
    public String getContraDate() {
        return contraDate;
    }

    public void setContraDate(String contraDate) {
        this.contraDate = contraDate;
    }

    @Basic
    @Column(name = "Contraceptive_Code")
    public String getContraceptiveCode() {
        return contraceptiveCode;
    }

    public void setContraceptiveCode(String contraceptiveCode) {
        this.contraceptiveCode = contraceptiveCode;
    }

    @Basic
    @Column(name = "Export_Status")
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
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
    @Column(name = "Date_Update")
    public Timestamp getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Basic
    @Column(name = "User_ID")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FamilyplanningFrom that = (FamilyplanningFrom) o;
        return personalId == that.personalId &&
                exportStatus == that.exportStatus &&
                Objects.equals(contraDate, that.contraDate) &&
                Objects.equals(contraceptiveCode, that.contraceptiveCode) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId, contraDate, contraceptiveCode, exportStatus, regionId, dateUpdate, userId);
    }
}
