package vn.byt.qlds.model.search.response;

import java.util.ArrayList;
import java.util.List;

public class SHouseHoldResponse {
    public String houseHoldCode;// ho so
    public String houseHoldNumber;// so nha ung voi ten chu ho
    public List<String> persons = new ArrayList<>();//
    public int female15To49Number;// so nu tu 15 to 49
    public boolean isBigHouseHold;// la ho tap the
    public String nHouseHoldDate; // ngay nhap ho moi
    public String flacDate;// ngay bien dong
}
