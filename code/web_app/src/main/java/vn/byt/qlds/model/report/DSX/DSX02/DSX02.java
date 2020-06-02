package vn.byt.qlds.model.report.DSX.DSX02;

import vn.byt.qlds.model.report.ReportTemplate;


public class DSX02 extends ReportTemplate<DSX02Sub> {


    private DSX02Sub dsx02Total = new DSX02Sub();

    public DSX02() {
        dsx02Total.setAddress("Toàn xã");
        subs.add(dsx02Total);
    }

    @Override
    public ReportTemplate addSub(DSX02Sub sub) {
        dsx02Total.setTotalComePerson(dsx02Total.getTotalComePerson() + sub.getTotalComePerson());
        dsx02Total.setTotalDiePerson(dsx02Total.getTotalDiePerson() + sub.getTotalDiePerson());
        dsx02Total.setTotalDivorcePerson(dsx02Total.getTotalDivorcePerson() + sub.getTotalDivorcePerson());
        dsx02Total.setTotalGoPerson(dsx02Total.getTotalGoPerson() + sub.getTotalGoPerson());
        dsx02Total.setTotalHouseHold(dsx02Total.getTotalHouseHold() + sub.getTotalHouseHold());
        dsx02Total.setTotalMarryPerson(dsx02Total.getTotalMarryPerson() + sub.getTotalMarryPerson());
        dsx02Total.setTotalMenSterilization(dsx02Total.getTotalMenSterilization() + sub.getTotalMenSterilization());
        dsx02Total.setTotalNotBigHouseHold(dsx02Total.getTotalNotBigHouseHold() + sub.getTotalNotBigHouseHold());
        dsx02Total.setTotalNoUseContraceptive(dsx02Total.getTotalNoUseContraceptive() + sub.getTotalNoUseContraceptive());
        dsx02Total.setTotalResident(dsx02Total.getTotalResident() + sub.getTotalResident());
        dsx02Total.setTotalUseContraceptive(dsx02Total.getTotalUseContraceptive() + sub.getTotalTwoChild());
        dsx02Total.setTotalUseDrinkDrug(dsx02Total.getTotalUseDrinkDrug() + sub.getTotalUseDrinkDrug());
        dsx02Total.setTotalUseImplantDrug(dsx02Total.getTotalUseImplantDrug() + sub.getTotalUseImplantDrug());
        dsx02Total.setTotalUseInjectDrug(dsx02Total.getTotalUseInjectDrug() + sub.getTotalUseInjectDrug());
        dsx02Total.setTotalUseOtherMethod(dsx02Total.getTotalUseOtherMethod() + sub.getTotalUseOtherMethod());
        dsx02Total.setTotalUsePlastic(dsx02Total.getTotalUsePlastic() + sub.getTotalUsePlastic());
        dsx02Total.setTotalUseRing(dsx02Total.getTotalUseRing() + sub.getTotalUseRing());
        dsx02Total.setTotalWomenLt49(dsx02Total.getTotalWomenLt49() + sub.getTotalWomenLt49());
        dsx02Total.setTotalWomenLt49HaveHusband(dsx02Total.getTotalWomenLt49HaveHusband() + sub.getTotalWomenLt49HaveHusband());
        dsx02Total.setTotalWomenSterilization(dsx02Total.getTotalWomenSterilization() + sub.getTotalWomenSterilization());

        return super.addSub(sub);
    }

    // xet 1 so truong dac thu chi tinh tong
    // ko tinh rieng tung don vi
    @Override
    public ReportTemplate setTotal(DSX02Sub sub) {
        dsx02Total.setTotalCTV(sub.getTotalCTV());
        dsx02Total.setTotalNewCTV(sub.getTotalNewCTV());
        dsx02Total.setTotalWomenCTV(sub.getTotalWomenCTV());
        dsx02Total.setTotalThreeChild(sub.getTotalThreeChild());
        dsx02Total.setTotalTwoChild(sub.getTotalTwoChild());
        return this;
    }
}
