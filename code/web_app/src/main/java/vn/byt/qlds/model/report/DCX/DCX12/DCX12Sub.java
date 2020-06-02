package vn.byt.qlds.model.report.DCX.DCX12;

import vn.byt.qlds.model.report.DCX.DCXSub;

public class DCX12Sub extends DCXSub {
    private String displayName;
    private int total;
    private int male;
    private int female;
    private int totalNotReadWrite;
    private int nrwMale; /*Not Read Write chưa biết đọc viết giới tính NAM */
    private int nrwFemale; /*Not Read Write chưa biết đọc viết giới tính Nữ */

    public int getNrwMale() {
        return nrwMale;
    }

    public void setNrwMale(int nrwMale) {
        this.nrwMale = nrwMale;
        this.totalNotReadWrite = this.nrwMale + this.nrwFemale;
    }

    public int getNrwFemale() {
        return nrwFemale;
    }

    public void setNrwFemale(int nrwFemale) {
        this.nrwFemale = nrwFemale;
        this.totalNotReadWrite = this.nrwMale + this.nrwFemale;
    }

    public int getTotalNotReadWrite() {
        return totalNotReadWrite;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
