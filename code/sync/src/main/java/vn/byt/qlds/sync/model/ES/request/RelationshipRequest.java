package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationshipRequest {
    private String relationCode;
    private String relationName;
    private String relationCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
    private int id;
}
