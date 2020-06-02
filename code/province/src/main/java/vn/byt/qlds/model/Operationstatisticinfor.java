package vn.byt.qlds.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "operationstatisticinfor")
public class Operationstatisticinfor {
    private Integer reportId;
    private String regionId;
    private Short rptQuarter;
    private Short rptYear;
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

    @Id
    @Column(name = "Report_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    @Basic
    @Column(name = "Region_ID")
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Basic
    @Column(name = "RptQuarter")
    public Short getRptQuarter() {
        return rptQuarter;
    }

    public void setRptQuarter(Short rptQuarter) {
        this.rptQuarter = rptQuarter;
    }

    @Basic
    @Column(name = "RptYear")
    public Short getRptYear() {
        return rptYear;
    }

    public void setRptYear(Short rptYear) {
        this.rptYear = rptYear;
    }

    @Basic
    @Column(name = "RptMonth")
    public Short getRptMonth() {
        return rptMonth;
    }

    public void setRptMonth(Short rptMonth) {
        this.rptMonth = rptMonth;
    }

    @Basic
    @Column(name = "Total_Propagate_Number")
    public Integer getTotalPropagateNumber() {
        return totalPropagateNumber;
    }

    public void setTotalPropagateNumber(Integer totalPropagateNumber) {
        this.totalPropagateNumber = totalPropagateNumber;
    }

    @Basic
    @Column(name = "Total_Pano_Number")
    public Integer getTotalPanoNumber() {
        return totalPanoNumber;
    }

    public void setTotalPanoNumber(Integer totalPanoNumber) {
        this.totalPanoNumber = totalPanoNumber;
    }

    @Basic
    @Column(name = "Total_Brochure_Number")
    public Integer getTotalBrochureNumber() {
        return totalBrochureNumber;
    }

    public void setTotalBrochureNumber(Integer totalBrochureNumber) {
        this.totalBrochureNumber = totalBrochureNumber;
    }

    @Basic
    @Column(name = "Total_Book_Number")
    public Integer getTotalBookNumber() {
        return totalBookNumber;
    }

    public void setTotalBookNumber(Integer totalBookNumber) {
        this.totalBookNumber = totalBookNumber;
    }

    @Basic
    @Column(name = "Total_Cattsette_Number")
    public Integer getTotalCattsetteNumber() {
        return totalCattsetteNumber;
    }

    public void setTotalCattsetteNumber(Integer totalCattsetteNumber) {
        this.totalCattsetteNumber = totalCattsetteNumber;
    }

    @Basic
    @Column(name = "Two_Children_Pair")
    public Integer getTwoChildrenPair() {
        return twoChildrenPair;
    }

    public void setTwoChildrenPair(Integer twoChildrenPair) {
        this.twoChildrenPair = twoChildrenPair;
    }

    @Basic
    @Column(name = "MoreThanThreeChildren_Pair")
    public Integer getMoreThanThreeChildrenPair() {
        return moreThanThreeChildrenPair;
    }

    public void setMoreThanThreeChildrenPair(Integer moreThanThreeChildrenPair) {
        this.moreThanThreeChildrenPair = moreThanThreeChildrenPair;
    }

    @Basic
    @Column(name = "District_Cadre")
    public Integer getDistrictCadre() {
        return districtCadre;
    }

    public void setDistrictCadre(Integer districtCadre) {
        this.districtCadre = districtCadre;
    }

    @Basic
    @Column(name = "Female_District_Cadre")
    public Integer getFemaleDistrictCadre() {
        return femaleDistrictCadre;
    }

    public void setFemaleDistrictCadre(Integer femaleDistrictCadre) {
        this.femaleDistrictCadre = femaleDistrictCadre;
    }

    @Basic
    @Column(name = "Export_Status")
    public Boolean getExportStatus() {
        return exportStatus;
    }

    public void setExportStatus(Boolean exportStatus) {
        this.exportStatus = exportStatus;
    }

    @Basic
    @Column(name = "Contract_District_Cadre")
    public Integer getContractDistrictCadre() {
        return contractDistrictCadre;
    }

    public void setContractDistrictCadre(Integer contractDistrictCadre) {
        this.contractDistrictCadre = contractDistrictCadre;
    }

    @Basic
    @Column(name = "Fieldworker_Cadre")
    public Integer getFieldworkerCadre() {
        return fieldworkerCadre;
    }

    public void setFieldworkerCadre(Integer fieldworkerCadre) {
        this.fieldworkerCadre = fieldworkerCadre;
    }

    @Basic
    @Column(name = "Female_Fieldworker1")
    public Integer getFemaleFieldworker1() {
        return femaleFieldworker1;
    }

    public void setFemaleFieldworker1(Integer femaleFieldworker1) {
        this.femaleFieldworker1 = femaleFieldworker1;
    }

    @Basic
    @Column(name = "Money_ChildProtection")
    public Double getMoneyChildProtection() {
        return moneyChildProtection;
    }

    public void setMoneyChildProtection(Double moneyChildProtection) {
        this.moneyChildProtection = moneyChildProtection;
    }

    @Basic
    @Column(name = "Expense_Carrer_Commnue")
    public Double getExpenseCarrerCommnue() {
        return expenseCarrerCommnue;
    }

    public void setExpenseCarrerCommnue(Double expenseCarrerCommnue) {
        this.expenseCarrerCommnue = expenseCarrerCommnue;
    }

    @Basic
    @Column(name = "Expense_Carrer_District")
    public Double getExpenseCarrerDistrict() {
        return expenseCarrerDistrict;
    }

    public void setExpenseCarrerDistrict(Double expenseCarrerDistrict) {
        this.expenseCarrerDistrict = expenseCarrerDistrict;
    }

    @Basic
    @Column(name = "Cadre_Province")
    public Double getCadreProvince() {
        return cadreProvince;
    }

    public void setCadreProvince(Double cadreProvince) {
        this.cadreProvince = cadreProvince;
    }

    @Basic
    @Column(name = "Expense_Carrer_Superior")
    public Double getExpenseCarrerSuperior() {
        return expenseCarrerSuperior;
    }

    public void setExpenseCarrerSuperior(Double expenseCarrerSuperior) {
        this.expenseCarrerSuperior = expenseCarrerSuperior;
    }

    @Basic
    @Column(name = "Spend_Population")
    public Double getSpendPopulation() {
        return spendPopulation;
    }

    public void setSpendPopulation(Double spendPopulation) {
        this.spendPopulation = spendPopulation;
    }

    @Basic
    @Column(name = "Spend_Child")
    public Double getSpendChild() {
        return spendChild;
    }

    public void setSpendChild(Double spendChild) {
        this.spendChild = spendChild;
    }

    @Basic
    @Column(name = "Spend_Family")
    public Double getSpendFamily() {
        return spendFamily;
    }

    public void setSpendFamily(Double spendFamily) {
        this.spendFamily = spendFamily;
    }

    @Basic
    @Column(name = "Spend_Another")
    public Double getSpendAnother() {
        return spendAnother;
    }

    public void setSpendAnother(Double spendAnother) {
        this.spendAnother = spendAnother;
    }

    @Basic
    @Column(name = "Expense_SN")
    public Double getExpenseSn() {
        return expenseSn;
    }

    public void setExpenseSn(Double expenseSn) {
        this.expenseSn = expenseSn;
    }

    @Basic
    @Column(name = "Expense_SN_Commune")
    public Double getExpenseSnCommune() {
        return expenseSnCommune;
    }

    public void setExpenseSnCommune(Double expenseSnCommune) {
        this.expenseSnCommune = expenseSnCommune;
    }

    @Basic
    @Column(name = "Expense_Contruct")
    public Double getExpenseContruct() {
        return expenseContruct;
    }

    public void setExpenseContruct(Double expenseContruct) {
        this.expenseContruct = expenseContruct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operationstatisticinfor that = (Operationstatisticinfor) o;
        return Objects.equals(reportId, that.reportId) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(rptQuarter, that.rptQuarter) &&
                Objects.equals(rptYear, that.rptYear) &&
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
                Objects.equals(expenseContruct, that.expenseContruct);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, regionId, rptQuarter, rptYear, rptMonth, totalPropagateNumber, totalPanoNumber, totalBrochureNumber, totalBookNumber, totalCattsetteNumber, twoChildrenPair, moreThanThreeChildrenPair, districtCadre, femaleDistrictCadre, exportStatus, contractDistrictCadre, fieldworkerCadre, femaleFieldworker1, moneyChildProtection, expenseCarrerCommnue, expenseCarrerDistrict, cadreProvince, expenseCarrerSuperior, spendPopulation, spendChild, spendFamily, spendAnother, expenseSn, expenseSnCommune, expenseContruct);
    }
}
