package vn.byt.qlds.model.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitCategory {
    private Integer id;
    private String code;
    private String name;
    private String parent;
    private String zone;
    private Integer levels;
    private String note;
    private String area;
    private String briefName;
    private Integer isActive;
    private Integer selected;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;


    public UnitCategory(UnitCategoryRequest request) {
        createFromRequest(request);
    }

    public UnitCategory createFromRequest(UnitCategoryRequest request) {
        this.name = request.name;
        this.briefName = request.briefName;
        this.code = request.code;
        this.levels = request.levels;
        this.parent = request.parent;
        this.zone = request.zone;
        this.area = request.area;
        this.note = request.note;
        this.isActive = request.isActive;
        return this;
    }

}
