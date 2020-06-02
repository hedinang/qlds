package vn.byt.qlds.model.report;

public class ReportResponse {
    private String rcId;
    private String rcName;
    private String levels;
    private String rptName;
    private String rcType;
    private String procName;
    private String paras;
    private String showMonth;
    private String showQuarter;
    private String showYear;
    private String showFromdate;
    private String showTodate;

    public ReportResponse() {
    }

    public ReportResponse(String rcId, String rcName, String levels, String rptName, String rcType, String procName, String paras, String showMonth, String showQuarter, String showYear, String showFromdate, String showTodate) {
        this.rcId = rcId;
        this.rcName = rcName;
        this.levels = levels;
        this.rptName = rptName;
        this.rcType = rcType;
        this.procName = procName;
        this.paras = paras;
        this.showMonth = showMonth;
        this.showQuarter = showQuarter;
        this.showYear = showYear;
        this.showFromdate = showFromdate;
        this.showTodate = showTodate;
    }

    public String getRcId() {
        return rcId;
    }

    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    public String getRcName() {
        return rcName;
    }

    public void setRcName(String rcName) {
        this.rcName = rcName;
    }

    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public String getShowQuarter() {
        return showQuarter;
    }

    public void setShowQuarter(String showQuarter) {
        this.showQuarter = showQuarter;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public String getShowFromdate() {
        return showFromdate;
    }

    public void setShowFromdate(String showFromdate) {
        this.showFromdate = showFromdate;
    }

    public String getShowTodate() {
        return showTodate;
    }

    public void setShowTodate(String showTodate) {
        this.showTodate = showTodate;
    }
}
