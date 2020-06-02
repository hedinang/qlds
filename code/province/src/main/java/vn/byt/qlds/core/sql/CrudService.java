package vn.byt.qlds.core.sql;

import com.google.gson.Gson;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import vn.byt.qlds.configuration.ConnectorManager;
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.model.response.PageResponse;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class CrudService<T, ID extends Serializable> {
    public Class<T> entityClass;
    @Autowired
    ConnectorManager connectorManager;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public CrudService() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public Session getSession(String idSession) {
        return connectorManager.openSessionById(idSession);
    }

    public T create(String idSession, T entity) {
        Session session = getSession(idSession);
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Object key = session.save(entity);
            transaction.commit();
            session.clear();
            T t = session.find(entityClass, key);
            putToRabit(idSession, entity);
            session.close();
            return t;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return null;
        }
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

    public PageResponse<T> getPage(String idSession, int page, int limit, String direction, String property) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
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

    public T update(String idSession, T entity) {
        Session session = getSession(idSession);
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(entity);
            transaction.commit();
            putToRabit(idSession, entity);
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return null;
        }
    }

    protected void putToRabit(String idSession, T entity) {
        try {
            if (entity != null) {
                ESMessageSync<T> esMessageSync = new ESMessageSync<>();
                esMessageSync.setDbName(idSession);
                esMessageSync.data = entity;
                rabbitTemplate.convertAndSend("QLDS", entityClass.getSimpleName(), new Gson().toJson(esMessageSync));
            }
        } catch (Exception e) {

        }
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

    public List<T> getAll(String idSession) {
        Session session = getSession(idSession);
        try {
            Criteria criteria = session.createCriteria(entityClass);
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