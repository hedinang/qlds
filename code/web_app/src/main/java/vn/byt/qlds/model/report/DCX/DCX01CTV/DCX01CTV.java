package vn.byt.qlds.model.report.DCX.DCX01CTV;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX01CTV extends DCX<DCX01CTVSub> {
    private DCX01CTVSub dcx01CTVTotal = new DCX01CTVSub();

    public DCX01CTV() {
        dcx01CTVTotal.setCollaboratorName("Tổng cộng");
        subs.add(dcx01CTVTotal);
    }

    @Override
    public ReportTemplate addSub(DCX01CTVSub sub) {
        dcx01CTVTotal.setTotalHouseHold(dcx01CTVTotal.getTotalHouseHold() + sub.getTotalHouseHold());
        dcx01CTVTotal.setTotalMen(dcx01CTVTotal.getTotalMen() + sub.getTotalMen());
        dcx01CTVTotal.setTotalWomen(dcx01CTVTotal.getTotalWomen() + sub.getTotalWomen());
        dcx01CTVTotal.setTotalPerson(dcx01CTVTotal.getTotalPerson() + sub.getTotalPerson());
        return super.addSub(sub);
    }
}
