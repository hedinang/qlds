package vn.byt.qlds.sync.model.ES.request;

import java.sql.Timestamp;

public class SeparationHouseHoldRequest {
    public int id;
    public Integer personId;
    public Integer houseHoldIdOld;
    public Integer addressIdOld;
    public Integer addressIdNew;
    public String regionIdOld;
    public String regionIdNew;
    public Integer status;
    public Boolean isDeleted;
    public Long timeCreated;
    public Long timeLastUpdated;
    public Long userCreated;
    public Long userLastUpdated;
    public String description;
    public Integer houseHoldIdNew;
    public String houseHoldCodeNew;
    public Long startDate;
    public String householdNumber;
    public Boolean isBigHouseHold;
}
