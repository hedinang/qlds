package vn.byt.qlds.model.report.DCX.DCX01;

import vn.byt.qlds.model.report.ReportTemplate;

public class DCX01 extends ReportTemplate<DCX01Sub> {

    private DCX01Sub dcx01Total = new DCX01Sub();

    public DCX01() {
        dcx01Total.setAddress("Tổng cộng");
        subs.add(dcx01Total);
    }

    @Override
    public ReportTemplate addSub(DCX01Sub sub) {
        dcx01Total.setTotalHouseHold(dcx01Total.totalHouseHold + sub.totalHouseHold);
        dcx01Total.setTotalPerson(dcx01Total.totalPerson + sub.totalPerson);
        dcx01Total.setTotalMen(dcx01Total.totalMen + sub.totalMen);
        dcx01Total.setTotalWomen(dcx01Total.totalWomen + sub.totalWomen);
        return super.addSub(sub);
    }
}
