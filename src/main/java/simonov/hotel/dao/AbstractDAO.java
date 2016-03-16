package simonov.hotel.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.GenericDAO;

import java.io.Serializable;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public abstract class AbstractDAO<T, PK extends Serializable> implements GenericDAO<T, PK> {
    @Autowired
    SessionFactory sessionFactory;

    Class<T> type;

    public AbstractDAO(Class<T> type) {
        this.type = type;
    }

    @Override
    public PK save(T newInstance) {
        return (PK) getCurrentSession().save(newInstance);
    }

    @Override
    public T get(PK id) {
        return (T) getCurrentSession().get(type, id);
    }

    @Override
    public List<T> getAll() {
        return getCurrentSession().createCriteria(type).list();
    }

    @Override
    public void update(T o) {
        getCurrentSession().update(o);
    }

    @Override
    public void delete(T o) {
        getCurrentSession().delete(o);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
