package vn.byt.qlds.model._province.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private Integer collaboratorId;
    private String name;
    private String levels; // là cấp 1 hay không
    private String regionId;
    private String notes;
    private String fullName;
}
