package vn.byt.qlds.model.separation_house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeparationHouseHold {
    private int id;
    private Integer personId;
    private Integer houseHoldIdOld;
    private Integer addressIdOld;
    private Integer addressIdNew;
    private String regionIdOld;
    private String regionIdNew;
    private Integer houseHoldIdNew;
    private String houseHoldCodeNew;
    private String householdNumber;
    private Boolean isBigHouseHold;
    private Timestamp startDate;
    private String description;
    private Integer status;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    public SeparationHouseHold(SeparationHouseHoldRequest request) {
        createFromRequest(request);
    }

    public SeparationHouseHold createFromRequest(SeparationHouseHoldRequest request) {
        this.personId = request.personId;
        this.houseHoldIdOld = request.houseHoldIdOld;
        this.addressIdOld = request.addressIdOld;
        this.addressIdNew = request.addressIdNew;
        this.regionIdOld = request.regionIdOld;
        this.regionIdNew = request.regionIdNew;
        this.houseHoldIdNew = request.houseHoldIdNew;
        this.houseHoldCodeNew = request.houseHoldCodeNew;
        this.householdNumber = request.householdNumber;
        this.isBigHouseHold = request.isBigHouseHold;
        this.startDate = request.startDate;
        this.description = request.description;
        return this;
    }
}
