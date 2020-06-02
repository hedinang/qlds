package vn.byt.qlds.model._province.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Person {
    private Integer id;
    private Integer personalId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String placeOfBirth;
    private Integer nationalityCode;
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

    public Person(PersonRequest request){
        createFromRequest(request);
    }

    public Person createFromRequest(PersonRequest request){
        this.firstName = request.firstName;
        this.lastName = request.lastName;
        this.sexId = request.sexId;
        this.ethnicId = request.ethnicId;
        this.relationId = request.relationId;
        this.dateOfBirth = request.dateOfBirth;
        this.maritalId = request.maritalId;
        this.residenceId = request.residenceId;
        this.educationId = request.educationId;
        this.technicalId = request.technicalId;
        this.houseHoldId = request.houseHoldId;
        this.regionId = request.regionId;
        this.nationalityCode = 704; // code VN
        return this;
    }
}
