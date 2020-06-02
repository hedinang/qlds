package vn.byt.qlds.model.relationship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Relationship {
    private String relationCode;
    private String relationName;
    private String relationCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private int id;

    public Relationship(RelationshipRequest request) {
        createFromRequest(request);
    }

    public Relationship createFromRequest(RelationshipRequest request) {
        this.relationCode = request.relationCode;
        this.relationName = request.relationName;
        this.isActive = request.isActive;
        return this;
    }
}
