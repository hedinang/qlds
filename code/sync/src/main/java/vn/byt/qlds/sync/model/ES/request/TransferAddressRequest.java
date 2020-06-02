package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferAddressRequest {
    private int id;
    private Integer addressOld;
    private String regionOld;
    private String regionNew;
    private Integer level;
    private Integer status;
    private Boolean isOutside;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
