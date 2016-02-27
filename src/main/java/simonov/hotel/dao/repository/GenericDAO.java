package simonov.hotel.dao.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    void save(T newInstance);
    T get(PK id);
    List<T> getAll();
    void update(T o);
    void delete(T o);
}
