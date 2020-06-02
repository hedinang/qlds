package vn.byt.qlds.model.unit;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class UnitCategoryRequest {
    @NotEmpty(message = "Tên đơn vị không được để trống!")
    @Length(min = 1, max = 150)
    public String name;
    @NotEmpty(message = "BriefName không được để trống!")
    public String briefName;
    @NotEmpty(message = "Mã không được để trống!")
    @Length(min = 1, max = 10)
    public String code;
    public Integer levels;
    @NotEmpty(message = "Parent không được để trống!")
    public String parent;
    @NotEmpty(message = "Zone không được để trống!")
    public String zone;
    @NotEmpty(message = "Area không được để trống!")
    public String area;
    public String note;
    public Integer isActive;

}
