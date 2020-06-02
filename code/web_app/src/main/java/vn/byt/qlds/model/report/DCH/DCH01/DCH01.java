package vn.byt.qlds.model.report.DCH.DCH01;

import vn.byt.qlds.model.report.ReportTemplate;

public class DCH01 extends ReportTemplate<DCH01Sub> {

    private DCH01Sub dch01Total = new DCH01Sub();

    public DCH01() {
        dch01Total.setAddress("Tổng cộng");
        subs.add(dch01Total);
    }

    @Override
    public ReportTemplate addSub(DCH01Sub sub) {
        dch01Total.setTotalHouseHold(dch01Total.totalHouseHold + sub.totalHouseHold);
        dch01Total.setTotalPerson(dch01Total.totalPerson + sub.totalPerson);
        dch01Total.setTotalMen(dch01Total.totalMen + sub.totalMen);
        dch01Total.setTotalWomen(dch01Total.totalWomen + sub.totalWomen);
        return super.addSub(sub);
    }
}
