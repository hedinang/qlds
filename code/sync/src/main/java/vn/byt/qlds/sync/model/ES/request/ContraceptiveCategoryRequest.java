package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContraceptiveCategoryRequest {
    private int id;
    private String code;
    private String name;
    private String codeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
}
