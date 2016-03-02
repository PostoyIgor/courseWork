package simonov.hotel.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.entity.Booking;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class BookingHibernateDAO extends AbstractDAO<Booking, Integer> implements BookingDAO {

    public BookingHibernateDAO() {
        super(Booking.class);
    }

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        Query query = getCurrentSession().createQuery("from Booking where user.id = :userId");
        query.setInteger("userId", userId);
        return query.list();
    }

    @Override
    public List<Booking> getBookingByRoom(int roomId) {
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId");
        query.setInteger("roomId", roomId);
        return query.list();
    }
}
