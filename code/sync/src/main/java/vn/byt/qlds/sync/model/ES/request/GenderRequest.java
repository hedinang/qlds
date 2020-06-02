package vn.byt.qlds.sync.model.ES.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GenderRequest {
    private int id;
    private String name;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Long timeCreated;
    private Long timeLastUpdated;
}
