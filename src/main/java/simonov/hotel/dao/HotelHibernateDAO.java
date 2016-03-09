package simonov.hotel.dao;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public List<Hotel> getHotelsWithFreeRoom(int countryId, int cityId, int hotelId,
                                             LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats) {
        int index = 1;
        Query query = getCurrentSession().createQuery(query(countryId, cityId, hotelId, seats));
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        if (countryId != 0) {
            query.setParameter("country", countryId);
        } else if (cityId != 0) {
            query.setParameter("city", cityId);
        } else if (hotelId != 0) {
            query.setParameter("hotelName", hotelId);
        }

        for (Map.Entry entry : seats.entrySet()) {
            query.setParameter("seats" + index, entry.getKey());
            query.setParameter("value" + index, ((Integer) entry.getValue()).longValue());
            index++;
        }

        List<Hotel> h = query.list();

        return h;
    }
    @Override
    public List<Hotel> getHotelsByCity(int city) {
        Query query = getCurrentSession().createQuery("select h from Hotel as h where h.city.id = :city");
        query.setParameter("city", city);
        return query.list();
    }

    @Override
    public List<Hotel> getHotelsByCountry(int country) {
        Query query = getCurrentSession().createQuery("select h from Hotel as h left join City as city on h.city.country.id = :country");
        query.setParameter("country", country);
        return query.list();
    }

    private String query(int country, int city, int hotelName, Map<Integer, Integer> seats) {

        StringBuilder query = new StringBuilder("select distinct h from Hotel as h left join fetch h.rooms as room where");
        StringBuilder subquery = new StringBuilder(" (");
        int index = 1;
        int mapSize = seats.size();

        if (city != 0) {
            query.append(" h.city.id = :city and");
        } else if (hotelName != 0) {
            query.append(" h.name = :hotelName and");
        } else if (country != 0) {
            query.append(" h.city in (select c.id from City as c where c.country.id = :country) and ");
        }
        for (Map.Entry entry : seats.entrySet()) {
            query.append(" (select count(r.id) from Room as r where h.id = r.hotel.id and r.seats = :seats" + index + " and not exists " +
                    "(select distinct b.room.id from Booking as b where r.id = b.room.id and (endDate>=:startDate and startDate<=:endDate)))>=:value" + index + " and ");
            if (index == mapSize) {
                subquery.append("room.seats = :seats" + index + ") ");
            } else {
                subquery.append("room.seats = :seats" + index + " or ");
            }
            index++;
        }
        query.append(" not exists (select distinct b.room.id from Booking as b where room.id = b.room.id and (endDate>=:startDate and startDate<=:endDate)) and ");
        query.append(subquery);
        return query.toString();
    }


}

