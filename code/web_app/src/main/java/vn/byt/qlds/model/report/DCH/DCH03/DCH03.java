package vn.byt.qlds.model.report.DCH.DCH03;


import vn.byt.qlds.model.report.DCH.DCH;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCH03 extends DCH<DCH03Sub> {

    private DCH03Sub dch03Total = new DCH03Sub();

    public DCH03() {
        dch03Total.setDisplayName("Tổng cộng");
        dch03Total.setMale(0);
        dch03Total.setFemale(0);
        subs.add(dch03Total);
    }

    @Override
    public ReportTemplate addSub(DCH03Sub sub) {
        dch03Total.setMale(dch03Total.getMale() + sub.getMale());
        dch03Total.setFemale(dch03Total.getFemale() + sub.getFemale());
        return super.addSub(sub);
    }
}
