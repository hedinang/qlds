package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PersonRequest implements Serializable {
    private Integer personalId;
    private String firstName;
    private String lastName;
    private String fullName;
    private Long dateOfBirth;
    private Long dateOfBirthMother;
    private String placeOfBirth;
    private Integer nationalityCode;
    private Integer relationId;
    private Integer ethnicId;
    private Integer residenceId;
    private Integer educationId;
    private Integer technicalId;
    private Integer maritalId;
    private Integer sexId;
    private String addressId;
    private String regionId;
    private String districtId;
    private String provinceId;
    private Integer houseHoldId;
    private Integer generateId; // personHealthyRequest tuong duong voi generateID
    private String personStatus;
    private String educationLevel;
    private String invalidId;
    private Boolean exportStatus;
    private Long changeDate;
    private Long startDate;
    private Long endDate;
    private String notes;
    private Integer motherId;
    private Boolean birthNumber;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
