package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TransferHouseHoldRequest {
    private int id;
    private Integer houseHoldId;
    private String houseHoldName;
    private Integer addressIdOld;
    private String addressOld;
    private Integer addressIdNew;
    private String addressNew;
    private String regionIdOld;
    private String regionIdNew;
    private Integer status;
    private String description;
    private Long startDate;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
