package vn.byt.qlds.model.unit;

import java.util.ArrayList;
import java.util.List;

public class UnitTree {
    public Integer id;
    public String code;
    public String name;
    public List<UnitTree> children;

    public UnitTree(UnitResponse value) {
        this.id = value.id;
        this.code = value.code;
        this.name = value.name;
        this.children = new ArrayList<>();
    }
}
