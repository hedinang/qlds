package vn.byt.qlds.model.collaborator;


import javax.validation.constraints.NotNull;

public class CollaboratorRequest {
    @NotNull(message = "Bad request ma")
    public String ma;
    @NotNull(message = "Bad request firstName")
    public String firstName;
    @NotNull(message = "Bad request lastName")
    public String lastName;
    public String hireDate;
    public String endDate;
    public Integer sexId;
    @NotNull(message = "Bad request regionId")
    public String regionId;
    @NotNull(message = "Bad request")
    public Boolean isActive;
    public Boolean isCadre;
    public Long stt;
}
