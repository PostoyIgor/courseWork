package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.GenericDAO;

import java.io.Serializable;
import java.util.List;

@Service
public abstract class GenericService<T, PK extends Serializable> {
    @Autowired
    private GenericDAO<T, PK> genericDao;

    public T get(PK id) {
        return this.getGenericDao().get(id);
    }

    public List<T> getAll() {
        return this.getGenericDao().getAll();
    }

    public void save(T newInstance) {
         this.getGenericDao().save(newInstance);
    }

    public void updpate(T updateInstance) {
          this.getGenericDao().update(updateInstance);
    }

    public void delete(T entity) {
        this.getGenericDao().delete(entity);
    }

    public GenericDAO<T, PK> getGenericDao() {
        return genericDao;
    }

    public void setGenericDao(GenericDAO<T, PK> genericDao) {
        this.genericDao = genericDao;
    }


}