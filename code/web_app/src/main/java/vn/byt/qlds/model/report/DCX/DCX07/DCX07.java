package vn.byt.qlds.model.report.DCX.DCX07;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX07 extends DCX<DCX07Sub> {
    private DCX07Sub dcx17Total = new DCX07Sub();

    public DCX07() {
        dcx17Total.setDisplayName("Tổng cộng");
        subs.add(dcx17Total);
    }

    @Override
    public ReportTemplate addSub(DCX07Sub dcx07Sub) {
        dcx17Total.setMale(dcx17Total.getMale() + dcx07Sub.getMale());
        dcx17Total.setFemale(dcx17Total.getFemale() + dcx07Sub.getFemale());
        return super.addSub(dcx07Sub);
    }

}
