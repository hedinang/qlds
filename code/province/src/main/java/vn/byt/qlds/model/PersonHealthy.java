package vn.byt.qlds.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
@Table(name = "nguoi_dan_suc_khoe")
public class PersonHealthy implements Serializable {
    private Integer id;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private Integer generateCode;
    private Timestamp genDate;
    private Integer birthNumber;
    private Integer weight1;
    private Integer weight2;
    private Integer weight3;
    private Integer weight4;
    private String placeOfBirth;
    private Integer pregnantCheckNumber;
    private Boolean pregnantAbortNoUse;
    private Integer userId;
    private Boolean exportStatus;
    private String deliver;
    private Timestamp dateSlss;
    private String resultSlss;
    private Timestamp dateSlts1;
    private String resultSlts1;
    private Timestamp dateSlts2;
    private String resultSlts2;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "personal_id")
    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
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
    @Column(name = "generate_code")
    public Integer getGenerateCode() {
        return generateCode;
    }

    public void setGenerateCode(Integer generateCode) {
        this.generateCode = generateCode;
    }

    @Basic
    @Column(name = "gen_date")
    public Timestamp getGenDate() {
        return genDate;
    }

    public void setGenDate(Timestamp genDate) {
        this.genDate = genDate;
    }

    @Basic
    @Column(name = "birth_number")
    public Integer getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(Integer birthNumber) {
        this.birthNumber = birthNumber;
    }

    @Basic
    @Column(name = "weight1")
    public Integer getWeight1() {
        return weight1;
    }

    public void setWeight1(Integer weight1) {
        this.weight1 = weight1;
    }

    @Basic
    @Column(name = "weight2")
    public Integer getWeight2() {
        return weight2;
    }

    public void setWeight2(Integer weight2) {
        this.weight2 = weight2;
    }

    @Basic
    @Column(name = "weight3")
    public Integer getWeight3() {
        return weight3;
    }

    public void setWeight3(Integer weight3) {
        this.weight3 = weight3;
    }

    @Basic
    @Column(name = "weight4")
    public Integer getWeight4() {
        return weight4;
    }

    public void setWeight4(Integer weight4) {
        this.weight4 = weight4;
    }

    @Basic
    @Column(name = "place_of_birth")
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Basic
    @Column(name = "pregnant_check_number")
    public Integer getPregnantCheckNumber() {
        return pregnantCheckNumber;
    }

    public void setPregnantCheckNumber(Integer pregnantCheckNumber) {
        this.pregnantCheckNumber = pregnantCheckNumber;
    }

    @Basic
    @Column(name = "regnant_abort_no_use")
    public Boolean getPregnantAbortNoUse() {
        return pregnantAbortNoUse;
    }

    public void setPregnantAbortNoUse(Boolean pregnantAbortNoUse) {
        this.pregnantAbortNoUse = pregnantAbortNoUse;
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
    @Column(name = "export_status")
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "deliver")
    public String getDeliver() {
        return deliver;
    }

    public void setDeliver(String deliver) {
        this.deliver = deliver;
    }

    @Basic
    @Column(name = "date_slss")
    public Timestamp getDateSlss() {
        return dateSlss;
    }

    public void setDateSlss(Timestamp dateSlss) {
        this.dateSlss = dateSlss;
    }

    @Basic
    @Column(name = "result_slss")
    public String getResultSlss() {
        return resultSlss;
    }

    public void setResultSlss(String resultSlss) {
        this.resultSlss = resultSlss;
    }

    @Basic
    @Column(name = "date_slts1")
    public Timestamp getDateSlts1() {
        return dateSlts1;
    }

    public void setDateSlts1(Timestamp dateSlts1) {
        this.dateSlts1 = dateSlts1;
    }

    @Basic
    @Column(name = "result_slts1")
    public String getResultSlts1() {
        return resultSlts1;
    }

    public void setResultSlts1(String resultSlts1) {
        this.resultSlts1 = resultSlts1;
    }

    @Basic
    @Column(name = "date_slts2")
    public Timestamp getDateSlts2() {
        return dateSlts2;
    }

    public void setDateSlts2(Timestamp dateSlts2) {
        this.dateSlts2 = dateSlts2;
    }

    @Basic
    @Column(name = "result_slts2")
    public String getResultSlts2() {
        return resultSlts2;
    }

    public void setResultSlts2(String resultSlts2) {
        this.resultSlts2 = resultSlts2;
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
        PersonHealthy that = (PersonHealthy) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(personalId, that.personalId) &&
                Objects.equals(dateUpdate, that.dateUpdate) &&
                Objects.equals(generateCode, that.generateCode) &&
                Objects.equals(genDate, that.genDate) &&
                Objects.equals(birthNumber, that.birthNumber) &&
                Objects.equals(weight1, that.weight1) &&
                Objects.equals(weight2, that.weight2) &&
                Objects.equals(weight3, that.weight3) &&
                Objects.equals(weight4, that.weight4) &&
                Objects.equals(placeOfBirth, that.placeOfBirth) &&
                Objects.equals(pregnantCheckNumber, that.pregnantCheckNumber) &&
                Objects.equals(pregnantAbortNoUse, that.pregnantAbortNoUse) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(deliver, that.deliver) &&
                Objects.equals(dateSlss, that.dateSlss) &&
                Objects.equals(resultSlss, that.resultSlss) &&
                Objects.equals(dateSlts1, that.dateSlts1) &&
                Objects.equals(resultSlts1, that.resultSlts1) &&
                Objects.equals(dateSlts2, that.dateSlts2) &&
                Objects.equals(resultSlts2, that.resultSlts2) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionId, personalId, dateUpdate, generateCode, genDate, birthNumber, weight1, weight2, weight3, weight4, placeOfBirth, pregnantCheckNumber, pregnantAbortNoUse, userId, exportStatus, deliver, dateSlss, resultSlss, dateSlts1, resultSlts1, dateSlts2, resultSlts2, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}