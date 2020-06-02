package vn.byt.qlds.model.report.DSH.DSH01;

import vn.byt.qlds.model.report.ReportTemplate;


public class DSH01 extends ReportTemplate<DSH01Sub> {
    private DSH01Sub dsh01Sub = new DSH01Sub();

    public DSH01() {
        dsh01Sub.setAddress("Tổng cộng");
        subs.add(dsh01Sub);
    }

    @Override
    public ReportTemplate addSub(DSH01Sub sub) {
        dsh01Sub.setTotalBoy(dsh01Sub.totalBoy + sub.totalBoy);
        dsh01Sub.setTotalChild(dsh01Sub.totalChild + sub.totalChild);
        dsh01Sub.setTotalChildOfFamilyGt3(dsh01Sub.totalChildOfFamilyGt3 + sub.totalChildOfFamilyGt3);
        dsh01Sub.setTotalChildOfMotherAgeLt20(dsh01Sub.totalChildOfMotherAgeLt20 + sub.totalChildOfMotherAgeLt20);
        dsh01Sub.setTotalGirl(dsh01Sub.totalGirl + sub.totalGirl);
        dsh01Sub.setTotalMenSterilization(dsh01Sub.totalMenSterilization + sub.totalMenSterilization);
        dsh01Sub.setTotalWomenInsteadImplantedDrug(dsh01Sub.totalWomenInsteadImplantedDrug + sub.totalWomenInsteadImplantedDrug);
        dsh01Sub.setTotalWomenInsteadRing(dsh01Sub.totalWomenInsteadRing + sub.totalWomenInsteadRing);
        dsh01Sub.setTotalWomenSterilization(dsh01Sub.totalWomenSterilization + sub.totalWomenSterilization);
        dsh01Sub.setTotalWomenStopContraceptiveRing(dsh01Sub.totalWomenStopContraceptiveRing + sub.totalWomenStopContraceptiveRing);
        dsh01Sub.setTotalWomenStopImplantedDrug(dsh01Sub.totalWomenStopImplantedDrug + sub.totalWomenStopImplantedDrug);
        dsh01Sub.setTotalWomenUseContraceptiveRing(dsh01Sub.totalWomenUseContraceptiveRing + sub.totalWomenUseContraceptiveRing);
        dsh01Sub.setTotalWomenUseImplantedDrug(dsh01Sub.totalWomenUseImplantedDrug + sub.totalWomenUseImplantedDrug);

        return super.addSub(sub);
    }
}
