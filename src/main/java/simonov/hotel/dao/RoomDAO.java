package simonov.hotel.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class RoomDAO {
@Autowired
SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveRoom(Room room){
        getCurrentSession().save(room);
    }

    public List<Room> getRooms(){
        return getCurrentSession().createCriteria(Room.class).list();
    }

    public boolean isFree(LocalDate start, LocalDate end, int roomId){
//         getCurrentSession().createCriteria(Booking.class).add(Restrictions.eq("room_id",1)).list();
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId");
        query.setInteger("roomId",roomId);
        List<Booking> bookings = query.list();

        //TODO !!!
        for(Booking booking: bookings){
            if(booking.getEndDate().isBefore(start)||booking.getStartDate().isAfter(end))
                continue;
            return false;
        }
        return true;
    }

    public Room getRoomById(int id) {
       return (Room) getCurrentSession().get(Room.class,id);
    }
}
