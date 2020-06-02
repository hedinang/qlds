package vn.byt.qlds.model.report.DCX.DCX06;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX06 extends DCX<DCX06Sub> {
    private DCX06Sub dcx06Total = new DCX06Sub();

    public DCX06() {
        dcx06Total.setDisplayName("Tổng cộng");
        subs.add(dcx06Total);
    }

    @Override
    public ReportTemplate addSub(DCX06Sub sub) {
        dcx06Total.setNotWriteRead(dcx06Total.getNotWriteRead() + sub.getNotWriteRead());
        dcx06Total.setPrimarySchool(dcx06Total.getPrimarySchool() + sub.getPrimarySchool());
        dcx06Total.setSecondarySchool(dcx06Total.getSecondarySchool() + sub.getSecondarySchool());
        dcx06Total.setHighSchool(dcx06Total.getHighSchool() + sub.getHighSchool());
        dcx06Total.setIntermediate(dcx06Total.getIntermediate() + sub.getIntermediate());
        dcx06Total.setCollege(dcx06Total.getCollege() + sub.getCollege());
        dcx06Total.setUniversity(dcx06Total.getUniversity() + sub.getUniversity());
        dcx06Total.setGtUniversity(dcx06Total.getGtUniversity() + sub.getGtUniversity());
        return super.addSub(sub);
    }

}
