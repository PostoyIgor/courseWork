package simonov.hotel.dao.interfaces;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, PK extends Serializable> {
    PK save(T newInstance);

    T get(PK id);

    List<T> getAll();

    void update(T o);

    void delete(T o);
}
