package vn.byt.qlds.model.report.DCX.DCX05;

import vn.byt.qlds.model.report.DCX.DCXSub;

public class DCX05Sub extends DCXSub {
    private String nationCategoryName;
    private int total;
    private int male;
    private int female;

    public String getNationCategoryName() {
        return nationCategoryName;
    }

    public void setNationCategoryName(String nationCategoryName) {
        this.nationCategoryName = nationCategoryName;
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
