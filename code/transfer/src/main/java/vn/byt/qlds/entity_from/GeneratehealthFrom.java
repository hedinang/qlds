package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "generatehealth")

public class GeneratehealthFrom {
    private Integer generateId;
    private Integer generateCode;
    private Timestamp genDate;
    private Boolean exportStatus;
    private Integer birthNumber;
    private Integer weight1;
    private Integer weight2;
    private Integer weight3;
    private Integer weight4;
    private String placeOfBirth;
    private Integer pregnantCheckNumber;
    private Boolean pregnantAbortNoUse;
    private String regionId;
    private Integer personalId;
    private Integer userId;
    private Timestamp dateUpdate;
    private Timestamp createdDate;
    private String deliver;
    private Timestamp dateSlss;
    private String resultSlss;
    private Timestamp dateSlts1;
    private String resultSlts1;
    private Timestamp dateSlts2;
    private String resultSlts2;

    @Basic
    @Column(name = "Generate_ID")
    @Id
    public Integer getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Integer generateId) {
        this.generateId = generateId;
    }

    @Basic
    @Column(name = "Generate_Code")
    public Integer getGenerateCode() {
        return generateCode;
    }

    public void setGenerateCode(Integer generateCode) {
        this.generateCode = generateCode;
    }

    @Basic
    @Column(name = "Gen_Date")
    public Timestamp getGenDate() {
        return genDate;
    }

    public void setGenDate(Timestamp genDate) {
        this.genDate = genDate;
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
    @Column(name = "Birth_Number")
    public Integer getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(Integer birthNumber) {
        this.birthNumber = birthNumber;
    }

    @Basic
    @Column(name = "Weight1")
    public Integer getWeight1() {
        return weight1;
    }

    public void setWeight1(Integer weight1) {
        this.weight1 = weight1;
    }

    @Basic
    @Column(name = "Weight2")
    public Integer getWeight2() {
        return weight2;
    }

    public void setWeight2(Integer weight2) {
        this.weight2 = weight2;
    }

    @Basic
    @Column(name = "Weight3")
    public Integer getWeight3() {
        return weight3;
    }

    public void setWeight3(Integer weight3) {
        this.weight3 = weight3;
    }

    @Basic
    @Column(name = "Weight4")
    public Integer getWeight4() {
        return weight4;
    }

    public void setWeight4(Integer weight4) {
        this.weight4 = weight4;
    }

    @Basic
    @Column(name = "PlaceOfBirth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Basic
    @Column(name = "Pregnant_Check_Number")
    public Integer getPregnantCheckNumber() {
        return pregnantCheckNumber;
    }

    public void setPregnantCheckNumber(Integer pregnantCheckNumber) {
        this.pregnantCheckNumber = pregnantCheckNumber;
    }

    @Basic
    @Column(name = "Pregnant_Abort_No_Use")
    public Boolean getPregnantAbortNoUse() {
        return pregnantAbortNoUse;
    }

    public void setPregnantAbortNoUse(Boolean pregnantAbortNoUse) {
        this.pregnantAbortNoUse = pregnantAbortNoUse;
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

    @Basic
    @Column(name = "CreatedDate")
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "Deliver")
    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    @Basic
    @Column(name = "Date_SLSS")
    public Timestamp getDateSlss() {
        return dateSlss;
    }

    public void setDateSlss(Timestamp dateSlss) {
        this.dateSlss = dateSlss;
    }

    @Basic
    @Column(name = "Result_SLSS")
    public String getResultSlss() {
        return resultSlss;
    }

    public void setResultSlss(String resultSlss) {
        this.resultSlss = resultSlss;
    }

    @Basic
    @Column(name = "Date_SLTS1")
    public Timestamp getDateSlts1() {
        return dateSlts1;
    }

    public void setDateSlts1(Timestamp dateSlts1) {
        this.dateSlts1 = dateSlts1;
    }

    @Basic
    @Column(name = "Result_SLTS1")
    public String getResultSlts1() {
        return resultSlts1;
    }

    public void setResultSlts1(String resultSlts1) {
        this.resultSlts1 = resultSlts1;
    }

    @Basic
    @Column(name = "Date_SLTS2")
    public Timestamp getDateSlts2() {
        return dateSlts2;
    }

    public void setDateSlts2(Timestamp dateSlts2) {
        this.dateSlts2 = dateSlts2;
    }

    @Basic
    @Column(name = "Result_SLTS2")
    public String getResultSlts2() {
        return resultSlts2;
    }

    public void setResultSlts2(String resultSlts2) {
        this.resultSlts2 = resultSlts2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratehealthFrom that = (GeneratehealthFrom) o;
        return generateId == that.generateId &&
                generateCode == that.generateCode &&
                Objects.equals(genDate, that.genDate) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(birthNumber, that.birthNumber) &&
                Objects.equals(weight1, that.weight1) &&
                Objects.equals(weight2, that.weight2) &&
                Objects.equals(weight3, that.weight3) &&
                Objects.equals(weight4, that.weight4) &&
                Objects.equals(placeOfBirth, that.placeOfBirth) &&
                Objects.equals(pregnantCheckNumber, that.pregnantCheckNumber) &&
                Objects.equals(pregnantAbortNoUse, that.pregnantAbortNoUse) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(personalId, that.personalId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(deliver, that.deliver) &&
                Objects.equals(dateSlss, that.dateSlss) &&
                Objects.equals(resultSlss, that.resultSlss) &&
                Objects.equals(dateSlts1, that.dateSlts1) &&
                Objects.equals(resultSlts1, that.resultSlts1) &&
                Objects.equals(dateSlts2, that.dateSlts2) &&
                Objects.equals(resultSlts2, that.resultSlts2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generateId, generateCode, genDate, exportStatus, birthNumber, weight1, weight2, weight3, weight4, placeOfBirth, pregnantCheckNumber, pregnantAbortNoUse, regionId, personalId, userId, dateUpdate, createdDate, deliver, dateSlss, resultSlss, dateSlts1, resultSlts1, dateSlts2, resultSlts2);
    }
}
