package vn.byt.qlds.entity_from;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AddressFromPK implements Serializable {
    private int addressId;
    private String regionId;

    @Column(name = "Address_ID")
    @Id
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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
        AddressFromPK that = (AddressFromPK) o;
        return addressId == that.addressId &&
                Objects.equals(regionId, that.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, regionId);
    }
}
