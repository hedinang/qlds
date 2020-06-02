package vn.byt.qlds.entity_from;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PersonalFromPK implements Serializable {
    private int personalId;
    private String regionId;

    @Column(name = "Personal_ID")
    @Id
    public int getPersonalId() {
        return personalId;
    }

    public void setPersonalId(int personalId) {
        this.personalId = personalId;
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
        PersonalFromPK that = (PersonalFromPK) o;
        return personalId == that.personalId &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personalId, regionId);
    }
}
