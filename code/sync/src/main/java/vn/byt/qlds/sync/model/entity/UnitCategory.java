package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "units")
public class UnitCategory implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private String parent;
    private String zone;
    private Integer levels;
    private String note;
    private String area;
    private String briefName;
    private Integer isActive;
    private Integer selected;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 15)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 500)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "parent", nullable = true, length = 15)
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Basic
    @Column(name = "zone", nullable = true, length = 10)
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Basic
    @Column(name = "levels", nullable = true)
    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    @Basic
    @Column(name = "is_active", nullable = true)
    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "selected", nullable = true)
    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
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

    @Basic
    @Column(name = "note", nullable = true, length = 255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "area", nullable = true, length = 255)
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "brief_name", nullable = true, length = 255)
    public String getBriefName() {
        return briefName;
    }

    public void setBriefName(String briefName) {
        this.briefName = briefName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitCategory dmDonVi = (UnitCategory) o;
        return Objects.equals(id, dmDonVi.id) &&
                Objects.equals(code, dmDonVi.code) &&
                Objects.equals(name, dmDonVi.name) &&
                Objects.equals(parent, dmDonVi.parent) &&
                Objects.equals(zone, dmDonVi.zone) &&
                Objects.equals(levels, dmDonVi.levels) &&
                Objects.equals(isActive, dmDonVi.isActive) &&
                Objects.equals(selected, dmDonVi.selected) &&
                Objects.equals(isDeleted, dmDonVi.isDeleted) &&
                Objects.equals(userCreated, dmDonVi.userCreated) &&
                Objects.equals(userLastUpdated, dmDonVi.userLastUpdated) &&
                Objects.equals(timeCreated, dmDonVi.timeCreated) &&
                Objects.equals(timeLastUpdated, dmDonVi.timeLastUpdated) &&
                Objects.equals(note, dmDonVi.note) &&
                Objects.equals(area, dmDonVi.area) &&
                Objects.equals(briefName, dmDonVi.briefName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, parent, zone, levels, isActive, selected, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated, note, area, briefName);
    }
}
