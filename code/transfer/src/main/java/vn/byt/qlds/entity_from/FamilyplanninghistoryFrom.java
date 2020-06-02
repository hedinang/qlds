package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "familyplanninghistory", catalog = "")
@IdClass(FamilyplanninghistoryFromPK.class)
public class FamilyplanninghistoryFrom {
    private Integer fpHistoryId;
    private String contraDate;
    private String contraceptiveCode;
    private Boolean exportStatus;
    private String regionId;
    private Integer personalId;
    private Integer userId;
    private Timestamp dateUpdate;

    @Id
    @Column(name = "FPHistory_ID")
    public int getFpHistoryId() {
        return fpHistoryId;
    }

    public void setFpHistoryId(int fpHistoryId) {
        this.fpHistoryId = fpHistoryId;
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
    @Column(name = "Personal_ID")
    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    @Basic
    @Column(name = "User_ID")
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
        FamilyplanninghistoryFrom that = (FamilyplanninghistoryFrom) o;
        return fpHistoryId == that.fpHistoryId &&
                Objects.equals(contraDate, that.contraDate) &&
                Objects.equals(contraceptiveCode, that.contraceptiveCode) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(personalId, that.personalId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dateUpdate, that.dateUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpHistoryId, contraDate, contraceptiveCode, exportStatus, regionId, personalId, userId, dateUpdate);
    }
}
