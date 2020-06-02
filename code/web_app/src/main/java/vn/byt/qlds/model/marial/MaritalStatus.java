package vn.byt.qlds.model.marial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatus {
    private int maritalCode;
    private String maritalName;
    private int maritalCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public MaritalStatus(MaritalStatusRequest request) {
        createFromRequest(request);
    }

    public MaritalStatus createFromRequest(MaritalStatusRequest request) {
        this.maritalName = request.maritalName;
        this.isActive = request.isActive;
        return this;
    }
}
