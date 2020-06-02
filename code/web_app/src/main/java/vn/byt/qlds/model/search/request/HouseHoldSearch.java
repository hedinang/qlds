package vn.byt.qlds.model.search.request;

public class HouseHoldSearch {

    int limit;
    int page;
    String regionId;//
    String addressId;//
    String houseHoldCode;// ho so
    String houseHoldNumber;// so nha ung voi ten chu ho
    String personName;//
    int personNumber;// so nguoi
    public String methodPerson;
    int female15To49Number;// so nu tu 15 to 49
    public String methodFemale;
    boolean isBigHouseHold;// la ho tap the
    String fromNHouseHoldDate; // tu ngay nhap ho moi
    String toNHouseHoldDate;// den ngay nhap ho moi
    String fromFlacDate;// tu ngay bien dong
    String toFlacDate;// den ngay biet dong

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getHouseHoldCode() {
        return houseHoldCode;
    }

    public void setHouseHoldCode(String houseHoldCode) {
        this.houseHoldCode = houseHoldCode;
    }

    public String getHouseHoldNumber() {
        return houseHoldNumber;
    }

    public void setHouseHoldNumber(String houseHoldNumber) {
        this.houseHoldNumber = houseHoldNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
    }

    public int getFemale15To49Number() {
        return female15To49Number;
    }

    public void setFemale15To49Number(int female15To49Number) {
        this.female15To49Number = female15To49Number;
    }

    public boolean isBigHouseHold() {
        return isBigHouseHold;
    }

    public void setBigHouseHold(boolean bigHouseHold) {
        isBigHouseHold = bigHouseHold;
    }

    public String getFromNHouseHoldDate() {
        return fromNHouseHoldDate;
    }

    public void setFromNHouseHoldDate(String fromNHouseHoldDate) {
        this.fromNHouseHoldDate = fromNHouseHoldDate;
    }

    public String getToNHouseHoldDate() {
        return toNHouseHoldDate;
    }

    public void setToNHouseHoldDate(String toNHouseHoldDate) {
        this.toNHouseHoldDate = toNHouseHoldDate;
    }

    public String getFromFlacDate() {
        return fromFlacDate;
    }

    public void setFromFlacDate(String fromFlacDate) {
        this.fromFlacDate = fromFlacDate;
    }

    public String getToFlacDate() {
        return toFlacDate;
    }

    public void setToFlacDate(String toFlacDate) {
        this.toFlacDate = toFlacDate;
    }
}
