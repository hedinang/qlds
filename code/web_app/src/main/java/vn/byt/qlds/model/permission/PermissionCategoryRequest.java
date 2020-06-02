package vn.byt.qlds.model.permission;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

public class PermissionCategoryRequest {
    @NotEmpty(message = "A field name is invalid!")
    @Length(min = 1, max = 50, message = "Mã từ 1 đến 50 ký tự")
    public String code;
    @NotEmpty(message = "A field name is invalid!")
    public String name;
    public Boolean isActive;
    @Nullable
    public Integer parent;
}
