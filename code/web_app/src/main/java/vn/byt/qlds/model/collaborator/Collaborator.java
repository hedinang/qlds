package vn.byt.qlds.model.collaborator;

import vn.byt.qlds.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator implements Serializable {
    private Integer id;
    private Integer collaboratorId;
    private String ma;
    private String firstName;
    private String lastName;
    private Timestamp hireDate;
    private String regionId;
    private String sexId;
    private Boolean isCadre;
    private Boolean exportStatus;
    private Boolean isDeleted;
    private Long stt;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Boolean isActive;
    private Timestamp endDate;

    public Collaborator(CollaboratorRequest request) throws ParseException {
        createFromRequest(request);
    }

    public Collaborator createFromRequest(CollaboratorRequest request) throws ParseException {
        this.ma = request.ma;
        this.firstName = request.firstName.toUpperCase();
        this.lastName = request.lastName.toUpperCase();
        this.hireDate = StringUtils.convertStringToTimestamp(request.hireDate, "dd/MM/yyyy");
        this.endDate = StringUtils.convertStringToTimestamp(request.endDate, "dd/MM/yyyy");
        this.sexId = String.valueOf(request.sexId);
        this.isCadre = request.isCadre;
        this.isActive = request.isActive;
        this.regionId = request.regionId;
        this.stt = request.stt;
        return this;
    }
}
