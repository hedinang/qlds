package vn.byt.qlds.model.gender;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class GenderRequest {
    @NotEmpty(message = "Tên giới tính không được để trống")
    @Length(min = 1, max = 10, message = "Tên giới tính từ 1 đến 10 ký tự")
    public String genderName;
}
