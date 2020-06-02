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
public class UnitLevelCategory {
    public Integer id;
    public Integer levelCode;
    public String levelName;
    public Boolean isDeleted;
    private Boolean isActive;
    public Long userCreated;
    public Long userLastUpdated;
    public Timestamp timeCreated;
    public Timestamp timeLastUpdated;
    public String note;

    public UnitLevelCategory(UnitLevelCategoryRequest request) {
        createFromRequest(request);
    }

    public UnitLevelCategory createFromRequest(UnitLevelCategoryRequest request) {
        this.levelName = request.levelName;
        this.note = request.note;
        this.isActive = request.isActive;
        return this;
    }
}
