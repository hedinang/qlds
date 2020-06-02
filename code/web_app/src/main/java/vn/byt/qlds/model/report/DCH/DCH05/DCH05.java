package vn.byt.qlds.model.report.DCH.DCH05;

import vn.byt.qlds.model.report.DCH.DCH;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCH05 extends DCH<DCH05Sub> {
    private DCH05Sub dch05Total = new DCH05Sub();

    public DCH05() {
        dch05Total.setNationCategoryName("Tổng cộng");
        dch05Total.setMale(0);
        dch05Total.setFemale(0);
        subs.add(dch05Total);
    }

    @Override
    public ReportTemplate addSub(DCH05Sub sub) {
        dch05Total.setMale(dch05Total.getMale() + sub.getMale());
        dch05Total.setFemale(dch05Total.getFemale() + sub.getFemale());
        return super.addSub(sub);
    }
}
