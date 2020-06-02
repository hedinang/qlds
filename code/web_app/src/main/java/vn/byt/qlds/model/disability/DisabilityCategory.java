package vn.byt.qlds.model.disability;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DisabilityCategory implements Serializable {
    private int id;
    private String code;
    private String name;
    private String codeOld;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;


    public DisabilityCategory(DisabilityRequest request) {
        createFromRequest(request);
    }

    public DisabilityCategory createFromRequest(DisabilityRequest request) {
        this.code = request.code;
        this.name = request.name;
        this.isDeleted = request.isDeleted;
        return this;
    }

}
