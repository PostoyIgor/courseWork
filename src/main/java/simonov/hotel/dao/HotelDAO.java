package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@SuppressWarnings("unchecked")
public class HotelDAO extends AbstractDAO<Hotel, Integer> implements IHotelDAO {

    @Override
    public List<Hotel> getHotelsByUser(Integer userId) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);
        hotelCriteria.createAlias("user","user");
        hotelCriteria.add(Restrictions.eq("user.id",userId));
        return hotelCriteria.list();
    }

    public List<Hotel> getHotelsWithFreeRoom(String city, String hotelName,
                                             Integer stars, LocalDate startDate,
                                             LocalDate endDate, Integer numOfTravelers) {
        Session session = getCurrentSession();
        session.enableFilter("RoomFilter")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("seats", numOfTravelers);
        Criteria hotelCriteria = session.createCriteria(Hotel.class);
        if (city.length() != 0) {
            hotelCriteria.add(Restrictions.like("city", city + "%"));
        }
        if (hotelName.length() != 0) {
            hotelCriteria.add(Restrictions.like("name", hotelName + "%"));
        }
        if (stars != null) {
            hotelCriteria.add(Restrictions.eq("stars", stars));
        }

        List<Hotel> hotelList = hotelCriteria.list();

        return hotelList.stream().filter(hotel -> hotel.getRooms().size() > 0).collect(Collectors.toList());
    }
}
