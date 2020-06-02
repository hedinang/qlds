package vn.byt.qlds.model.region;

public class RegionChangeTypeResponse {
    private String changeTypeCode;
    private String changeTypeDesc;

    public RegionChangeTypeResponse() {
    }

    public RegionChangeTypeResponse(String changeTypeCode, String changeTypeDesc) {
        this.changeTypeCode = changeTypeCode;
        this.changeTypeDesc = changeTypeDesc;
    }

    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    public String getChangeTypeDesc() {
        return changeTypeDesc;
    }

    public void setChangeTypeDesc(String changeTypeDesc) {
        this.changeTypeDesc = changeTypeDesc;
    }
}
