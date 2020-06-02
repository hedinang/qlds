package vn.byt.qlds.entity_from;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FamilyplanninghistoryFromPK implements Serializable {
    private int fpHistoryId;
    private String regionId;

    @Column(name = "FPHistory_ID")
    @Id
    public int getFpHistoryId() {
        return fpHistoryId;
    }

    public void setFpHistoryId(int fpHistoryId) {
        this.fpHistoryId = fpHistoryId;
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
        FamilyplanninghistoryFromPK that = (FamilyplanninghistoryFromPK) o;
        return fpHistoryId == that.fpHistoryId &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fpHistoryId, regionId);
    }
}
