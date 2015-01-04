package vitaliy.kuzmich.dao;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T, PK extends Serializable> {
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    @Autowired
    private SessionFactory sessionFactory;

    public GenericDao(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public Session getSession() throws HibernateException {
        Session sess = sessionFactory.getCurrentSession();
        if (sess == null) {
            sess = sessionFactory.openSession();
        }
        return sess;
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return getSession().createCriteria(persistentClass).list();
    }


    @SuppressWarnings("unchecked")
    public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
    }

    @SuppressWarnings("unchecked")
    public T save(T object) {
        Session sess = getSession();
        return (T) sess.merge(object);
    }

    public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);
    }

    public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);
    }

}
