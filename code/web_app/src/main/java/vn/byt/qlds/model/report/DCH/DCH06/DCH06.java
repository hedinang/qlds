package vn.byt.qlds.model.report.DCH.DCH06;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCH06 extends DCX<DCH06Sub> {
    private DCH06Sub dch06Total = new DCH06Sub();

    public DCH06() {
        dch06Total.setDisplayName("Tổng cộng");
        subs.add(dch06Total);
    }

    @Override
    public synchronized ReportTemplate addSub(DCH06Sub sub) {
        dch06Total.setNotWriteRead(dch06Total.getNotWriteRead() + sub.getNotWriteRead());
        dch06Total.setPrimarySchool(dch06Total.getPrimarySchool() + sub.getPrimarySchool());
        dch06Total.setSecondarySchool(dch06Total.getSecondarySchool() + sub.getSecondarySchool());
        dch06Total.setHighSchool(dch06Total.getHighSchool() + sub.getHighSchool());
        dch06Total.setIntermediate(dch06Total.getIntermediate() + sub.getIntermediate());
        dch06Total.setCollege(dch06Total.getCollege() + sub.getCollege());
        dch06Total.setUniversity(dch06Total.getUniversity() + sub.getUniversity());
        dch06Total.setGtUniversity(dch06Total.getGtUniversity() + sub.getGtUniversity());
        return super.addSub(sub);

    }

}
