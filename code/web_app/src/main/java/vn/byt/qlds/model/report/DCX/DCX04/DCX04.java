package vn.byt.qlds.model.report.DCX.DCX04;

import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.ReportTemplate;

public class DCX04 extends DCX<DCX04Sub> {

    private DCX04Sub dcx04SubTotal = new DCX04Sub();

    public DCX04() {
        this.dcx04SubTotal.setDisplayName("Tổng cộng");
        this.dcx04SubTotal.setSingle(0);
        this.dcx04SubTotal.setMarried(0);
        this.dcx04SubTotal.setWidow(0);
        this.dcx04SubTotal.setSeparateDivorce(0);
        subs.add(dcx04SubTotal);
    }

    @Override
    public ReportTemplate addSub(DCX04Sub sub) {
        this.dcx04SubTotal.setSingle(this.dcx04SubTotal.getSingle() + sub.getSingle());
        this.dcx04SubTotal.setMarried(this.dcx04SubTotal.getMarried() + sub.getMarried());
        this.dcx04SubTotal.setWidow(this.dcx04SubTotal.getWidow() + sub.getWidow());
        this.dcx04SubTotal.setSeparateDivorce(this.dcx04SubTotal.getSeparateDivorce() + sub.getSeparateDivorce());
        return super.addSub(sub);
    }
}
