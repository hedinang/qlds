package vn.byt.qlds.ministry.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.model.Account;
import vn.byt.qlds.ministry.model.AccountRole;
import vn.byt.qlds.ministry.model.RolePermission;
import vn.byt.qlds.ministry.model.response.AccountResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountService extends CrudService<Account, Integer> {

    public Account getAccountByUsername(String idSession, String username) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(Account.class);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionId = Restrictions.eq("userName", username);
        LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
        criteria.add(andExp);
        Account account = (Account) criteria.uniqueResult();
        return account;
    }
}
