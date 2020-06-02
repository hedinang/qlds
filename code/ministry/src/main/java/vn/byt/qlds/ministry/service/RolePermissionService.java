package vn.byt.qlds.ministry.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.model.RolePermission;

import java.util.List;

@Component
public class RolePermissionService extends CrudService<RolePermission, Integer> {
    public List<RolePermission> getPermisionByRoleId(String idSession, int roleId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(RolePermission.class);
        criteria.add((Restrictions.eq("roleId", roleId)));
        criteria.add((Restrictions.eq("isDeleted", false)));
        List<RolePermission> rolePermissions = criteria.list();
        session.close();
        return rolePermissions;
    }
}
