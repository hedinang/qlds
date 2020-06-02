package vn.byt.qlds.model.marial;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class MaritalStatusRequest {
    @NotEmpty(message = "Tên tình trạng hôn nhân không được để trống")
    @Length(min = 1, max = 150, message = "Tên tình trạng hôn nhân từ 1 đến 10 ký tự")
    public String maritalName;
    public Boolean isActive;
}
