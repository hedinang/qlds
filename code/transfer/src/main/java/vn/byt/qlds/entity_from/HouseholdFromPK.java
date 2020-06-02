package vn.byt.qlds.entity_from;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class HouseholdFromPK implements Serializable {
    private int houseHoldId;
    private String regionId;

    @Column(name = "HouseHold_ID")
    @Id
    public int getHouseHoldId() {
        return houseHoldId;
    }

    public void setHouseHoldId(int houseHoldId) {
        this.houseHoldId = houseHoldId;
    }

    @Column(name = "Region_ID")
    @Id
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HouseholdFromPK that = (HouseholdFromPK) o;
        return houseHoldId == that.houseHoldId &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(houseHoldId, regionId);
    }
}
