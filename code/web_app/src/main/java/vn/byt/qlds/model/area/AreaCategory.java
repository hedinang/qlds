package vn.byt.qlds.model.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaCategory {
    private int id;
    private String areaName;
    private Boolean isActive;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public AreaCategory(AreaCategoryRequest request) {
        createFromRequest(request);
    }

    public AreaCategory createFromRequest(AreaCategoryRequest request) {
        this.areaName = request.areaName;
        this.isActive = request.isActive;
        return this;
    }

}
