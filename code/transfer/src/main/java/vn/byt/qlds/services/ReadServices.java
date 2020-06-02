package vn.byt.qlds.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import vn.byt.qlds.config.ConnectorManager;
import vn.byt.qlds.config.db.PageResponse;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class ReadServices<T, ID extends Serializable> {
    public Class<T> entityClass;
    @Autowired
    ConnectorManager connectorManager;

    public ReadServices() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public Session getSession(String idSession) {
        return connectorManager.openSessionById(idSession);
    }


    public T read(String idSession, ID id) {
        Session session = getSession(idSession);

        try {
            Criteria criteria = session.createCriteria(entityClass);
            Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
            Criterion criterionId = Restrictions.eq("id", id);
            LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
            criteria.add(andExp);
            T t = (T) criteria.uniqueResult();
            session.close();
            return t;
        } catch (Exception e) {
            session.close();
            return null;
        }
    }

    public PageResponse<T> getPage(String idSession, Map<String, Object> query) {
        Integer limit = 10;
        Integer page = 1;
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        String direction = "";
        String property = "";
        if (query != null)
            for (String key : query.keySet()) {
                if (!key.equals("limit") && !key.equals("page") && !key.equals("es_key_like") && !key.equals("es_value_like"))
                    criteria.add((Restrictions.eq(key, query.get(key))));
                if (key.equals("limit"))
                    limit = (Integer) query.get(key);
                if (key.equals("page"))
                    page = (Integer) query.get(key);
                if (key.equals("es_key_like"))
                    criteria.add((Restrictions.ilike(key, query.get(key))));
                if (key.equals("key_sort")) direction = (String) query.get(key);
                if (key.equals("key_sort_order")) property = (String) query.get(key);
            }
        int realPage = page - 1;
        criteria.add((Restrictions.eq("isDeleted", false)));
        int total = criteria.list().size();
        criteria.setFirstResult(realPage * limit);
        criteria.setMaxResults(limit);

        switch (direction) {
            case "ASC": {
                criteria.addOrder(Order.asc(property));
                break;
            }
            case "DESC": {
                criteria.addOrder(Order.desc(property));
                break;
            }
            default: {

            }
        }
        PageResponse<T> pageResponse = new PageResponse<T>(criteria.list(), page, total);
        session.close();
        return pageResponse;
    }

    public boolean update(String idSession, Map<String, Object> where, Map<String, Object> value) {
        Session session = getSession(idSession);
        Transaction transaction = session.getTransaction();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(entityClass);
        Root<T> root = criteria.from(entityClass);

        for (String key : value.keySet()) {
            criteria.set(root.get(key), value.get(key));
        }
        Expression<Boolean> filterPredicate = null;

        for (String key : where.keySet()) {
            if (filterPredicate == null) {
                filterPredicate = builder.equal(root.get(key), where.get(key));
            } else {
                filterPredicate = builder.and(filterPredicate, builder.equal(root.get(key), where.get(key)));
            }
        }

        criteria.where(filterPredicate);
        try {
            transaction.begin();
            session.createQuery(criteria).executeUpdate();
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return false;
        }
    }

    public boolean delete(String idSession, ID id) {
        Session session = getSession(idSession);
        Transaction transaction = session.getTransaction();
        T entity = session.get(entityClass, id);
        if (entity == null) {
            session.close();
            return false;
        } else {
            transaction.begin();
            session.delete(entity);
            transaction.commit();
            session.close();
            return true;
        }
    }

    public List<T> getAll(Map<String, Object> query) throws IOException {
        Session session = getSession("common");
        try {
            Criteria criteria = session.createCriteria(entityClass);
            if (query != null)
                for (String key : query.keySet()) {
                    if (!key.equals("es_key_like") && !key.equals("es_value_like"))
                        criteria.add((Restrictions.eq(key, query.get(key))));
                    if (key.equals("es_key_like"))
                        criteria.add((Restrictions.ilike(key, query.get(key))));
                }
            criteria.add((Restrictions.eq("isDeleted", false)));
            List<T> list = (List<T>) criteria.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            return null;
        }
    }

}
