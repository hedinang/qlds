package vn.byt.qlds.model.permission;

import java.util.List;

public class PermissionTreeResponse {
    public Integer id;
    public String code;
    public String name;
    public Boolean isActive;
    public List<PermissionCategory> permissions;
}
