package simonov.hotel.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IRoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class RoomDAO extends AbstractDAO<Room,Integer> implements IRoomDAO {
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
}
