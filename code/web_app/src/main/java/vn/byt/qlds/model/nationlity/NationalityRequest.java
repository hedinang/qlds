package vn.byt.qlds.model.nationlity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class NationalityRequest {
    @NotEmpty(message = "Tên quốc tịch không được để trống")
    @Length(min = 1, max = 150, message = "Tên quốc tịch từ 1 đến 10 ký tự")
    public String nationalityName;
    @NotEmpty(message = "Tên quốc gia không được để trống")
    @Length(min = 1, max = 150, message = "Tên quốc gia từ 1 đến 10 ký tự")
    public String countryName;
    public Boolean isActive;
}
