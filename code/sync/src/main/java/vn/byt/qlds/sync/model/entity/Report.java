package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "report")
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

    @Id
    @Column(name = "rc_id", nullable = false, length = 30)
    public String getRcId() {
        return rcId;
    }

    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    @Basic
    @Column(name = "rc_name", nullable = true, length = 1000)
    public String getRcName() {
        return rcName;
    }

    public void setRcName(String rcName) {
        this.rcName = rcName;
    }

    @Basic
    @Column(name = "levels", nullable = true, length = 10)
    public String getLevels() {
        return levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    @Basic
    @Column(name = "rpt_name", nullable = true, length = 100)
    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    @Basic
    @Column(name = "rc_type", nullable = true, length = 2)
    public String getRcType() {
        return rcType;
    }

    public void setRcType(String rcType) {
        this.rcType = rcType;
    }

    @Basic
    @Column(name = "proc_name", nullable = true, length = 100)
    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    @Basic
    @Column(name = "paras", nullable = true, length = 500)
    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    @Basic
    @Column(name = "show_month", nullable = true, length = 1)
    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    @Basic
    @Column(name = "show_quarter", nullable = true, length = 1)
    public String getShowQuarter() {
        return showQuarter;
    }

    public void setShowQuarter(String showQuarter) {
        this.showQuarter = showQuarter;
    }

    @Basic
    @Column(name = "show_year", nullable = true, length = 1)
    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    @Basic
    @Column(name = "show_from_date", nullable = true, length = 1)
    public String getShowFromDate() {
        return showFromDate;
    }

    public void setShowFromDate(String showFromDate) {
        this.showFromDate = showFromDate;
    }

    @Basic
    @Column(name = "show_to_date", nullable = true, length = 1)
    public String getShowToDate() {
        return showToDate;
    }

    public void setShowToDate(String showToDate) {
        this.showToDate = showToDate;
    }

    @Basic
    @Column(name = "is_deleted", nullable = true)
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Basic
    @Column(name = "user_created", nullable = true)
    public Long getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    @Basic
    @Column(name = "user_last_updated", nullable = true)
    public Long getUserLastUpdated() {
        return userLastUpdated;
    }

    public void setUserLastUpdated(Long userLastUpdated) {
        this.userLastUpdated = userLastUpdated;
    }

    @Basic
    @CreationTimestamp
    @Column(name = "time_created", nullable = true)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @UpdateTimestamp
    @Column(name = "time_last_updated", nullable = true)
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
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
                Objects.equals(showFromDate, report.showFromDate) &&
                Objects.equals(showToDate, report.showToDate) &&
                Objects.equals(isDeleted, report.isDeleted) &&
                Objects.equals(userCreated, report.userCreated) &&
                Objects.equals(userLastUpdated, report.userLastUpdated) &&
                Objects.equals(timeCreated, report.timeCreated) &&
                Objects.equals(timeLastUpdated, report.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcId, rcName, levels, rptName, rcType, procName, paras, showMonth, showQuarter, showYear, showFromDate, showToDate, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}

