package vn.byt.qlds.model._province.family_plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyPlanningHistory {
    private Integer id;
    private Integer fpHistoryId;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private String contraDate;
    private String contraceptiveCode;
    private Integer userId;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private Integer contraceptiveId;

    public FamilyPlanningHistory(FamilyPlanningHistoryRequest request){
        createFromRequest(request);
    }

    public FamilyPlanningHistory createFromRequest(FamilyPlanningHistoryRequest request) {
        this.contraceptiveCode  = request.contraceptiveCode;
        this.personalId  = request.personalId;
        this.regionId  = request.regionId;
        this.contraDate  = request.contraDate;
        this.contraceptiveId  = request.contraceptiveId;
        return this;
    }
}
