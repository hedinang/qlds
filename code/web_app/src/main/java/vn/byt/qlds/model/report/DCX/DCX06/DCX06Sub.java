package vn.byt.qlds.model.report.DCX.DCX06;

import vn.byt.qlds.model.report.DCX.DCXSub;

public class DCX06Sub extends DCXSub {
    private String displayName;
    private int fromAge;
    private int toAge;
    private int total;
    private int notWriteRead;
    private int primarySchool;
    private int secondarySchool;
    private int highSchool;
    private int intermediate; /*trung cấp*/
    private int college;    /*cao đẳng*/
    private int university; /*đại học*/
    private int gtUniversity; /* > đại học*/

    private void updateTotal() {
        this.total = this.notWriteRead
                + this.primarySchool
                + this.secondarySchool
                + this.highSchool
                + this.intermediate
                + this.college
                + this.university
                + this.gtUniversity;
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

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public int getNotWriteRead() {
        return notWriteRead;
    }

    public void setNotWriteRead(int notWriteRead) {
        this.notWriteRead = notWriteRead;
        updateTotal();
    }

    public int getPrimarySchool() {
        return primarySchool;
    }

    public void setPrimarySchool(int primarySchool) {
        this.primarySchool = primarySchool;
        updateTotal();
    }

    public int getSecondarySchool() {
        return secondarySchool;
    }

    public void setSecondarySchool(int secondarySchool) {
        this.secondarySchool = secondarySchool;
        updateTotal();
    }

    public int getHighSchool() {
        return highSchool;
    }

    public void setHighSchool(int highSchool) {
        this.highSchool = highSchool;
        updateTotal();
    }

    public int getIntermediate() {
        return intermediate;
    }

    public void setIntermediate(int intermediate) {
        this.intermediate = intermediate;
        updateTotal();
    }

    public int getCollege() {
        return college;
    }

    public void setCollege(int college) {
        this.college = college;
        updateTotal();
    }

    public int getUniversity() {
        return university;
    }

    public void setUniversity(int university) {
        this.university = university;
        updateTotal();
    }

    public int getGtUniversity() {
        return gtUniversity;
    }

    public void setGtUniversity(int gtUniversity) {
        this.gtUniversity = gtUniversity;
        updateTotal();
    }

    public int getTotal() {
        return total;
    }
}
