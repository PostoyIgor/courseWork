package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.User;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class BookingDAO {

    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Booking getBooking(){
        return (Booking) getCurrentSession().createCriteria(Booking.class).list().get(0);
    }

    public void saveBooking(Booking booking){
        getCurrentSession().save(booking);
    }

    public List<Booking> getBookings(){
       return getCurrentSession().createCriteria(Booking.class).list();
    }

    public List<Booking> getBookingsByUser(int userId){
        Query query = getCurrentSession().createQuery("from Booking where user.id = :userId");
        query.setInteger("userId",userId);
        return query.list();
    }

    public List<Booking> getBookingByRoom(int roomId){
        Query query = getCurrentSession().createQuery("from Booking where room.id = :roomId");
        query.setInteger("roomId",roomId);
        return query.list();
    }

    public void deleteBooking(Booking booking){
        getCurrentSession().delete(booking);
    }
}
