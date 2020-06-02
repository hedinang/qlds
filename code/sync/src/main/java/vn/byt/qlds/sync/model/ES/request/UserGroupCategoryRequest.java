package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupCategoryRequest {
    private Integer id;
    private String groupName;
    private Integer unitId;
    private Integer levelId;
    private Boolean isActive;
    private String note;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
}
