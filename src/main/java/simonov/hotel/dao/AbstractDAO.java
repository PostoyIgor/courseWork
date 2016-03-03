package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.GenericDAO;

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

    public AbstractDAO(Class<T> type){
        this.type = type;
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
    public List<T> getListByPage(int firstResult, int maxResult) {
        Criteria criteria = getCurrentSession().createCriteria(type);
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResult);
        return criteria.list();
    }
    @Override
    public Long getTotalCount(){
        Criteria criteriaCount = getCurrentSession().createCriteria(type);
        criteriaCount.setProjection(Projections.rowCount());
        return (Long) criteriaCount.uniqueResult();
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
