package vn.byt.qlds.model.report.DST.DST01;

import vn.byt.qlds.model.report.ReportSub;

public class DST01Sub extends ReportSub {
    public String address;
    int totalChild;
    int totalBoy;
    int totalGirl;
    int totalChildOfFamilyGt3;
    int totalChildOfMotherAgeLt20;
    int totalWomenUseContraceptiveRing; // dat vong
    int totalWomenInsteadRing;
    int totalWomenStopContraceptiveRing;
    int totalMenSterilization; // triet san
    int totalWomenSterilization;//
    int totalWomenUseImplantedDrug; // su dung thuoc cay tranh thai
    int totalWomenInsteadImplantedDrug;
    int totalWomenStopImplantedDrug;

    public int getTotalChild() {
        return totalChild;
    }

    public void setTotalChild(int totalChild) {
        this.totalChild = totalChild;
    }

    public int getTotalBoy() {
        return totalBoy;
    }

    public void setTotalBoy(int totalBoy) {
        this.totalBoy = totalBoy;
    }

    public int getTotalGirl() {
        return totalGirl;
    }

    public void setTotalGirl(int totalGirl) {
        this.totalGirl = totalGirl;
    }

    public int getTotalChildOfFamilyGt3() {
        return totalChildOfFamilyGt3;
    }

    public void setTotalChildOfFamilyGt3(int totalChildOfFamilyGt3) {
        this.totalChildOfFamilyGt3 = totalChildOfFamilyGt3;
    }

    public int getTotalChildOfMotherAgeLt20() {
        return totalChildOfMotherAgeLt20;
    }

    public void setTotalChildOfMotherAgeLt20(int totalChildOfMotherAgeLt20) {
        this.totalChildOfMotherAgeLt20 = totalChildOfMotherAgeLt20;
    }

    public int getTotalWomenUseContraceptiveRing() {
        return totalWomenUseContraceptiveRing;
    }

    public void setTotalWomenUseContraceptiveRing(int totalWomenUseContraceptiveRing) {
        this.totalWomenUseContraceptiveRing = totalWomenUseContraceptiveRing;
    }

    public int getTotalWomenInsteadRing() {
        return totalWomenInsteadRing;
    }

    public void setTotalWomenInsteadRing(int totalWomenInsteadRing) {
        this.totalWomenInsteadRing = totalWomenInsteadRing;
    }

    public int getTotalWomenStopContraceptiveRing() {
        return totalWomenStopContraceptiveRing;
    }

    public void setTotalWomenStopContraceptiveRing(int totalWomenStopContraceptiveRing) {
        this.totalWomenStopContraceptiveRing = totalWomenStopContraceptiveRing;
    }

    public int getTotalMenSterilization() {
        return totalMenSterilization;
    }

    public void setTotalMenSterilization(int totalMenSterilization) {
        this.totalMenSterilization = totalMenSterilization;
    }

    public int getTotalWomenSterilization() {
        return totalWomenSterilization;
    }

    public void setTotalWomenSterilization(int totalWomenSterilization) {
        this.totalWomenSterilization = totalWomenSterilization;
    }

    public int getTotalWomenUseImplantedDrug() {
        return totalWomenUseImplantedDrug;
    }

    public void setTotalWomenUseImplantedDrug(int totalWomenUseImplantedDrug) {
        this.totalWomenUseImplantedDrug = totalWomenUseImplantedDrug;
    }

    public int getTotalWomenInsteadImplantedDrug() {
        return totalWomenInsteadImplantedDrug;
    }

    public void setTotalWomenInsteadImplantedDrug(int totalWomenInsteadImplantedDrug) {
        this.totalWomenInsteadImplantedDrug = totalWomenInsteadImplantedDrug;
    }

    public int getTotalWomenStopImplantedDrug() {
        return totalWomenStopImplantedDrug;
    }

    public void setTotalWomenStopImplantedDrug(int totalWomenStopImplantedDrug) {
        this.totalWomenStopImplantedDrug = totalWomenStopImplantedDrug;
    }
}
