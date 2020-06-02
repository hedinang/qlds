package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollaboratorRequest {
    public Integer id;
    public Integer collaboratorId;
    public String ma;
    public String firstName;
    public String lastName;
    public String fullName;
    public Long hireDate;
    public String regionId;
    public String districtId;
    public String provinceId;
    public String sexId;
    public Boolean isCadre;
    public Boolean exportStatus;
    public Boolean isDeleted;
    public Long stt;
    public Long userCreated;
    public Long userLastUpdated;
    public Long timeCreated;
    public Long timeLastUpdated;
    public Boolean active;
    public Long endDate;
}
