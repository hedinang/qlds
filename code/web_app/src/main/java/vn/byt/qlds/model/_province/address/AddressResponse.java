package vn.byt.qlds.model._province.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    public Address address;
    public String collaboratorName;
    //    public String districtId;
    //    public String provinceId;
}
