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
    public boolean isFree(LocalDate startDate, LocalDate endDate, int roomId) {
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId" +
                " and (endDate>=:startDate and startDate<=:endDate)");
        query.setInteger("roomId", roomId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        List<Booking> bookings = query.list();
        return bookings.size() == 0;
    }

    @Override
    public boolean setLock(int id) {
        String stringQuery = "update Room r set r.locked = true where r.id = :id";
        Query query = getCurrentSession().createQuery(stringQuery);
        int update = query.setInteger("id", id).executeUpdate();
        return update != 0;
    }

    @Override
    public void unlock(int id) {
        String query = "update Room r set r.locked = false where r.id = :id";
        getCurrentSession().createQuery(query).setInteger("id", id).executeUpdate();
    }

    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Room.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        return criteria.list();
    }

    @Override
    public List<Room> getRoomsByType(int hotelId, String type) {
        Query query = getCurrentSession().createQuery("from Room as room where room.hotel.id = :hotelId and room.type = :type");
        query.setInteger("hotelId", hotelId);
        query.setString("type", type);
        return query.list();
    }
}
