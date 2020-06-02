package vn.byt.qlds.model.report.DCX.DCX17;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX17 extends DCX<DCX17Sub> {

    private DCX17Sub dcx17Total = new DCX17Sub();

    public DCX17() {
        dcx17Total.setDisplayName("Tổng cộng");
        subs.add(dcx17Total);
    }

    @Override
    public ReportTemplate addSub(DCX17Sub sub) {
        dcx17Total.setMale(dcx17Total.getMale() + sub.getMale());
        dcx17Total.setFemale(dcx17Total.getFemale() + sub.getFemale());
        return super.addSub(sub);
    }

}
