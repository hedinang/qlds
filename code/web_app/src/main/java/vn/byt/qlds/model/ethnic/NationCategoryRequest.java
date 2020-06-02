package vn.byt.qlds.model.ethnic;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class NationCategoryRequest {
    @NotEmpty(message = "Mã dân tộc không được để trống")
    @Length(min = 1, max = 10, message = "Mã dân tộc có độ dài từ 1 đến 10")
    public String code;
    @NotEmpty(message = "Tên dân tộc không được để trống")
    @Length(min = 1, max = 150, message = "Tên dân tộc có độ dài từ 1 đến 150")
    public String name;
    @Length(max = 255, message = "Ghi chú tối đa 255 ký tự")
    public String notes;
    public Boolean isMinority;
    public Boolean isActive;
}
