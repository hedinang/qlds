package vn.byt.qlds.model._province.house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HouseHoldRequest {
    public String houseHoldCode;
    public String houseHoldNumber;
    public Integer addressId;
    public String regionId;
    public Boolean isBigHouseHold;
    public Timestamp startDate;
    @Nullable
    public String houseHoldIdOld;
    @Nullable
    public String houseHoldStatus;
}
