package vn.byt.qlds.model.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferHouseHoldResponse {
    public Integer id;
    public Integer houseHoldId;
    public String houseHoldName;
    public Integer addressIdOld;
    public String addressOld;
    public Integer addressIdNew;
    public String addressNew;
    public String regionIdOld;
    public String regionIdNew;
    public Integer status;
    public String description;
    public String startDate;
}
