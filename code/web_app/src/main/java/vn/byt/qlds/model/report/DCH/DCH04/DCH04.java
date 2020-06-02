package vn.byt.qlds.model.report.DCH.DCH04;

import vn.byt.qlds.model.report.DCH.DCH;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCH04 extends DCH<DCH04Sub> {

    private DCH04Sub dch04SubTotal = new DCH04Sub();

    public DCH04() {
        this.dch04SubTotal.setDisplayName("Tổng cộng");
        this.dch04SubTotal.setSingle(0);
        this.dch04SubTotal.setMarried(0);
        this.dch04SubTotal.setWidow(0);
        this.dch04SubTotal.setSeparateDivorce(0);
        subs.add(dch04SubTotal);
    }

    @Override
    public ReportTemplate addSub(DCH04Sub sub) {
        this.dch04SubTotal.setSingle(this.dch04SubTotal.getSingle() + sub.getSingle());
        this.dch04SubTotal.setMarried(this.dch04SubTotal.getMarried() + sub.getMarried());
        this.dch04SubTotal.setWidow(this.dch04SubTotal.getWidow() + sub.getWidow());
        this.dch04SubTotal.setSeparateDivorce(this.dch04SubTotal.getSeparateDivorce() + sub.getSeparateDivorce());
        return super.addSub(sub);
    }
}
