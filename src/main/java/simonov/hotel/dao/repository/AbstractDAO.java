package simonov.hotel.dao.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public abstract class AbstractDAO<T,PK extends Serializable> implements GenericDAO<T,PK> {
    @Autowired
    SessionFactory sessionFactory;

    Class<T> type;

    public AbstractDAO() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T newInstance) {
         getCurrentSession().save(newInstance);
    }

    @Override
    public T get(PK id) {
        return (T) getCurrentSession().get(type,id);
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

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
