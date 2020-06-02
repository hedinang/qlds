package vn.byt.qlds.sync.model.ES.request;

public class PermissionRequest {
    public Integer id;
    public String name;
    public String code;
    public Integer parent;
    public Boolean isParent;
    public Boolean isDeleted;
    public Boolean isActive;
    public Long userCreated;
    public Long userLastUpdated;
    public Long timeCreated;
    public Long timeLastUpdated;
}
