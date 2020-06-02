package vn.byt.qlds.model._province.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonHealthy {
    private Integer id;
    private Integer generateId;
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
    private Timestamp createdDate;
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

    public PersonHealthy(PersonHealthyRequest request) {
        createFromRequest(request);
    }

    public PersonHealthy createFromRequest(PersonHealthyRequest request) {
        this.personalId = request.personalId;
        this.genDate = request.genDate;
        this.birthNumber = request.birthNumber;
        this.regionId = request.regionId;
        this.placeOfBirth = request.placeOfBirth;
        this.generateCode = request.generateCode;
        return this;
    }
}
