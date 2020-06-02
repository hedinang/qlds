package vn.byt.qlds.model._province.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseHold {
    private Integer id;
    private Integer houseHoldId;
    private String houseHoldCode;
    private Integer addressId;
    private String regionId;
    private String houseHoldNumber;
    private Boolean exportStatus;
    private Integer userId;
    private String houseHoldIdOld;
    private Timestamp startDate;
    private Timestamp endDate;
    private Boolean isBigHouseHold;
    private String notes;
    private String houseHoldStatus;
    private Boolean isChecked;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    public HouseHold(HouseHoldRequest request) {
        createFromRequest(request);
    }

    public HouseHold createFromRequest(HouseHoldRequest request) {
        this.houseHoldCode = request.houseHoldCode;
        this.houseHoldNumber = request.houseHoldNumber;
        this.addressId = request.addressId;
        this.regionId = request.regionId;
        this.startDate = request.startDate;
        this.isBigHouseHold = request.isBigHouseHold;
        this.houseHoldIdOld = request.houseHoldIdOld;
        this.houseHoldStatus = request.houseHoldStatus;
        return this;
    }
}
