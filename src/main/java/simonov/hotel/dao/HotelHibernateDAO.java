package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unchecked")
public class HotelHibernateDAO extends AbstractDAO<Hotel, Integer> implements HotelDAO {

    public HotelHibernateDAO() {
        super(Hotel.class);
    }

    @Override
    public List<Hotel> getHotelsByUser(Integer userId) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);
        hotelCriteria.createAlias("user", "user");
        hotelCriteria.add(Restrictions.eq("user.id", userId));
        return hotelCriteria.list();
    }

    public List<Hotel> getHotelsWithFreeRoom(String country, String city, String hotelName,
                                             LocalDate startDate, LocalDate endDate, Integer numOfTravelers) {
        Session session = getCurrentSession();
        session.enableFilter("RoomFilter")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("seats", numOfTravelers);
        Criteria hotelCriteria = session.createCriteria(Hotel.class);

        if (city.length() != 0) {
            hotelCriteria.createAlias("city", "city");
            hotelCriteria.add(Restrictions.eq("city.name", city));
        }

        if (country.length() != 0) {
            hotelCriteria.createAlias("city", "city");
            hotelCriteria.createAlias("city.country","country");
            hotelCriteria.add(Restrictions.eq("country.name", country));
        }
        if (hotelName.length() != 0) {
            hotelCriteria.add(Restrictions.like("name", hotelName + "%"));
        }

        List<Hotel> hotelList = hotelCriteria.list();

        return hotelList.stream().filter(hotel -> hotel.getRooms().size() > 0).collect(Collectors.toList());
    }
}
