package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HouseHoldRequest {
    private int houseHoldId;
    private String houseHoldCode;
    private Integer addressId;
    private String regionId;
    private String houseHoldNumber;
    private Boolean exportStatus;
    private Integer userId;
    private String houseHoldIdOld;
    private Long startDate;
    private Long endDate;
    private Boolean isBigHouseHold;
    private String notes;
    private String houseHoldStatus;
    private Boolean isChecked;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
