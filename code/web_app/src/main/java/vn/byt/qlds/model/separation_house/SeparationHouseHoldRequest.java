package vn.byt.qlds.model.separation_house;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
public class SeparationHouseHoldRequest implements Serializable {
    public Integer personId;
    public Integer houseHoldIdOld;
    public Integer addressIdOld;
    public Integer addressIdNew;
    public String regionIdOld;
    public String regionIdNew;
    public String description;
    @Nullable
    public Integer houseHoldIdNew;
    public String houseHoldCodeNew;
    public Timestamp startDate;
    public String householdNumber;
    public Boolean isBigHouseHold;
}