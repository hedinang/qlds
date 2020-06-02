package vn.byt.qlds.model.dead;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;


public class DeadRequest {
    @NotEmpty(message = "A field code is invalid!")
    @Length(min = 1, max = 10, message = "Mã từ 1 đến 10 ký tự")
    public String code;
    @NotEmpty(message = "A field name is invalid!")
    public String name;
    public Boolean isActive;
}
