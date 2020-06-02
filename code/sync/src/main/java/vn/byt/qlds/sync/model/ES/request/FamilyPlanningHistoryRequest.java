package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyPlanningHistoryRequest {
    /*data entity*/
    private int fpHistoryId;
    private String regionId;
    private int personalId;
    private String contraDate;
    private String contraceptiveCode;
    private Integer userId;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Long dateUpdate;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private Integer contraceptiveId;
    /*popular data*/
    private ContraceptiveCategoryRequest contraceptiveCategoryRequest;
    public UnitCategoryRequest unitCategoryRequest;
    public PersonRequest personRequest;
}
