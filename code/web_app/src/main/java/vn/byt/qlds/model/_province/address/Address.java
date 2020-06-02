package vn.byt.qlds.model._province.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private Integer collaboratorId;
    private String name;
    private String levels;
    private String parent;
    private String regionId;
    private String notes;
    private String fullName;
    private Boolean exportStatus;
    private Integer userId;
    private Integer addressIdOld;
    private Timestamp dateUpdate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private String districtId;
    private String provinceId;

    //todo validator
    public Address(AddressRequest request) {
        createFromRequest(request);
    }

    public Address createFromRequest(AddressRequest request) {
        this.collaboratorId = request.getCollaboratorId();
        this.name = request.getName();
        this.levels = request.getLevels();
        this.regionId = request.getRegionId();
        this.notes = request.getNotes();
        this.fullName = request.getFullName();
        return this;
    }
}
