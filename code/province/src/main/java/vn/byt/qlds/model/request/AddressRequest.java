package vn.byt.qlds.model.request;

public class AddressRequest {
    private Integer id;
    private Integer collaboratorId;
    private String name;
    private String levels;
    private String parent;
    private String regionId;
    private String notes;
    private String fullName;
    private Byte exportStatus;
    private Integer userId;
    private Integer addressIdOld;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCollaboratorId() {
        return collaboratorId;
    }

    public void setCollaboratorId(Integer collaboratorId) {
        this.collaboratorId = collaboratorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Byte getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Byte exportStatus) {
        this.exportStatus = exportStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressIdOld() {
        return addressIdOld;
    }

    public void setAddressIdOld(Integer addressIdOld) {
        this.addressIdOld = addressIdOld;
    }
}
