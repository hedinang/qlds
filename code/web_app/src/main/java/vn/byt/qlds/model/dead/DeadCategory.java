package vn.byt.qlds.model.dead;

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
public class DeadCategory implements Serializable {
    private int id;
    private String code;
    private String name;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public DeadCategory(DeadRequest request) {
        createFromRequest(request);
    }

    public DeadCategory createFromRequest(DeadRequest request) {
        this.code = request.code;
        this.name = request.name;
        this.isActive = request.isActive;
        return this;
    }
}
