package vn.byt.qlds.model._province.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonHistory {
    private Integer id;
    private Integer changeId;
    private String regionId;
    private Integer personalId;
    private Timestamp dateUpdate;
    private Timestamp changeDate;
    private String changeTypeCode;
    private String source;
    private String destination;
    private String status;
    private String notes;
    private Integer userId;
    private Boolean exportStatus;
    private Timestamp dieDate;
    private Timestamp goDate;
    private Timestamp comeDate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    public PersonHistory(PersonHistoryRequest request){
        createFromRequest(request);
    }

    public PersonHistory createFromRequest(PersonHistoryRequest request){
        this.changeTypeCode = request.changeTypeCode;
        this.personalId = request.personalId;
        this.regionId = request.regionId;
        this.destination = request.destination;
        return this;

    }
}
