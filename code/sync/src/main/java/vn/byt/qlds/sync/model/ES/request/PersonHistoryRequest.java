package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonHistoryRequest {
    private Integer changeId;
    private String regionId;
    private Integer personalId;
    private Long dateUpdate;
    private Long changeDate;
    private String changeTypeCode;
    private String source;
    private String destination;
    private String status;
    private String notes;
    private Integer userId;
    private Boolean exportStatus;
    private Long dieDate;
    private Long goDate;
    private Long comeDate;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private Integer updateInAge;
    public PersonRequest personRequest;
}
