package vn.byt.qlds.model.report.DSX.DSX01;

import vn.byt.qlds.model.report.ReportTemplate;

public class DSX01 extends ReportTemplate<DSX01Sub> {
    private DSX01Sub dsx01Total = new DSX01Sub();

    public DSX01() {
        dsx01Total.setAddress("Toàn xã");
        subs.add(dsx01Total);
    }

    @Override
    public ReportTemplate addSub(DSX01Sub sub) {
        dsx01Total.setTotalBoy(dsx01Total.getTotalBoy() + sub.getTotalBoy());
        dsx01Total.setTotalChild(dsx01Total.getTotalChild() + sub.getTotalChild());
        dsx01Total.setTotalChildOfFamilyGt3(dsx01Total.getTotalChildOfFamilyGt3() + sub.getTotalChildOfFamilyGt3());
        dsx01Total.setTotalChildOfMotherAgeLt20(dsx01Total.getTotalChildOfMotherAgeLt20() + sub.getTotalChildOfMotherAgeLt20());
        dsx01Total.setTotalGirl(dsx01Total.getTotalGirl() + sub.getTotalGirl());
        dsx01Total.setTotalMenSterilization(dsx01Total.getTotalMenSterilization() + sub.getTotalMenSterilization());
        dsx01Total.setTotalWomenInsteadImplantedDrug(dsx01Total.getTotalWomenInsteadImplantedDrug() + sub.getTotalWomenInsteadImplantedDrug());
        dsx01Total.setTotalWomenInsteadRing(dsx01Total.getTotalWomenInsteadRing() + sub.getTotalWomenInsteadRing());
        dsx01Total.setTotalWomenSterilization(dsx01Total.getTotalWomenSterilization() + sub.getTotalWomenSterilization());
        dsx01Total.setTotalWomenStopContraceptiveRing(dsx01Total.getTotalWomenStopContraceptiveRing() + sub.getTotalWomenStopContraceptiveRing());
        dsx01Total.setTotalWomenStopImplantedDrug(dsx01Total.getTotalWomenStopImplantedDrug() + sub.getTotalWomenStopImplantedDrug());
        dsx01Total.setTotalWomenUseContraceptiveRing(dsx01Total.getTotalWomenUseContraceptiveRing() + sub.getTotalWomenUseContraceptiveRing());
        dsx01Total.setTotalWomenUseImplantedDrug(dsx01Total.getTotalWomenUseImplantedDrug() + sub.getTotalWomenUseImplantedDrug());
        return super.addSub(sub);
    }

}
