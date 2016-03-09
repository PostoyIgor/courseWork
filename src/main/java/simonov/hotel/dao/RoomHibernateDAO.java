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
public class RoomHibernateDAO extends AbstractDAO<Room, Integer> implements RoomDAO {

    public RoomHibernateDAO() {
        super(Room.class);
    }

    @Override
    public boolean isFree(LocalDate start, LocalDate end, int roomId) {
        Query query1 = getCurrentSession().createQuery("from Room as r where r.id = :roomId and " +
                " not exists (from Booking as b where b.room.id=:roomId" +
                " and (startDate<=:endDate and endDate>=:startDate))");
        query1.setParameter("roomId", roomId);
        query1.setParameter("startDate", start);
        query1.setParameter("endDate", end);
        Room room = (Room) query1.uniqueResult();
        return room != null;
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        return criteria.list();
    }
}
