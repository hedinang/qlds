package vn.byt.qlds.sync.model.ES.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicalCategoryRequest {
    private String technicalCode;
    private String technicalName;
    private String technicalCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
    private int id;
}
