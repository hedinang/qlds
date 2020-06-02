package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRequest {
    private Integer id;
    private String areaName;
    private Boolean isActive;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
}
