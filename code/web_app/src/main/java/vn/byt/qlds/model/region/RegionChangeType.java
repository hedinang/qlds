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
public class RegionChangeType {
    private String changeTypeCode;
    private String changeTypeDesc;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
}
