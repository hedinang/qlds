package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "operation_statistic_infor")
public class Operationstatisticinfor {
    private int reportId;
    private String regionId;
    private Short rptQuarter;
    private short rptYear;
    private Short rptMonth;
    private Integer totalPropagateNumber;
    private Integer totalPanoNumber;
    private Integer totalBrochureNumber;
    private Integer totalBookNumber;
    private Integer totalCattsetteNumber;
    private Integer twoChildrenPair;
    private Integer moreThanThreeChildrenPair;
    private Integer districtCadre;
    private Integer femaleDistrictCadre;
    private Boolean exportStatus;
    private Integer contractDistrictCadre;
    private Integer fieldworkerCadre;
    private Integer femaleFieldworker1;
    private Double moneyChildProtection;
    private Double expenseCarrerCommnue;
    private Double expenseCarrerDistrict;
    private Double cadreProvince;
    private Double expenseCarrerSuperior;
    private Double spendPopulation;
    private Double spendChild;
    private Double spendFamily;
    private Double spendAnother;
    private Double expenseSn;
    private Double expenseSnCommune;
    private Double expenseContruct;
    private Boolean isDeleted;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;

    @Id
    @Column(name = "Report_ID", nullable = false)
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "Region_ID", nullable = false, length = 15)
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "RptQuarter", nullable = true)
    public Short getRptQuarter() {
        return rptQuarter;
    }

    public void setRptQuarter(Short rptQuarter) {
        this.rptQuarter = rptQuarter;
    }

    @Basic
    @Column(name = "RptYear", nullable = false)
    public short getRptYear() {
        return rptYear;
    }

    public void setRptYear(short rptYear) {
        this.rptYear = rptYear;
    }

    @Basic
    @Column(name = "RptMonth", nullable = true)
    public Short getRptMonth() {
        return rptMonth;
    }

    public void setRptMonth(Short rptMonth) {
        this.rptMonth = rptMonth;
    }

    @Basic
    @Column(name = "Total_Propagate_Number", nullable = true)
    public Integer getTotalPropagateNumber() {
        return totalPropagateNumber;
    }

    public void setTotalPropagateNumber(Integer totalPropagateNumber) {
        this.totalPropagateNumber = totalPropagateNumber;
    }

    @Basic
    @Column(name = "Total_Pano_Number", nullable = true)
    public Integer getTotalPanoNumber() {
        return totalPanoNumber;
    }

    public void setTotalPanoNumber(Integer totalPanoNumber) {
        this.totalPanoNumber = totalPanoNumber;
    }

    @Basic
    @Column(name = "Total_Brochure_Number", nullable = true)
    public Integer getTotalBrochureNumber() {
        return totalBrochureNumber;
    }

    public void setTotalBrochureNumber(Integer totalBrochureNumber) {
        this.totalBrochureNumber = totalBrochureNumber;
    }

    @Basic
    @Column(name = "Total_Book_Number", nullable = true)
    public Integer getTotalBookNumber() {
        return totalBookNumber;
    }

    public void setTotalBookNumber(Integer totalBookNumber) {
        this.totalBookNumber = totalBookNumber;
    }

    @Basic
    @Column(name = "Total_Cattsette_Number", nullable = true)
    public Integer getTotalCattsetteNumber() {
        return totalCattsetteNumber;
    }

    public void setTotalCattsetteNumber(Integer totalCattsetteNumber) {
        this.totalCattsetteNumber = totalCattsetteNumber;
    }

    @Basic
    @Column(name = "Two_Children_Pair", nullable = true)
    public Integer getTwoChildrenPair() {
        return twoChildrenPair;
    }

    public void setTwoChildrenPair(Integer twoChildrenPair) {
        this.twoChildrenPair = twoChildrenPair;
    }

    @Basic
    @Column(name = "MoreThanThreeChildren_Pair", nullable = true)
    public Integer getMoreThanThreeChildrenPair() {
        return moreThanThreeChildrenPair;
    }

    public void setMoreThanThreeChildrenPair(Integer moreThanThreeChildrenPair) {
        this.moreThanThreeChildrenPair = moreThanThreeChildrenPair;
    }

    @Basic
    @Column(name = "District_Cadre", nullable = true)
    public Integer getDistrictCadre() {
        return districtCadre;
    }

    public void setDistrictCadre(Integer districtCadre) {
        this.districtCadre = districtCadre;
    }

    @Basic
    @Column(name = "Female_District_Cadre", nullable = true)
    public Integer getFemaleDistrictCadre() {
        return femaleDistrictCadre;
    }

    public void setFemaleDistrictCadre(Integer femaleDistrictCadre) {
        this.femaleDistrictCadre = femaleDistrictCadre;
    }

    @Basic
    @Column(name = "Export_Status", nullable = true)
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "Contract_District_Cadre", nullable = true)
    public Integer getContractDistrictCadre() {
        return contractDistrictCadre;
    }

    public void setContractDistrictCadre(Integer contractDistrictCadre) {
        this.contractDistrictCadre = contractDistrictCadre;
    }

    @Basic
    @Column(name = "Fieldworker_Cadre", nullable = true)
    public Integer getFieldworkerCadre() {
        return fieldworkerCadre;
    }

    public void setFieldworkerCadre(Integer fieldworkerCadre) {
        this.fieldworkerCadre = fieldworkerCadre;
    }

    @Basic
    @Column(name = "Female_Fieldworker1", nullable = true)
    public Integer getFemaleFieldworker1() {
        return femaleFieldworker1;
    }

    public void setFemaleFieldworker1(Integer femaleFieldworker1) {
        this.femaleFieldworker1 = femaleFieldworker1;
    }

    @Basic
    @Column(name = "Money_ChildProtection", nullable = true, precision = 0)
    public Double getMoneyChildProtection() {
        return moneyChildProtection;
    }

    public void setMoneyChildProtection(Double moneyChildProtection) {
        this.moneyChildProtection = moneyChildProtection;
    }

    @Basic
    @Column(name = "Expense_Carrer_Commnue", nullable = true, precision = 0)
    public Double getExpenseCarrerCommnue() {
        return expenseCarrerCommnue;
    }

    public void setExpenseCarrerCommnue(Double expenseCarrerCommnue) {
        this.expenseCarrerCommnue = expenseCarrerCommnue;
    }

    @Basic
    @Column(name = "Expense_Carrer_District", nullable = true, precision = 0)
    public Double getExpenseCarrerDistrict() {
        return expenseCarrerDistrict;
    }

    public void setExpenseCarrerDistrict(Double expenseCarrerDistrict) {
        this.expenseCarrerDistrict = expenseCarrerDistrict;
    }

    @Basic
    @Column(name = "Cadre_Province", nullable = true, precision = 0)
    public Double getCadreProvince() {
        return cadreProvince;
    }

    public void setCadreProvince(Double cadreProvince) {
        this.cadreProvince = cadreProvince;
    }

    @Basic
    @Column(name = "Expense_Carrer_Superior", nullable = true, precision = 0)
    public Double getExpenseCarrerSuperior() {
        return expenseCarrerSuperior;
    }

    public void setExpenseCarrerSuperior(Double expenseCarrerSuperior) {
        this.expenseCarrerSuperior = expenseCarrerSuperior;
    }

    @Basic
    @Column(name = "Spend_Population", nullable = true, precision = 0)
    public Double getSpendPopulation() {
        return spendPopulation;
    }

    public void setSpendPopulation(Double spendPopulation) {
        this.spendPopulation = spendPopulation;
    }

    @Basic
    @Column(name = "Spend_Child", nullable = true, precision = 0)
    public Double getSpendChild() {
        return spendChild;
    }

    public void setSpendChild(Double spendChild) {
        this.spendChild = spendChild;
    }

    @Basic
    @Column(name = "Spend_Family", nullable = true, precision = 0)
    public Double getSpendFamily() {
        return spendFamily;
    }

    public void setSpendFamily(Double spendFamily) {
        this.spendFamily = spendFamily;
    }

    @Basic
    @Column(name = "Spend_Another", nullable = true, precision = 0)
    public Double getSpendAnother() {
        return spendAnother;
    }

    public void setSpendAnother(Double spendAnother) {
        this.spendAnother = spendAnother;
    }

    @Basic
    @Column(name = "Expense_SN", nullable = true, precision = 0)
    public Double getExpenseSn() {
        return expenseSn;
    }

    public void setExpenseSn(Double expenseSn) {
        this.expenseSn = expenseSn;
    }

    @Basic
    @Column(name = "Expense_SN_Commune", nullable = true, precision = 0)
    public Double getExpenseSnCommune() {
        return expenseSnCommune;
    }

    public void setExpenseSnCommune(Double expenseSnCommune) {
        this.expenseSnCommune = expenseSnCommune;
    }

    @Basic
    @Column(name = "Expense_Contruct", nullable = true, precision = 0)
    public Double getExpenseContruct() {
        return expenseContruct;
    }

    public void setExpenseContruct(Double expenseContruct) {
        this.expenseContruct = expenseContruct;
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
    @Column(name = "time_created", nullable = true)
    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Basic
    @Column(name = "time_last_updated", nullable = true)
    public Timestamp getTimeLastUpdated() {
        return timeLastUpdated;
    }

    public void setTimeLastUpdated(Timestamp timeLastUpdated) {
        this.timeLastUpdated = timeLastUpdated;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operationstatisticinfor that = (Operationstatisticinfor) o;
        return reportId == that.reportId &&
                rptYear == that.rptYear &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(rptQuarter, that.rptQuarter) &&
                Objects.equals(rptMonth, that.rptMonth) &&
                Objects.equals(totalPropagateNumber, that.totalPropagateNumber) &&
                Objects.equals(totalPanoNumber, that.totalPanoNumber) &&
                Objects.equals(totalBrochureNumber, that.totalBrochureNumber) &&
                Objects.equals(totalBookNumber, that.totalBookNumber) &&
                Objects.equals(totalCattsetteNumber, that.totalCattsetteNumber) &&
                Objects.equals(twoChildrenPair, that.twoChildrenPair) &&
                Objects.equals(moreThanThreeChildrenPair, that.moreThanThreeChildrenPair) &&
                Objects.equals(districtCadre, that.districtCadre) &&
                Objects.equals(femaleDistrictCadre, that.femaleDistrictCadre) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(contractDistrictCadre, that.contractDistrictCadre) &&
                Objects.equals(fieldworkerCadre, that.fieldworkerCadre) &&
                Objects.equals(femaleFieldworker1, that.femaleFieldworker1) &&
                Objects.equals(moneyChildProtection, that.moneyChildProtection) &&
                Objects.equals(expenseCarrerCommnue, that.expenseCarrerCommnue) &&
                Objects.equals(expenseCarrerDistrict, that.expenseCarrerDistrict) &&
                Objects.equals(cadreProvince, that.cadreProvince) &&
                Objects.equals(expenseCarrerSuperior, that.expenseCarrerSuperior) &&
                Objects.equals(spendPopulation, that.spendPopulation) &&
                Objects.equals(spendChild, that.spendChild) &&
                Objects.equals(spendFamily, that.spendFamily) &&
                Objects.equals(spendAnother, that.spendAnother) &&
                Objects.equals(expenseSn, that.expenseSn) &&
                Objects.equals(expenseSnCommune, that.expenseSnCommune) &&
                Objects.equals(expenseContruct, that.expenseContruct) &&
                Objects.equals(isDeleted, that.isDeleted) &&
                Objects.equals(timeCreated, that.timeCreated) &&
                Objects.equals(timeLastUpdated, that.timeLastUpdated) &&
                Objects.equals(userCreated, that.userCreated) &&
                Objects.equals(userLastUpdated, that.userLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, regionId, rptQuarter, rptYear, rptMonth, totalPropagateNumber, totalPanoNumber, totalBrochureNumber, totalBookNumber, totalCattsetteNumber, twoChildrenPair, moreThanThreeChildrenPair, districtCadre, femaleDistrictCadre, exportStatus, contractDistrictCadre, fieldworkerCadre, femaleFieldworker1, moneyChildProtection, expenseCarrerCommnue, expenseCarrerDistrict, cadreProvince, expenseCarrerSuperior, spendPopulation, spendChild, spendFamily, spendAnother, expenseSn, expenseSnCommune, expenseContruct, isDeleted, timeCreated, timeLastUpdated, userCreated, userLastUpdated);
    }
}
