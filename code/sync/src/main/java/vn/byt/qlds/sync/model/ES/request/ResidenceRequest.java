package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidenceRequest {
    public int residenceCode;
    public String residenceName;
    public String residenceCodeOld;
    public Boolean isDeleted;
    private Boolean isActive;
    public Long userCreated;
    public Long userLastUpdated;
    public Long timeCreated;
    public Long timeLastUpdated;
}
