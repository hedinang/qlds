package vn.byt.qlds.model.transfer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransferAddressRequest {
    public Integer addressOld;
    public String regionOld;
    public String regionNew;
    public Integer level;
    public Integer status;
    public Boolean isOutside;
}
