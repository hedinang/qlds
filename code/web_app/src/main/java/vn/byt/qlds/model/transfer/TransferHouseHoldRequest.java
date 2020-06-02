package vn.byt.qlds.model.transfer;

import java.sql.Timestamp;

public class TransferHouseHoldRequest {
    public Integer houseHoldId;
    public String houseHoldName;
    public Integer addressIdOld;
    public String addressOld;
    public Integer addressIdNew;
    public String addressNew;
    public String regionIdOld;
    public String regionIdNew;
    public Timestamp startDate;
    public String description;
}
