package vn.byt.qlds.model.report.DCH.DCH01;

import vn.byt.qlds.model.report.ReportSub;

public class DCH01Sub extends ReportSub {
    String address;
    int totalHouseHold;// tong bat dau tu ngay
    int totalPerson;// tong sinh
    int totalMen;// tong chet
    int totalWomen;// tong den

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalHouseHold() {
        return totalHouseHold;
    }

    public void setTotalHouseHold(int totalHouseHold) {
        this.totalHouseHold = totalHouseHold;
    }

    public int getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        this.totalPerson = totalPerson;
    }

    public int getTotalMen() {
        return totalMen;
    }

    public void setTotalMen(int totalMen) {
        this.totalMen = totalMen;
    }

    public int getTotalWomen() {
        return totalWomen;
    }

    public void setTotalWomen(int totalWomen) {
        this.totalWomen = totalWomen;
    }
}
