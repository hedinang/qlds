package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FamilyPlanRequest {
    private Integer personalId;
    private String contraceptiveCode;
    private Boolean exportStatus;
    private String regionId;
    private Integer userId;
    private Boolean isDeleted;
    private String contraDate;
    private Long dateUpdate;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
