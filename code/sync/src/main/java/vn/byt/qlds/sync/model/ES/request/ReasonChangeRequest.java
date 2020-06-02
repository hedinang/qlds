package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReasonChangeRequest {
    private Integer id;
    private String changeTypeCode;
    private String changeTypeName;
    private Boolean isActive;
    private String changeCodeOld;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;

}
