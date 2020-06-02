package vn.byt.qlds.model.nationlity;

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
public class Nationality implements Serializable {
    private int nationalityCode;
    private String nationalityName;
    private String countryName;
    private String nationalityCodeOld;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

    public Nationality(NationalityRequest request) {
        createFromRequest(request);
    }

    public Nationality createFromRequest(NationalityRequest request) {
        this.nationalityName = request.nationalityName;
        this.countryName = request.countryName;
        this.isActive = request.isActive;
        return this;
    }
}
