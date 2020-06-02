package vn.byt.qlds.model.education;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationCategory implements Serializable {
    private int id;
    private String code;
    private String name;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public EducationCategory(EducationCategoryRequest request) {
        createFromRequest(request);
    }

    public EducationCategory createFromRequest(EducationCategoryRequest request) {
        this.code = request.code;
        this.name = request.name;
        this.isActive = request.isActive;
        return this;
    }
}
