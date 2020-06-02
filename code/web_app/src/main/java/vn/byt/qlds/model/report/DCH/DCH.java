package vn.byt.qlds.model.report.DCH;

import vn.byt.qlds.model.report.ReportSub;
import vn.byt.qlds.model.report.ReportTemplate;

import java.util.Collection;
import java.util.List;

public abstract class DCH<T extends ReportSub> extends ReportTemplate<T> {
    @Override
    public ReportTemplate addSub(T sub) {
        return super.addSub(sub);
    }

    @Override
    public ReportTemplate addSubs(Collection<T> subs) {
        return super.addSubs(subs);
    }

    @Override
    public List<T> getSubs() {
        return super.getSubs();
    }
}
