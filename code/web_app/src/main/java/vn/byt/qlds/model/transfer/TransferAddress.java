package vn.byt.qlds.model.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferAddress {
    private Integer id;
    private Integer addressOld;
    private String regionOld;
    private String regionNew;
    private Integer level;
    private Integer status;
    private Boolean isOutside;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    public TransferAddress(TransferAddressRequest request) {
        creatFromRequest(request);
    }

    public TransferAddress creatFromRequest(TransferAddressRequest request) {
        this.addressOld = request.addressOld;
        this.regionOld = request.regionOld;
        this.regionNew = request.regionNew;
        this.level = request.level;
        this.isOutside = request.isOutside;
        return this;
    }
}
