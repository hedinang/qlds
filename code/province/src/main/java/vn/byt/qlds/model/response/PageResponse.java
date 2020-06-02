package vn.byt.qlds.model.response;

import java.io.Serializable;
import java.util.List;


public class PageResponse<T> implements Serializable {
    private List<T> list;
    private int page;
    private int total;
    public int limit;
//    public String direction = "";
//    public String property="";

    public PageResponse(List<T> list, int page, int total) {
        this.list = list;
        this.page = page;
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}


