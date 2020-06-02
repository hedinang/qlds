package vn.byt.qlds.model.report;

import java.io.Serializable;
import java.sql.Timestamp;

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

    public String getRcId() {
        return rcId;
    }

    public void setRcId(String rcId) {
        this.rcId = rcId;
    }

    public String getHeaderL() {
        return headerL;
    }

    public void setHeaderL(String headerL) {
        this.headerL = headerL;
    }

    public String getHeaderR() {
        return headerR;
    }

    public void setHeaderR(String headerR) {
        this.headerR = headerR;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getCaption1() {
        return caption1;
    }

    public void setCaption1(String caption1) {
        this.caption1 = caption1;
    }

    public String getCaption2() {
        return caption2;
    }

    public void setCaption2(String caption2) {
        this.caption2 = caption2;
    }

    public String getCaption3() {
        return caption3;
    }

    public void setCaption3(String caption3) {
        this.caption3 = caption3;
    }

    public String getFooterL() {
        return footerL;
    }

    public void setFooterL(String footerL) {
        this.footerL = footerL;
    }

    public String getFooterR() {
        return footerR;
    }

    public void setFooterR(String footerR) {
        this.footerR = footerR;
    }

    public String getShowFooter() {
        return showFooter;
    }

    public void setShowFooter(String showFooter) {
        this.showFooter = showFooter;
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
