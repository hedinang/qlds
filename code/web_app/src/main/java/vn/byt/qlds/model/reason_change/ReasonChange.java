package vn.byt.qlds.model.reason_change;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReasonChange implements Serializable {
    private String changeTypeCode;
    private String changeTypeName;
    private Integer isShow;
    private String changeCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Integer id;


    public ReasonChange(ReasonChangeRequest request) {
        createFromRequest(request);
    }

    public ReasonChange createFromRequest(ReasonChangeRequest request) {
        this.changeTypeCode = request.changeTypeCode;
        this.changeTypeName = request.changeTypeName;
        this.isActive = request.isActive;
        return this;
    }
}
