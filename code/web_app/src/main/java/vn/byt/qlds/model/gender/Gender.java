package vn.byt.qlds.model.gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {
    private int id;
    private String name;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public Gender(GenderRequest request){
        createFromRequest(request);
    }

    public Gender createFromRequest(GenderRequest request){
        this.name = request.genderName;
        return this;
    }
}
