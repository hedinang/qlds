package vn.byt.qlds.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "units_sys_params")
public class UnitParam implements Serializable {
    private Integer id;
    private String regionId;
    private Integer sysParamId;
    private String value;
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
    @Column(name = "region_id" , nullable = false, length = 15)
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "sys_param_id" , nullable = false)
    public Integer getSysParamId() {
        return sysParamId;
    }

    public void setSysParamId(Integer sysParamId) {
        this.sysParamId = sysParamId;
    }

    @Basic
    @Column(name = "value", nullable = false, length = 150)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        UnitParam that = (UnitParam) o;
        return id.equals(that.id) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(sysParamId, that.sysParamId) &&
                Objects.equals(value, that.value) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regionId, value, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}
