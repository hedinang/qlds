package vn.byt.qlds.model.report.DCH.DCH05;

import vn.byt.qlds.model.report.DCH.DCHSub;

public class DCH05Sub extends DCHSub {
    private String displayName;
    private int total;
    private int male;
    private int female;

    public String getNationCategoryName() {
        return displayName;
    }

    public void setNationCategoryName(String nationCategoryName) {
        this.displayName = nationCategoryName;
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
