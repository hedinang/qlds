package vn.byt.qlds.model.report.DCH.DCH04;

import vn.byt.qlds.model.report.DCH.DCHSub;

public class DCH04Sub extends DCHSub {
    private String displayName;
    private int total;
    private int single; /*độc thân*/
    private int married; /*đã kết hôn*/
    private int widow; /*góa phụ*/
    private int separate_divorce; /*ly thân hoặc ly dị*/

    public int getTotal() {
        return total;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
        this.total = this.single + this.married + this.widow + this.separate_divorce;
    }

    public int getMarried() {
        return married;
    }

    public void setMarried(int married) {
        this.married = married;
        this.total = this.single + this.married + this.widow + this.separate_divorce;
    }

    public int getWidow() {
        return widow;
    }

    public void setWidow(int widow) {
        this.widow = widow;
        this.total = this.single + this.married + this.widow + this.separate_divorce;
    }

    public int getSeparateDivorce() {
        return separate_divorce;
    }

    public void setSeparateDivorce(int separate_divorce) {
        this.separate_divorce = separate_divorce;
        this.total = this.single + this.married + this.widow + this.separate_divorce;
    }
}
