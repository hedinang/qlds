package vn.byt.qlds.sync.core.sql;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import vn.byt.qlds.sync.configuration.ConnectorManager;
import vn.byt.qlds.sync.model.response.PageResponse;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class CrudService<T, ID extends Serializable> {
    public Class<T> entityClass;
    @Autowired
    ConnectorManager connectorManager;

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
            T t = session.find(entityClass, key);
            session.close();
            return t;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return null;
        }
    }

    public T read(String idSession, ID id) {
        if (id == null) return null;
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

    public PageResponse<T> getPage(String idSession, int page, int limit) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        int realPage = page - 1;
        criteria.add((Restrictions.eq("isDeleted", false)));
        criteria.setFirstResult(realPage * limit);
        criteria.setMaxResults(limit);
        List<T> data = criteria.list();
        int total = data.size();
        PageResponse<T> pageResponse = new PageResponse<T>(data, page, total);
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
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return null;
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
            List<T> data = criteria.list();
            session.close();
            return data;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }

    public Long count(String idSession){
        Session session = getSession(idSession);
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            criteria.add((Restrictions.eq("isDeleted", false)));
            criteria.setProjection(Projections.rowCount());

            List list = criteria.list();
            if (list!=null) {
                Long rowCount =  (Long)list.get(0);
                System.out.println("Total Results:" + rowCount);
                return rowCount;
            }
            session.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return 0L;
        }
        return 0L;
    }
}