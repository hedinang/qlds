package vn.byt.qlds.ministry.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.model.AccountRole;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRoleService extends CrudService<AccountRole, Integer> {
    public List<AccountRole> getRoleByAccountID(String idSession, int accountId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(AccountRole.class);
        criteria.add((Restrictions.eq("userId", accountId)));
        criteria.add((Restrictions.eq("isDeleted", false)));
        List<AccountRole> accountRoles = (List<AccountRole>) criteria.list();
        session.close();
        return accountRoles;
    }

    public List<AccountRole> searchListAccountRole(String idSession, AccountRole accountRole) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        List<Criterion> listCriterion = new ArrayList<>();
        criteria.add(Restrictions.eq("isDeleted", false));
        if (accountRole.getRoleId() != null) {
            criteria.add(Restrictions.eq("roleId", accountRole.getRoleId()));
        }
        if (accountRole.getUserId() != null) {
            criteria.add(Restrictions.eq("userId", accountRole.getUserId()));
        }
        if (accountRole.getId() != null) {
            criteria.add(Restrictions.eq("id", accountRole.getUserId()));
        }
        List<AccountRole> list = criteria.list();
        session.close();
        return list;
    }

    public void deleteAccountRole(String idSession, int userId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        criteria.add(Restrictions.eq("userId", userId));
        List<AccountRole> accountRoles = (List<AccountRole>) criteria.list();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (AccountRole accountRole : accountRoles) {
            session.delete(accountRole);
        }
        transaction.commit();

        session.close();

    }
}
