package vn.byt.qlds.model.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionChange {
    private int id;
    private String changeDate;
    private String regionId;
    private String regionName;
    private String regionOld;
    private String parent;
    private String changeType;
    private String changeDesc;
    private String status;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public RegionChange(RegionChangeRequest request) {
        createFromRequest(request);
    }

    public RegionChange createFromRequest(RegionChangeRequest request) {
        this.regionId = request.regionId;
        this.regionName = request.regionName;
        return this;
    }

}
