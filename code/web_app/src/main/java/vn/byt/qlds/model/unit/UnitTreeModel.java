package vn.byt.qlds.model.unit;

public class UnitTreeModel {
    public Integer id;
    public String code;
    public String name;

    public UnitTreeModel(UnitResponse unitResponse){
        this.id = unitResponse.id;
        this.code = unitResponse.code;
        this.name = unitResponse.name;
    }
}
