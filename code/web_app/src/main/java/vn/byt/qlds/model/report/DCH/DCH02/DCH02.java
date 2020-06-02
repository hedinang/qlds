package vn.byt.qlds.model.report.DCH.DCH02;

import vn.byt.qlds.model.report.ReportTemplate;

public class DCH02 extends ReportTemplate<DCH02Sub> {
    private DCH02Sub dch02Total = new DCH02Sub();

    public DCH02() {
        dch02Total.setAddress("Tổng cộng");
        subs.add(dch02Total);
    }

    @Override
    public ReportTemplate addSub(DCH02Sub sub) {
        dch02Total.setTotalBirth(dch02Total.getTotalBirth() + sub.getTotalBirth());
        dch02Total.setTotalCome(dch02Total.getTotalCome() + sub.getTotalCome());
        dch02Total.setTotalDie(dch02Total.getTotalDie() + sub.getTotalDie());
        dch02Total.setTotalEnd(dch02Total.getTotalEnd() + sub.getTotalEnd());
        dch02Total.setTotalGo(dch02Total.getTotalGo() + sub.getTotalGo());
        return super.addSub(sub);
    }
}
