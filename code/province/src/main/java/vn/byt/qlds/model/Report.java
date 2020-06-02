package vn.byt.qlds.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "bao_cao")
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
    private String showFromdate;
    private String showTodate;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "RC_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getRcId() {
        return rcId;
    }

    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    @Basic
    @Column(name = "RC_NAME")
    public String getRcName() {
        return rcName;
    }

    public void setRcName(String rcName) {
        this.rcName = rcName;
    }

    @Basic
    @Column(name = "LEVELS")
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    @Basic
    @Column(name = "RPT_NAME")
    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    @Basic
    @Column(name = "RC_TYPE")
    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType;
    }

    @Basic
    @Column(name = "PROC_NAME")
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    @Basic
    @Column(name = "PARAS")
    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    @Basic
    @Column(name = "SHOW_MONTH")
    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    @Basic
    @Column(name = "SHOW_QUARTER")
    public String getShowQuarter() {
        return showQuarter;
    }

    public void setShowQuarter(String showQuarter) {
        this.showQuarter = showQuarter;
    }

    @Basic
    @Column(name = "SHOW_YEAR")
    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    @Basic
    @Column(name = "SHOW_FROMDATE")
    public String getShowFromdate() {
        return showFromdate;
    }

    public void setShowFromdate(String showFromdate) {
        this.showFromdate = showFromdate;
    }

    @Basic
    @Column(name = "SHOW_TODATE")
    public String getShowTodate() {
        return showTodate;
    }

    public void setShowTodate(String showTodate) {
        this.showTodate = showTodate;
    }

    @Basic
    @Column(name = "is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "time_created")
    @CreationTimestamp
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated")
    @UpdateTimestamp
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
    }

    @Basic
    @Column(name = "user_created")
    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    @Basic
    @Column(name = "user_last_updated")
    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(rcId, report.rcId) &&
                Objects.equals(rcName, report.rcName) &&
                Objects.equals(levels, report.levels) &&
                Objects.equals(rptName, report.rptName) &&
                Objects.equals(rcType, report.rcType) &&
                Objects.equals(procName, report.procName) &&
                Objects.equals(paras, report.paras) &&
                Objects.equals(showMonth, report.showMonth) &&
                Objects.equals(showQuarter, report.showQuarter) &&
                Objects.equals(showYear, report.showYear) &&
                Objects.equals(showFromdate, report.showFromdate) &&
                Objects.equals(showTodate, report.showTodate) &&
                Objects.equals(isDeleted, report.isDeleted) &&
                Objects.equals(timeCreated, report.timeCreated) &&
                Objects.equals(timeLastUpdated, report.timeLastUpdated) &&
                Objects.equals(userCreated, report.userCreated) &&
                Objects.equals(userLastUpdated, report.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcId, rcName, levels, rptName, rcType, procName, paras, showMonth, showQuarter, showYear, showFromdate, showTodate, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
