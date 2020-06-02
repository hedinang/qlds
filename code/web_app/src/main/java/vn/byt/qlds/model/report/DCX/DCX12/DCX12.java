package vn.byt.qlds.model.report.DCX.DCX12;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX12 extends DCX<DCX12Sub> {

    private DCX12Sub dcx12Total = new DCX12Sub();

    public DCX12() {
        dcx12Total.setDisplayName("Tổng cộng");
        subs.add(dcx12Total);
    }

    @Override
    public ReportTemplate addSub(DCX12Sub sub) {
        dcx12Total.setMale(dcx12Total.getMale() + sub.getMale());
        dcx12Total.setFemale(dcx12Total.getFemale() + sub.getFemale());
        dcx12Total.setNrwMale(dcx12Total.getNrwMale() + sub.getNrwMale());
        dcx12Total.setNrwFemale(dcx12Total.getNrwFemale() + sub.getNrwFemale());
        return super.addSub(sub);
    }

}
