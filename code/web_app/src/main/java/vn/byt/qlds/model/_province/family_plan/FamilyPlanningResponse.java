package vn.byt.qlds.model._province.family_plan;

public class FamilyPlanningResponse {
    private Integer personalId;
    private String contraDate;
    private String contraceptiveCode;
    private Boolean exportStatus;
    private String regionId;

    public FamilyPlanningResponse() {
    }

    public FamilyPlanningResponse(Integer personalId, String contraDate, String contraceptiveCode, Boolean exportStatus, String regionId) {
        this.personalId = personalId;
        this.contraDate = contraDate;
        this.contraceptiveCode = contraceptiveCode;
        this.exportStatus = exportStatus;
        this.regionId = regionId;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getContraDate() {
        return contraDate;
    }

    public void setContraDate(String contraDate) {
        this.contraDate = contraDate;
    }

    public String getContraceptiveCode() {
        return contraceptiveCode;
    }

    public void setContraceptiveCode(String contraceptiveCode) {
        this.contraceptiveCode = contraceptiveCode;
    }

    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
}
