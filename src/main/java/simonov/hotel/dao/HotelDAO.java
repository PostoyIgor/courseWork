package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class HotelDAO extends AbstractDAO<Hotel,Integer> implements IHotelDAO {

    @Override
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

    @Override
    public List<Hotel> getHotelsByUser(Integer userId) {
        Query query = getCurrentSession().createQuery("from Hotel where user.id = :userId");
        query.setInteger("userId", userId);
        return query.list();
    }
}
