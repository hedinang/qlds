package vn.byt.qlds.model.ethnic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NationCategory {
    private int id;
    private String code;
    private String name;
    private String notes;
    private String codeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Boolean isMinority;

    public NationCategory(NationCategoryRequest request) {
        createFromRequest(request);
    }

    public NationCategory createFromRequest(NationCategoryRequest request) {
        this.code = request.code;
        this.name = request.name;
        this.notes = request.notes;
        this.isMinority = request.isMinority;
        this.isActive = request.isActive;
        return this;
    }
}
