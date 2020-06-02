package vn.byt.qlds.sync.model.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "nationality")
public class Nationality {
    private int nationalityCode;
    private String nationalityName;
    private String countryName;
    private String nationalityCodeOld;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Boolean isActive;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nationality_code", nullable = false)
    public int getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(int nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    @Basic
    @Column(name = "nationality_name", nullable = false, length = 50)
    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    @Basic
    @Column(name = "country_name", nullable = true, length = 50)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "nationality_code_old", nullable = true, length = 10)
    public String getNationalityCodeOld() {
        return nationalityCodeOld;
    }

    public void setNationalityCodeOld(String nationalityCodeOld) {
        this.nationalityCodeOld = nationalityCodeOld;
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
        Nationality that = (Nationality) o;
        return nationalityCode == that.nationalityCode &&
                Objects.equals(nationalityName, that.nationalityName) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(nationalityCodeOld, that.nationalityCodeOld) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nationalityCode, nationalityName, countryName, nationalityCodeOld, isDeleted, userCreated, userLastUpdated, timeCreated, timeLastUpdated);
    }
}
