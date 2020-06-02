package vn.byt.qlds.config.db;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import vn.byt.qlds.config.ConnectorManager;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
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
            session.clear();
            T t = session.find(entityClass, key);
            session.close();
            return t;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            return null;
        }
    }

    public void createBulk(String idSession, List<T> data) {
        Session session = connectorManager.openSessionById(idSession);
        Transaction tx = null;
        Integer ID = null;

        try {
            tx = session.beginTransaction();
            int counter = data.size();
            for (int i = 0; i < counter; i++) {
                T item = data.get(i);
                session.save(item);
                if (i % 100 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return;
    }

    public List<T> getPage(String idSession, int page, int limit) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        int realPage = page - 1;
        criteria.setFirstResult(realPage * limit);
        criteria.setMaxResults(limit);
        return criteria.list();
    }


    public List<T> getAll(String idSession) {
        Session session = getSession(idSession);
        try {
            Criteria criteria = session.createCriteria(entityClass);
            List<T> list = (List<T>) criteria.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            return null;
        }
    }

    public Long count(String idSession) {
        Session session = getSession(idSession);
        try {
            session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            criteria.setProjection(Projections.rowCount());

            List list = criteria.list();
            if (list != null) {
                Long rowCount = (Long) list.get(0);
                System.out.println("Total Results:" + rowCount);
                return rowCount;
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return 0L;
        }
        return 0L;
    }
}