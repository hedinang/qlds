package vn.byt.qlds.model.report.DCX.DCX03;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX03 extends DCX<DCX03Sub> {

    private DCX03Sub dcx05Total = new DCX03Sub();

    public DCX03() {
        dcx05Total.setDisplayName("Tổng cộng");
        dcx05Total.setMale(0);
        dcx05Total.setFemale(0);
        subs.add(dcx05Total);
    }

    @Override
    public ReportTemplate addSub(DCX03Sub sub) {
        dcx05Total.setMale(dcx05Total.getMale() + sub.getMale());
        dcx05Total.setFemale(dcx05Total.getFemale() + sub.getFemale());
        return super.addSub(sub);
    }
}
