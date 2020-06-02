package vn.byt.qlds.model.technical;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalCategory {
    private String technicalCode;
    private String technicalName;
    private String technicalCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Integer id;

    public TechnicalCategory(TechnicalCategoryRequest request) {
        createFromRequest(request);
    }

    public TechnicalCategory createFromRequest(TechnicalCategoryRequest request) {
        this.technicalCode = request.technicalCode;
        this.technicalName = request.technicalName;
        this.isActive = request.isActive;
        return this;
    }

}
