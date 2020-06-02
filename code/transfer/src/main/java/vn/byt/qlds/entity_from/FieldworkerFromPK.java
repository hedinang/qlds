package vn.byt.qlds.entity_from;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class FieldworkerFromPK implements Serializable {
    private Integer fieldWorkerId;
    private String regionId;

    @Column(name = "FieldWorker_ID")
    @Id
    public Integer getFieldWorkerId() {
        return fieldWorkerId;
    }

    public void setFieldWorkerId(Integer fieldWorkerId) {
        this.fieldWorkerId = fieldWorkerId;
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
        FieldworkerFromPK that = (FieldworkerFromPK) o;
        return fieldWorkerId == that.fieldWorkerId &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldWorkerId, regionId);
    }
}
