package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class HotelDAO {
    @Autowired
    SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveHotel(Hotel hotel) {
        getCurrentSession().save(hotel);
    }

    public List<Hotel> getHotels() {
        return getCurrentSession().createCriteria(Hotel.class).list();
    }

    public Hotel getHotelById(int id) {
        return (Hotel) getCurrentSession().get(Hotel.class, id);
    }

    public List<Hotel> getHotelsWithPattern(String city, String hotelName, Integer stars, LocalDate fromDate, LocalDate toDate, Integer numOfTravelers) {
        Criteria criteria = getCurrentSession().createCriteria(Hotel.class);
        if (city.length() != 0) {
            criteria.add(Restrictions.like("city", city + "%"));
        }
        if (hotelName.length() != 0) {
            criteria.add(Restrictions.like("name", hotelName + "%"));
        }
        if (stars != null) {
            criteria.add(Restrictions.eq("stars", stars));
        }
        return criteria.list();
    }

    public List<Hotel> getUserHotels(int userId) {
        Query query = getCurrentSession().createQuery("from Hotel where user.id = :userId");
        query.setInteger("userId", userId);
        return query.list();
    }
}
