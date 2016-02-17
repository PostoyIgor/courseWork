package simonov.hotel.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.entity.Room;

import java.util.List;

@Repository
@Transactional
public class RoomDAO {
@Autowired
SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveRoom(Room room){
        getCurrentSession().save(room);
    }

    @SuppressWarnings("unchecked")
    public List<Room> getRooms(){
        return getCurrentSession().createCriteria(Room.class).list();
    }
}
