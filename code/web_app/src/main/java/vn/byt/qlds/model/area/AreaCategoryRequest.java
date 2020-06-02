package vn.byt.qlds.model.area;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AreaCategoryRequest {
    @NotEmpty(message = "Tên vùng không được để trống")
    @Length(min = 1, max = 150, message = "Tên vùng từ 1 đến 150 ký tự")
    public String areaName;
    @NotNull(message = "IsActive is invalid!")
    public Boolean isActive;
}
