package vn.byt.qlds.ministry.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "change_type")
public class ReasonChange {
    private int id;
    private String changeTypeCode;
    private String changeTypeName;
    private String changeCodeOld;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Boolean isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "change_type_code", nullable = true, length = 10)
    public String getChangeTypeCode() {
        return changeTypeCode;
    }

    public void setChangeTypeCode(String changeTypeCode) {
        this.changeTypeCode = changeTypeCode;
    }

    @Basic
    @Column(name = "change_type_name", nullable = true, length = 255)
    public String getChangeTypeName() {
        return changeTypeName;
    }

    public void setChangeTypeName(String changeTypeName) {
        this.changeTypeName = changeTypeName;
    }

    @Basic
    @Column(name = "change_code_old", nullable = true, length = 10)
    public String getChangeCodeOld() {
        return changeCodeOld;
    }

    public void setChangeCodeOld(String changeCodeOld) {
        this.changeCodeOld = changeCodeOld;
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
    @Column(name = "is_active", nullable = true)
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
    @Column(name = "time_created", nullable = true)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated", nullable = true, length = 255)
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
        ReasonChange that = (ReasonChange) o;
        return id == that.id &&
                Objects.equals(changeTypeCode, that.changeTypeCode) &&
                Objects.equals(changeTypeName, that.changeTypeName) &&
                Objects.equals(changeCodeOld, that.changeCodeOld) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, changeTypeCode, changeTypeName, changeCodeOld, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}
