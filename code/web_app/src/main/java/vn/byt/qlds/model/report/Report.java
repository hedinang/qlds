package vn.byt.qlds.model.report;

import java.io.Serializable;
import java.sql.Timestamp;

public class Report implements Serializable {
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
    private String showFromDate;
    private String showToDate;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;

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

    public String getShowFromDate() {
        return showFromDate;
    }

    public void setShowFromDate(String showFromDate) {
        this.showFromDate = showFromDate;
    }

    public String getShowTodate() {
        return showToDate;
    }

    public void setShowToDate(String showToDate) {
        this.showToDate = showToDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }
}
