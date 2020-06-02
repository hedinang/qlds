package vn.byt.qlds.model.report.DCX.DCX02;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX02 extends DCX<DCX02Sub> {
    private DCX02Sub dcx02Total = new DCX02Sub();

    public DCX02() {
        dcx02Total.setAddress("Tổng cộng");
        subs.add(dcx02Total);
    }

    @Override
    public ReportTemplate addSub(DCX02Sub sub) {
        dcx02Total.setTotalBirth(dcx02Total.getTotalBirth() + sub.getTotalBirth());
        dcx02Total.setTotalCome(dcx02Total.getTotalCome() + sub.getTotalCome());
        dcx02Total.setTotalDie(dcx02Total.getTotalDie() + sub.getTotalDie());
        dcx02Total.setTotalEnd(dcx02Total.getTotalEnd() + sub.getTotalEnd());
        dcx02Total.setTotalGo(dcx02Total.getTotalGo() + sub.getTotalGo());
        return super.addSub(sub);
    }
}
