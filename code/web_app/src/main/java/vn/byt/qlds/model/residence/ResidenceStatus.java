package vn.byt.qlds.model.residence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResidenceStatus {
    private int residenceCode;
    private String residenceName;
    private String residenceCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public ResidenceStatus(ResidenceStatusRequest request) {
        createFromRequest(request);
    }

    public ResidenceStatus createFromRequest(ResidenceStatusRequest request) {
        this.residenceName = request.residenceName;
        this.isActive = request.isActive;
        return this;
    }
}
