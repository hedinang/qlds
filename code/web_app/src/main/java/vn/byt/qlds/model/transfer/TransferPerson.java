package vn.byt.qlds.model.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferPerson {
    private Integer id;
    private String personId;
    private String personName;
    private String oldAddress;
    private String oldRegionId;
    private String newRegionId;
    private String newAddress;
    private Integer houseHoldId;
    private String houseHoldName;
    private String description;
    private Integer status;
    private Timestamp timeRequireSeparate;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;


    public TransferPerson(TransferPersonRequest request) {
        createFromRequest(request);
    }

    public TransferPerson createFromRequest(TransferPersonRequest request) {
        this.personId = request.personId;
        this.personName = request.personName;
        this.oldAddress = request.oldAddress;
        this.oldRegionId = request.oldRegionId;
        this.newRegionId = request.newRegionId;
        this.newAddress = request.newAddress;
        this.houseHoldId = request.houseHoldId;
        this.houseHoldName = request.houseHoldName;
        this.description = request.description;
        this.timeRequireSeparate = request.timeRequireSeparate;
        return this;
    }
}
