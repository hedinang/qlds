package vn.byt.qlds.model.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferHouseHold implements Serializable {
    private Integer id;
    private Integer houseHoldId;
    private String houseHoldName;
    private Integer addressIdOld;
    private String addressOld;
    private Integer addressIdNew;
    private String addressNew;
    private String regionIdOld;
    private String regionIdNew;
    private Integer status;
    private Timestamp startDate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private String description;

    public TransferHouseHold(TransferHouseHoldRequest request){
       createFromRequest(request);
    }

    public TransferHouseHold createFromRequest(TransferHouseHoldRequest request){
        this.houseHoldId = request.houseHoldId;
        this.houseHoldName = request.houseHoldName;
        this.addressIdOld  = request.addressIdOld;
        this.addressOld  = request.addressOld;
        this.addressIdNew  = request.addressIdNew;
        this.addressNew  = request.addressNew;
        this.regionIdOld  = request.regionIdOld;
        this.regionIdNew  = request.regionIdNew;
        this.startDate  = request.startDate;
        return this;
    }

}
