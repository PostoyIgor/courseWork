package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class RoomHibernateDAO extends AbstractDAO<Room,Integer> implements RoomDAO {

    public RoomHibernateDAO() {
        super(Room.class);
    }

    @Override
    public boolean isFree(LocalDate start, LocalDate end, int roomId){
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId");
        query.setInteger("roomId",roomId);
        List<Booking> bookings = query.list();
        for(Booking booking: bookings){
            if(booking.getEndDate().isBefore(start)||booking.getStartDate().isAfter(end))
                continue;
            return false;
        }
        return true;
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("hotel.id",hotelId));
        return criteria.list();
    }
}
