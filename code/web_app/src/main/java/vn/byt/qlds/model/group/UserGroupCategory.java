package vn.byt.qlds.model.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupCategory {
    private int id;
    private String groupName;
    private Integer levelId;
    private Boolean isActive;
    private String note;
    private Boolean isDeleted;
    private Long userCreated;
    private long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public UserGroupCategory(UserGroupCategoryRequest request) {
        createFromRequest(request);
    }

    public UserGroupCategory createFromRequest(UserGroupCategoryRequest request) {
        this.groupName = request.groupName;
        this.levelId = request.levelId;
        this.note = request.note;
        this.isActive = request.isActive;
        return this;
    }

}
