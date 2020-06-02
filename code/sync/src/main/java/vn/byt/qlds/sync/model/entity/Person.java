package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "personal")
public class Person {
    private Integer id;
    private Integer personalId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nationalityCode;
    private Integer relationId;
    private Integer ethnicId;
    private Integer residenceId;
    private Integer educationId;
    private Integer technicalId;
    private Integer maritalId;
    private Integer sexId;
    private String regionId;
    private Integer houseHoldId;
    private String personStatus;
    private String educationLevel;
    private String invalidId;
    private Boolean exportStatus;
    private Timestamp changeDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String notes;
    private Integer motherId;
    private Boolean birthNumber;
    private Integer generateId;
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

    @Column(name = "personal_id", nullable = false)
    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 10)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true, length = 10)
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "place_of_birth", nullable = true, length = 15)
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    @Basic
    @Column(name = "nationality_code", nullable = true)
    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    @Basic
    @Column(name = "relation_id", nullable = true)
    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    @Basic
    @Column(name = "ethnic_id", nullable = true)
    public Integer getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(Integer ethnicId) {
        this.ethnicId = ethnicId;
    }

    @Basic
    @Column(name = "residence_id", nullable = true)
    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    @Basic
    @Column(name = "education_id", nullable = true)
    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    @Basic
    @Column(name = "technical_id", nullable = true)
    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    @Basic
    @Column(name = "marital_id", nullable = true)
    public Integer getMaritalId() {
        return maritalId;
    }

    public void setMaritalId(Integer maritalId) {
        this.maritalId = maritalId;
    }

    @Basic
    @Column(name = "sex_id", nullable = true)
    public Integer getSexId() {
        return sexId;
    }

    public void setSexId(Integer sexId) {
        this.sexId = sexId;
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
    @Column(name = "houseHold_id", nullable = true)
    public Integer getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(Integer houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "person_status", nullable = true, length = 50)
    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    @Basic
    @Column(name = "education_level", nullable = true, length = 10)
    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    @Basic
    @Column(name = "invalid_id", nullable = true, length = 10)
    public String getInvalidId() {
        return invalidId;
    }

    public void setInvalidId(String invalidId) {
        this.invalidId = invalidId;
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
    @Column(name = "change_date", nullable = true)
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
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
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "mother_id", nullable = true)
    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }

    @Basic
    @Column(name = "birth_number", nullable = true)
    public Boolean getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(Boolean birthNumber) {
        this.birthNumber = birthNumber;
    }

    @Basic
    @Column(name = "generate_id", nullable = true)
    public Integer getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Integer generateId) {
        this.generateId = generateId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personalId == person.personalId &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(placeOfBirth, person.placeOfBirth) &&
                Objects.equals(nationalityCode, person.nationalityCode) &&
                Objects.equals(relationId, person.relationId) &&
                Objects.equals(ethnicId, person.ethnicId) &&
                Objects.equals(residenceId, person.residenceId) &&
                Objects.equals(educationId, person.educationId) &&
                Objects.equals(technicalId, person.technicalId) &&
                Objects.equals(maritalId, person.maritalId) &&
                Objects.equals(sexId, person.sexId) &&
                Objects.equals(regionId, person.regionId) &&
                Objects.equals(houseHoldId, person.houseHoldId) &&
                Objects.equals(personStatus, person.personStatus) &&
                Objects.equals(educationLevel, person.educationLevel) &&
                Objects.equals(invalidId, person.invalidId) &&
                Objects.equals(exportStatus, person.exportStatus) &&
                Objects.equals(changeDate, person.changeDate) &&
                Objects.equals(startDate, person.startDate) &&
                Objects.equals(endDate, person.endDate) &&
                Objects.equals(notes, person.notes) &&
                Objects.equals(motherId, person.motherId) &&
                Objects.equals(birthNumber, person.birthNumber) &&
                Objects.equals(generateId, person.generateId) &&
                Objects.equals(isDeleted, person.isDeleted) &&
                Objects.equals(timeCreated, person.timeCreated) &&
                Objects.equals(timeLastUpdated, person.timeLastUpdated) &&
                Objects.equals(userCreated, person.userCreated) &&
                Objects.equals(userLastUpdated, person.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId, firstName, lastName, dateOfBirth, placeOfBirth, nationalityCode, relationId, ethnicId, residenceId, educationId, technicalId, maritalId, sexId, regionId, houseHoldId, personStatus, educationLevel, invalidId, exportStatus, changeDate, startDate, endDate, notes, motherId, birthNumber, generateId, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
