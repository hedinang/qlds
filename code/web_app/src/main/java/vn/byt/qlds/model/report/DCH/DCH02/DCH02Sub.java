package vn.byt.qlds.model.report.DCH.DCH02;

import vn.byt.qlds.model.report.ReportSub;

public class DCH02Sub extends ReportSub {
    String address;
    int totalBirth;// tong sinh
    int totalDie;// tong chet
    int totalCome;// tong den
    int totalGo;// tong di
    int totalEnd;// tong den ngay


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalBirth() {
        return totalBirth;
    }

    public void setTotalBirth(int totalBirth) {
        this.totalBirth = totalBirth;
    }

    public int getTotalDie() {
        return totalDie;
    }

    public void setTotalDie(int totalDie) {
        this.totalDie = totalDie;
    }

    public int getTotalCome() {
        return totalCome;
    }

    public void setTotalCome(int totalCome) {
        this.totalCome = totalCome;
    }

    public int getTotalGo() {
        return totalGo;
    }

    public void setTotalGo(int totalGo) {
        this.totalGo = totalGo;
    }

    public int getTotalEnd() {
        return totalEnd;
    }

    public void setTotalEnd(int totalEnd) {
        this.totalEnd = totalEnd;
    }
}
