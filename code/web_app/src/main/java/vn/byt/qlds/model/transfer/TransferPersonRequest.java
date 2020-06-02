package vn.byt.qlds.model.transfer;

import java.sql.Timestamp;

public class TransferPersonRequest {
    public String personId;
    public String personName;
    public String oldAddress;
    public String newAddress;
    public String oldRegionId;
    public String newRegionId;
    public Integer houseHoldId;
    public String houseHoldName;
    public Integer status;
    public String description;
    public Timestamp timeRequireSeparate;
}
