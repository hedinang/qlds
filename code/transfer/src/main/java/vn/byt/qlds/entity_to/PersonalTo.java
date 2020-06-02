package vn.byt.qlds.entity_to;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "personal")
public class PersonalTo {
    private int id;
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
    private Integer invalidId;
    private Boolean exportStatus;
    private Timestamp changeDate;
    private Timestamp startDate;
    private Timestamp endDate;
    private String notes;
    private Integer motherId;
    private String birthNumber;
    private Integer generateId;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "date_of_birth")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    @Column(name = "nationality_code")
    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    @Basic
    @Column(name = "relation_id")
    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    @Basic
    @Column(name = "ethnic_id")
    public Integer getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(Integer ethnicId) {
        this.ethnicId = ethnicId;
    }

    @Basic
    @Column(name = "residence_id")
    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
    }

    @Basic
    @Column(name = "education_id")
    public Integer getEducationId() {
        return educationId;
    }

    public void setEducationId(Integer educationId) {
        this.educationId = educationId;
    }

    @Basic
    @Column(name = "technical_id")
    public Integer getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(Integer technicalId) {
        this.technicalId = technicalId;
    }

    @Basic
    @Column(name = "marital_id")
    public Integer getMaritalId() {
        return maritalId;
    }

    public void setMaritalId(Integer maritalId) {
        this.maritalId = maritalId;
    }

    @Basic
    @Column(name = "sex_id")
    public Integer getSexId() {
        return sexId;
    }

    public void setSexId(Integer sexId) {
        this.sexId = sexId;
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
    @Column(name = "houseHold_id")
    public Integer getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(Integer houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Basic
    @Column(name = "person_status")
    public String getPersonStatus() {
        return personStatus;
    }

    public void setPersonStatus(String personStatus) {
        this.personStatus = personStatus;
    }

    @Basic
    @Column(name = "education_level")
    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    @Basic
    @Column(name = "invalid_id")
    public Integer getInvalidId() {
        return invalidId;
    }

    public void setInvalidId(Integer invalidId) {
        this.invalidId = invalidId;
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
    @Column(name = "change_date")
    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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
    @Column(name = "mother_id")
    public Integer getMotherId() {
        return motherId;
    }

    public void setMotherId(Integer motherId) {
        this.motherId = motherId;
    }

    @Basic
    @Column(name = "birth_number")
    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }

    @Basic
    @Column(name = "generate_id")
    public Integer getGenerateId() {
        return generateId;
    }

    public void setGenerateId(Integer generateId) {
        this.generateId = generateId;
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
    @Column(name = "time_last_updated")
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
        PersonalTo that = (PersonalTo) o;
        return id == that.id &&
                personalId == that.personalId &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(placeOfBirth, that.placeOfBirth) &&
                Objects.equals(nationalityCode, that.nationalityCode) &&
                Objects.equals(relationId, that.relationId) &&
                Objects.equals(ethnicId, that.ethnicId) &&
                Objects.equals(residenceId, that.residenceId) &&
                Objects.equals(educationId, that.educationId) &&
                Objects.equals(technicalId, that.technicalId) &&
                Objects.equals(maritalId, that.maritalId) &&
                Objects.equals(sexId, that.sexId) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(houseHoldId, that.houseHoldId) &&
                Objects.equals(personStatus, that.personStatus) &&
                Objects.equals(educationLevel, that.educationLevel) &&
                Objects.equals(invalidId, that.invalidId) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(changeDate, that.changeDate) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(motherId, that.motherId) &&
                Objects.equals(birthNumber, that.birthNumber) &&
                Objects.equals(generateId, that.generateId) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalId, firstName, lastName, dateOfBirth, placeOfBirth, nationalityCode, relationId, ethnicId, residenceId, educationId, technicalId, maritalId, sexId, regionId, houseHoldId, personStatus, educationLevel, invalidId, exportStatus, changeDate, startDate, endDate, notes, motherId, birthNumber, generateId, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
