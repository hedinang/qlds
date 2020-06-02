package vn.byt.qlds.model.separation_house;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SeparationHouseHoldResponse {
    public SeparationHouseHold separationHouseHold;
    public String personName;
    public String houseHoldOldName;
    public String houseHoldNewName;
    public String addressOldName;
    public String addressNewName;
    public String regionNewName;
    public String regionOldName;
}
