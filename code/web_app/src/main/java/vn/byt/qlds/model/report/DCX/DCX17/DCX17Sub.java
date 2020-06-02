package vn.byt.qlds.model.report.DCX.DCX17;

import vn.byt.qlds.model.report.DCX.DCXSub;

public class DCX17Sub extends DCXSub {
    private String displayName;
    private int age;
    private int total;
    private int male;
    private int female;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
