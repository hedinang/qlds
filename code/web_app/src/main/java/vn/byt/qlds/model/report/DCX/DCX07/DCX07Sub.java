package vn.byt.qlds.model.report.DCX.DCX07;

import vn.byt.qlds.model.report.DCX.DCXSub;

public class DCX07Sub extends DCXSub {
    private String displayName;
    private int fromAge;
    private int toAge;
    private int total;
    private int male;
    private int female;

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getTotal() {
        return total;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
        this.total = this.male + this.female;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
        this.total = this.male + this.female;
    }
}
