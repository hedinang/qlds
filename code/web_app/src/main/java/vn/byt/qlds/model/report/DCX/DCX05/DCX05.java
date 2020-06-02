package vn.byt.qlds.model.report.DCX.DCX05;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX05 extends DCX<DCX05Sub> {

    private DCX05Sub dcx05Total = new DCX05Sub();

    public DCX05() {
        dcx05Total.setNationCategoryName("Tổng cộng");
        dcx05Total.setMale(0);
        dcx05Total.setFemale(0);
        subs.add(dcx05Total);
    }

    @Override
    public ReportTemplate addSub(DCX05Sub sub) {
        dcx05Total.setMale(dcx05Total.getMale() + sub.getMale());
        dcx05Total.setFemale(dcx05Total.getFemale() + sub.getFemale());
        return super.addSub(sub);
    }
}
