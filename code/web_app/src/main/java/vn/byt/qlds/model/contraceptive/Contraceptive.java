package vn.byt.qlds.model.contraceptive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contraceptive {
    private int id;
    private String code;
    private String name;
    private String codeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public Contraceptive(ContraceptiveRequest request) {
        createFromRequest(request);
    }

    public Contraceptive createFromRequest(ContraceptiveRequest request) {
        this.code = request.code;
        this.name = request.name;
        this.isActive = request.isActive;

        return this;
    }
}
