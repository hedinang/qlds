package vn.byt.qlds.sync.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "supplementinfor")
public class Supplementinfor {
    private Integer reportId;
    private String regionId;
    private Integer khhgdServiceBaseNumber;
    private Integer communeMedicalStation;
    private Integer privateMedicalBase;
    private Integer provinceHospital;
    private Integer districtHospital;
    private Integer areaHospital;
    private Integer socialBranchBase;
    private Integer branchHospital;
    private Integer centreHospital;
    private Integer provinceCadre;
    private Integer femaleProvinceCadre;
    private Integer contractProvinceCadre;
    private Integer districtCadre;
    private Integer femaleDistrictCadre;
    private Integer contractDistrictCadre;
    private Integer communeCadre;
    private Double contractCommuneCadre;
    private Integer femaleCommuneCadre;
    private Double implementInvestment;
    private Double investmentDistrict;
    private Double investmentProvince;
    private Double investmentCentre;
    private Double investmentUpper;
    private Double expensesAllocation;
    private Double expensesCommune;
    private Double expensesDistrict;
    private Double expensesProvince;
    private Double expensesCentre;
    private Double expensesUpper;
    private Integer rptYear;
    private Double investmentCommune;
    private Boolean exportStatus;
    private Integer housePoor;
    private Integer houseCulture;
    private Integer houseLaw;
    private Integer totalAbuse;
    private Integer totalAbuseSettle;
    private Integer totalAbuseChild;
    private Integer babySale;
    private Integer babySaleFemale;
    private Integer babyInject;
    private Integer babyUnderfed;
    private Integer babyHiv;
    private Integer babyFarHome;
    private Integer babyInjureSex;
    private Integer communeChildProtection;
    private Integer communeChildStand;
    private Double moneyChildProtection;
    private Double sourceOfCapitalOda;

    @Id
    @Column(name = "Report_ID")
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
    @Column(name = "KHHGD_Service_Base_Number")
    public Integer getKhhgdServiceBaseNumber() {
        return khhgdServiceBaseNumber;
    }

    public void setKhhgdServiceBaseNumber(Integer khhgdServiceBaseNumber) {
        this.khhgdServiceBaseNumber = khhgdServiceBaseNumber;
    }

    @Basic
    @Column(name = "Commune_Medical_Station")
    public Integer getCommuneMedicalStation() {
        return communeMedicalStation;
    }

    public void setCommuneMedicalStation(Integer communeMedicalStation) {
        this.communeMedicalStation = communeMedicalStation;
    }

    @Basic
    @Column(name = "Private_Medical_Base")
    public Integer getPrivateMedicalBase() {
        return privateMedicalBase;
    }

    public void setPrivateMedicalBase(Integer privateMedicalBase) {
        this.privateMedicalBase = privateMedicalBase;
    }

    @Basic
    @Column(name = "Province_Hospital")
    public Integer getProvinceHospital() {
        return provinceHospital;
    }

    public void setProvinceHospital(Integer provinceHospital) {
        this.provinceHospital = provinceHospital;
    }

    @Basic
    @Column(name = "District_Hospital")
    public Integer getDistrictHospital() {
        return districtHospital;
    }

    public void setDistrictHospital(Integer districtHospital) {
        this.districtHospital = districtHospital;
    }

    @Basic
    @Column(name = "Area_Hospital")
    public Integer getAreaHospital() {
        return areaHospital;
    }

    public void setAreaHospital(Integer areaHospital) {
        this.areaHospital = areaHospital;
    }

    @Basic
    @Column(name = "Social_Branch_Base")
    public Integer getSocialBranchBase() {
        return socialBranchBase;
    }

    public void setSocialBranchBase(Integer socialBranchBase) {
        this.socialBranchBase = socialBranchBase;
    }

    @Basic
    @Column(name = "Branch_Hospital")
    public Integer getBranchHospital() {
        return branchHospital;
    }

    public void setBranchHospital(Integer branchHospital) {
        this.branchHospital = branchHospital;
    }

    @Basic
    @Column(name = "Centre_Hospital")
    public Integer getCentreHospital() {
        return centreHospital;
    }

    public void setCentreHospital(Integer centreHospital) {
        this.centreHospital = centreHospital;
    }

    @Basic
    @Column(name = "Province_Cadre")
    public Integer getProvinceCadre() {
        return provinceCadre;
    }

    public void setProvinceCadre(Integer provinceCadre) {
        this.provinceCadre = provinceCadre;
    }

    @Basic
    @Column(name = "Female_Province_Cadre")
    public Integer getFemaleProvinceCadre() {
        return femaleProvinceCadre;
    }

    public void setFemaleProvinceCadre(Integer femaleProvinceCadre) {
        this.femaleProvinceCadre = femaleProvinceCadre;
    }

    @Basic
    @Column(name = "Contract_Province_Cadre")
    public Integer getContractProvinceCadre() {
        return contractProvinceCadre;
    }

    public void setContractProvinceCadre(Integer contractProvinceCadre) {
        this.contractProvinceCadre = contractProvinceCadre;
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
    @Column(name = "Contract_District_Cadre")
    public Integer getContractDistrictCadre() {
        return contractDistrictCadre;
    }

    public void setContractDistrictCadre(Integer contractDistrictCadre) {
        this.contractDistrictCadre = contractDistrictCadre;
    }

    @Basic
    @Column(name = "Commune_Cadre")
    public Integer getCommuneCadre() {
        return communeCadre;
    }

    public void setCommuneCadre(Integer communeCadre) {
        this.communeCadre = communeCadre;
    }

    @Basic
    @Column(name = "Contract_Commune_Cadre")
    public Double getContractCommuneCadre() {
        return contractCommuneCadre;
    }

    public void setContractCommuneCadre(Double contractCommuneCadre) {
        this.contractCommuneCadre = contractCommuneCadre;
    }

    @Basic
    @Column(name = "Female_Commune_Cadre")
    public Integer getFemaleCommuneCadre() {
        return femaleCommuneCadre;
    }

    public void setFemaleCommuneCadre(Integer femaleCommuneCadre) {
        this.femaleCommuneCadre = femaleCommuneCadre;
    }

    @Basic
    @Column(name = "Implement_Investment")
    public Double getImplementInvestment() {
        return implementInvestment;
    }

    public void setImplementInvestment(Double implementInvestment) {
        this.implementInvestment = implementInvestment;
    }

    @Basic
    @Column(name = "Investment_District")
    public Double getInvestmentDistrict() {
        return investmentDistrict;
    }

    public void setInvestmentDistrict(Double investmentDistrict) {
        this.investmentDistrict = investmentDistrict;
    }

    @Basic
    @Column(name = "Investment_Province")
    public Double getInvestmentProvince() {
        return investmentProvince;
    }

    public void setInvestmentProvince(Double investmentProvince) {
        this.investmentProvince = investmentProvince;
    }

    @Basic
    @Column(name = "Investment_Centre")
    public Double getInvestmentCentre() {
        return investmentCentre;
    }

    public void setInvestmentCentre(Double investmentCentre) {
        this.investmentCentre = investmentCentre;
    }

    @Basic
    @Column(name = "Investment_Upper")
    public Double getInvestmentUpper() {
        return investmentUpper;
    }

    public void setInvestmentUpper(Double investmentUpper) {
        this.investmentUpper = investmentUpper;
    }

    @Basic
    @Column(name = "Expenses_Allocation")
    public Double getExpensesAllocation() {
        return expensesAllocation;
    }

    public void setExpensesAllocation(Double expensesAllocation) {
        this.expensesAllocation = expensesAllocation;
    }

    @Basic
    @Column(name = "Expenses_Commune")
    public Double getExpensesCommune() {
        return expensesCommune;
    }

    public void setExpensesCommune(Double expensesCommune) {
        this.expensesCommune = expensesCommune;
    }

    @Basic
    @Column(name = "Expenses_District")
    public Double getExpensesDistrict() {
        return expensesDistrict;
    }

    public void setExpensesDistrict(Double expensesDistrict) {
        this.expensesDistrict = expensesDistrict;
    }

    @Basic
    @Column(name = "Expenses_Province")
    public Double getExpensesProvince() {
        return expensesProvince;
    }

    public void setExpensesProvince(Double expensesProvince) {
        this.expensesProvince = expensesProvince;
    }

    @Basic
    @Column(name = "Expenses_Centre")
    public Double getExpensesCentre() {
        return expensesCentre;
    }

    public void setExpensesCentre(Double expensesCentre) {
        this.expensesCentre = expensesCentre;
    }

    @Basic
    @Column(name = "Expenses_Upper")
    public Double getExpensesUpper() {
        return expensesUpper;
    }

    public void setExpensesUpper(Double expensesUpper) {
        this.expensesUpper = expensesUpper;
    }

    @Basic
    @Column(name = "RptYear")
    public Integer getRptYear() {
        return rptYear;
    }

    public void setRptYear(Integer rptYear) {
        this.rptYear = rptYear;
    }

    @Basic
    @Column(name = "Investment_Commune")
    public Double getInvestmentCommune() {
        return investmentCommune;
    }

    public void setInvestmentCommune(Double investmentCommune) {
        this.investmentCommune = investmentCommune;
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
    @Column(name = "House_Poor")
    public Integer getHousePoor() {
        return housePoor;
    }

    public void setHousePoor(Integer housePoor) {
        this.housePoor = housePoor;
    }

    @Basic
    @Column(name = "House_Culture")
    public Integer getHouseCulture() {
        return houseCulture;
    }

    public void setHouseCulture(Integer houseCulture) {
        this.houseCulture = houseCulture;
    }

    @Basic
    @Column(name = "House_Law")
    public Integer getHouseLaw() {
        return houseLaw;
    }

    public void setHouseLaw(Integer houseLaw) {
        this.houseLaw = houseLaw;
    }

    @Basic
    @Column(name = "Total_Abuse")
    public Integer getTotalAbuse() {
        return totalAbuse;
    }

    public void setTotalAbuse(Integer totalAbuse) {
        this.totalAbuse = totalAbuse;
    }

    @Basic
    @Column(name = "Total_Abuse_Settle")
    public Integer getTotalAbuseSettle() {
        return totalAbuseSettle;
    }

    public void setTotalAbuseSettle(Integer totalAbuseSettle) {
        this.totalAbuseSettle = totalAbuseSettle;
    }

    @Basic
    @Column(name = "Total_Abuse_Child")
    public Integer getTotalAbuseChild() {
        return totalAbuseChild;
    }

    public void setTotalAbuseChild(Integer totalAbuseChild) {
        this.totalAbuseChild = totalAbuseChild;
    }

    @Basic
    @Column(name = "Baby_Sale")
    public Integer getBabySale() {
        return babySale;
    }

    public void setBabySale(Integer babySale) {
        this.babySale = babySale;
    }

    @Basic
    @Column(name = "Baby_Sale_Female")
    public Integer getBabySaleFemale() {
        return babySaleFemale;
    }

    public void setBabySaleFemale(Integer babySaleFemale) {
        this.babySaleFemale = babySaleFemale;
    }

    @Basic
    @Column(name = "Baby_Inject")
    public Integer getBabyInject() {
        return babyInject;
    }

    public void setBabyInject(Integer babyInject) {
        this.babyInject = babyInject;
    }

    @Basic
    @Column(name = "Baby_Underfed")
    public Integer getBabyUnderfed() {
        return babyUnderfed;
    }

    public void setBabyUnderfed(Integer babyUnderfed) {
        this.babyUnderfed = babyUnderfed;
    }

    @Basic
    @Column(name = "Baby_HIV")
    public Integer getBabyHiv() {
        return babyHiv;
    }

    public void setBabyHiv(Integer babyHiv) {
        this.babyHiv = babyHiv;
    }

    @Basic
    @Column(name = "Baby_FarHome")
    public Integer getBabyFarHome() {
        return babyFarHome;
    }

    public void setBabyFarHome(Integer babyFarHome) {
        this.babyFarHome = babyFarHome;
    }

    @Basic
    @Column(name = "Baby_InjureSex")
    public Integer getBabyInjureSex() {
        return babyInjureSex;
    }

    public void setBabyInjureSex(Integer babyInjureSex) {
        this.babyInjureSex = babyInjureSex;
    }

    @Basic
    @Column(name = "Commune_ChildProtection")
    public Integer getCommuneChildProtection() {
        return communeChildProtection;
    }

    public void setCommuneChildProtection(Integer communeChildProtection) {
        this.communeChildProtection = communeChildProtection;
    }

    @Basic
    @Column(name = "Commune_ChildStand")
    public Integer getCommuneChildStand() {
        return communeChildStand;
    }

    public void setCommuneChildStand(Integer communeChildStand) {
        this.communeChildStand = communeChildStand;
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
    @Column(name = "SourceOfCapital_ODA")
    public Double getSourceOfCapitalOda() {
        return sourceOfCapitalOda;
    }

    public void setSourceOfCapitalOda(Double sourceOfCapitalOda) {
        this.sourceOfCapitalOda = sourceOfCapitalOda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplementinfor that = (Supplementinfor) o;
        return Objects.equals(reportId, that.reportId) &&
                Objects.equals(regionId, that.regionId) &&
                Objects.equals(khhgdServiceBaseNumber, that.khhgdServiceBaseNumber) &&
                Objects.equals(communeMedicalStation, that.communeMedicalStation) &&
                Objects.equals(privateMedicalBase, that.privateMedicalBase) &&
                Objects.equals(provinceHospital, that.provinceHospital) &&
                Objects.equals(districtHospital, that.districtHospital) &&
                Objects.equals(areaHospital, that.areaHospital) &&
                Objects.equals(socialBranchBase, that.socialBranchBase) &&
                Objects.equals(branchHospital, that.branchHospital) &&
                Objects.equals(centreHospital, that.centreHospital) &&
                Objects.equals(provinceCadre, that.provinceCadre) &&
                Objects.equals(femaleProvinceCadre, that.femaleProvinceCadre) &&
                Objects.equals(contractProvinceCadre, that.contractProvinceCadre) &&
                Objects.equals(districtCadre, that.districtCadre) &&
                Objects.equals(femaleDistrictCadre, that.femaleDistrictCadre) &&
                Objects.equals(contractDistrictCadre, that.contractDistrictCadre) &&
                Objects.equals(communeCadre, that.communeCadre) &&
                Objects.equals(contractCommuneCadre, that.contractCommuneCadre) &&
                Objects.equals(femaleCommuneCadre, that.femaleCommuneCadre) &&
                Objects.equals(implementInvestment, that.implementInvestment) &&
                Objects.equals(investmentDistrict, that.investmentDistrict) &&
                Objects.equals(investmentProvince, that.investmentProvince) &&
                Objects.equals(investmentCentre, that.investmentCentre) &&
                Objects.equals(investmentUpper, that.investmentUpper) &&
                Objects.equals(expensesAllocation, that.expensesAllocation) &&
                Objects.equals(expensesCommune, that.expensesCommune) &&
                Objects.equals(expensesDistrict, that.expensesDistrict) &&
                Objects.equals(expensesProvince, that.expensesProvince) &&
                Objects.equals(expensesCentre, that.expensesCentre) &&
                Objects.equals(expensesUpper, that.expensesUpper) &&
                Objects.equals(rptYear, that.rptYear) &&
                Objects.equals(investmentCommune, that.investmentCommune) &&
                Objects.equals(exportStatus, that.exportStatus) &&
                Objects.equals(housePoor, that.housePoor) &&
                Objects.equals(houseCulture, that.houseCulture) &&
                Objects.equals(houseLaw, that.houseLaw) &&
                Objects.equals(totalAbuse, that.totalAbuse) &&
                Objects.equals(totalAbuseSettle, that.totalAbuseSettle) &&
                Objects.equals(totalAbuseChild, that.totalAbuseChild) &&
                Objects.equals(babySale, that.babySale) &&
                Objects.equals(babySaleFemale, that.babySaleFemale) &&
                Objects.equals(babyInject, that.babyInject) &&
                Objects.equals(babyUnderfed, that.babyUnderfed) &&
                Objects.equals(babyHiv, that.babyHiv) &&
                Objects.equals(babyFarHome, that.babyFarHome) &&
                Objects.equals(babyInjureSex, that.babyInjureSex) &&
                Objects.equals(communeChildProtection, that.communeChildProtection) &&
                Objects.equals(communeChildStand, that.communeChildStand) &&
                Objects.equals(moneyChildProtection, that.moneyChildProtection) &&
                Objects.equals(sourceOfCapitalOda, that.sourceOfCapitalOda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, regionId, khhgdServiceBaseNumber, communeMedicalStation, privateMedicalBase, provinceHospital, districtHospital, areaHospital, socialBranchBase, branchHospital, centreHospital, provinceCadre, femaleProvinceCadre, contractProvinceCadre, districtCadre, femaleDistrictCadre, contractDistrictCadre, communeCadre, contractCommuneCadre, femaleCommuneCadre, implementInvestment, investmentDistrict, investmentProvince, investmentCentre, investmentUpper, expensesAllocation, expensesCommune, expensesDistrict, expensesProvince, expensesCentre, expensesUpper, rptYear, investmentCommune, exportStatus, housePoor, houseCulture, houseLaw, totalAbuse, totalAbuseSettle, totalAbuseChild, babySale, babySaleFemale, babyInject, babyUnderfed, babyHiv, babyFarHome, babyInjureSex, communeChildProtection, communeChildStand, moneyChildProtection, sourceOfCapitalOda);
    }
}
