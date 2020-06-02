package vn.byt.qlds.entity_from;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "personal")
@IdClass(PersonalFromPK.class)
public class PersonalFrom {
    private int personalId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationalityCode;
    private String relationCode;
    private String ethnicCode;
    private String residenceCode;
    private String educationCode;
    private String technicalCode;
    private String maritalCode;
    private String sexId;
    private String regionId;
    private int houseHoldId;
    private String personStatus;
    private String educationLevel;
    private Integer userId;
    private String invalidCode;
    private Boolean exportStatus;
    private Timestamp changeDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String notes;
    private Integer motherId;
    private String birthNumber;
    private Integer generateId;

    @Id
    @Column(name = "Personal_ID")
    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
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
    @Column(name = "DateOfBirth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    @Column(name = "Nationality_Code")
    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    @Basic
    @Column(name = "Relation_Code")
    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    @Basic
    @Column(name = "Ethnic_Code")
    public String getEthnicCode() {
        return ethnicCode;
    }

    public void setEthnicCode(String ethnicCode) {
        this.ethnicCode = ethnicCode;
    }

    @Basic
    @Column(name = "Residence_Code")
    public String getResidenceCode() {
        return residenceCode;
    }

    public void setResidenceCode(String residenceCode) {
        this.residenceCode = residenceCode;
    }

    @Basic
    @Column(name = "Education_Code")
    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    @Basic
    @Column(name = "Technical_Code")
    public String getTechnicalCode() {
        return technicalCode;
    }

    public void setTechnicalCode(String technicalCode) {
        this.technicalCode = technicalCode;
    }

    @Basic
    @Column(name = "Marital_Code")
    public String getMaritalCode() {
        return maritalCode;
    }

    public void setMaritalCode(String maritalCode) {
        this.maritalCode = maritalCode;
    }

    @Basic
    @Column(name = "Sex_ID")
    public String getSexId() {
        return sexId;
    }

    public void setSexId(String sexId) {
        this.sexId = sexId;
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
    @Column(name = "HouseHold_ID")
    public int getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(int houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "Person_Status")
    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    @Basic
    @Column(name = "Education_Level")
    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
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
    @Column(name = "Invalid_Code")
    public String getInvalidCode() {
        return invalidCode;
    }

    public void setInvalidCode(String invalidCode) {
        this.invalidCode = invalidCode;
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
    @Column(name = "Change_Date")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    @Basic
    @Column(name = "Start_Date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "End_Date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "Notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "Mother_ID")
    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }

    @Basic
    @Column(name = "Birth_Number")
    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }

    @Basic
    @Column(name = "Generate_ID")
    public Integer getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Integer generateId) {
        this.generateId = generateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalFrom that = (PersonalFrom) o;
        return personalId == that.personalId &&
                houseHoldId == that.houseHoldId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(placeOfBirth, that.placeOfBirth) &&
                Objects.equals(nationalityCode, that.nationalityCode) &&
                Objects.equals(relationCode, that.relationCode) &&
                Objects.equals(ethnicCode, that.ethnicCode) &&
                Objects.equals(residenceCode, that.residenceCode) &&
                Objects.equals(educationCode, that.educationCode) &&
                Objects.equals(technicalCode, that.technicalCode) &&
                Objects.equals(maritalCode, that.maritalCode) &&
                Objects.equals(sexId, that.sexId) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(personStatus, that.personStatus) &&
                Objects.equals(educationLevel, that.educationLevel) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(invalidCode, that.invalidCode) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(motherId, that.motherId) &&
                Objects.equals(birthNumber, that.birthNumber) &&
                Objects.equals(generateId, that.generateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId, firstName, lastName, dateOfBirth, placeOfBirth, nationalityCode, relationCode, ethnicCode, residenceCode, educationCode, technicalCode, maritalCode, sexId, regionId, houseHoldId, personStatus, educationLevel, userId, invalidCode, exportStatus, changeDate, startDate, endDate, notes, motherId, birthNumber, generateId);
    }
}
