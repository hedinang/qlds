package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "nhat_ki_hoat_dong")
public class LogAction {
    private long id;
    private Timestamp timeCreated;
    private Long userCreated;
    private String userName;
    private String description;
    private Boolean isDeleted;
    private String action;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time_created", nullable = true)
    @CreationTimestamp
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
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
    @Column(name = "user_name", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "action", nullable = true, length = 255)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogAction logAction = (LogAction) o;
        return id == logAction.id &&
                Objects.equals(timeCreated, logAction.timeCreated) &&
                Objects.equals(userCreated, logAction.userCreated) &&
                Objects.equals(userName, logAction.userName) &&
                Objects.equals(description, logAction.description) &&
                Objects.equals(isDeleted, logAction.isDeleted) &&
                Objects.equals(action, logAction.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeCreated, userCreated, userName, description, isDeleted, action);
    }
}
