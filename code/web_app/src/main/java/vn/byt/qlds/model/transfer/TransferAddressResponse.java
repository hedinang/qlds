package vn.byt.qlds.model.transfer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferAddressResponse {
    private TransferAddress transferAddress;
    private String timeCreated;
    private String addressOldName;
    private String regionOldName;
    private String regionNewName;
}
