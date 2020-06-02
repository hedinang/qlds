package vn.byt.qlds.model.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public abstract class ReportTemplate<T extends ReportSub> {
    public String rcId;
    public String headerL;
    public String headerR;
    public String subtitle;
    public String caption1;
    public String caption2;
    public String caption3;
    public String footerL;
    public String footerR;
    public String showFooter;
    public String regionName;
    public String startTime;
    public String endTime;
    public int month;
    public int quater;
    public int year;
    //data
    protected List<T> subs = new ArrayList<>();

    public ReportTemplate<T> addSub(T sub) {
        subs.add(sub);
        return this;
    }

    public ReportTemplate<T> addSubs(Collection<T> subs) {
        for (T t : subs) {
            addSub(t);
        }
        return this;
    }

    public List<T> getSubs() {
        return subs;
    }

    public ReportTemplate<T> setTotal(T total) {
        return this;
    }
}

