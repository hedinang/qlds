package vn.byt.qlds.ministry.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "report_design")
public class ReportDesign implements Serializable {
    private String rcId;
    private String headerL;
    private String headerR;
    private String subtitle;
    private String caption1;
    private String caption2;
    private String caption3;
    private String footerL;
    private String footerR;
    private String showFooter;
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
    @Column(name = "header_left", nullable = true, length = 500)
    public String getHeaderL() {
        return headerL;
    }

    public void setHeaderL(String headerL) {
        this.headerL = headerL;
    }

    @Basic
    @Column(name = "header_right", nullable = true, length = 500)
    public String getHeaderR() {
        return headerR;
    }

    public void setHeaderR(String headerR) {
        this.headerR = headerR;
    }

    @Basic
    @Column(name = "subtitle", nullable = true, length = 500)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Basic
    @Column(name = "caption1", nullable = true, length = 500)
    public String getCaption1() {
        return caption1;
    }

    public void setCaption1(String caption1) {
        this.caption1 = caption1;
    }

    @Basic
    @Column(name = "caption2", nullable = true, length = 500)
    public String getCaption2() {
        return caption2;
    }

    public void setCaption2(String caption2) {
        this.caption2 = caption2;
    }

    @Basic
    @Column(name = "caption3", nullable = true, length = 500)
    public String getCaption3() {
        return caption3;
    }

    public void setCaption3(String caption3) {
        this.caption3 = caption3;
    }

    @Basic
    @Column(name = "footer_left", nullable = true, length = 500)
    public String getFooterL() {
        return footerL;
    }

    public void setFooterL(String footerL) {
        this.footerL = footerL;
    }

    @Basic
    @Column(name = "footer_right", nullable = true, length = 500)
    public String getFooterR() {
        return footerR;
    }

    public void setFooterR(String footerR) {
        this.footerR = footerR;
    }

    @Basic
    @Column(name = "show_footer", nullable = true, length = 1)
    public String getShowFooter() {
        return showFooter;
    }

    public void setShowFooter(String showFooter) {
        this.showFooter = showFooter;
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
        ReportDesign that = (ReportDesign) o;
        return Objects.equals(rcId, that.rcId) &&
                Objects.equals(headerL, that.headerL) &&
                Objects.equals(headerR, that.headerR) &&
                Objects.equals(subtitle, that.subtitle) &&
                Objects.equals(caption1, that.caption1) &&
                Objects.equals(caption2, that.caption2) &&
                Objects.equals(caption3, that.caption3) &&
                Objects.equals(footerL, that.footerL) &&
                Objects.equals(footerR, that.footerR) &&
                Objects.equals(showFooter, that.showFooter) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rcId, headerL, headerR, subtitle, caption1, caption2, caption3, footerL, footerR, showFooter, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}
