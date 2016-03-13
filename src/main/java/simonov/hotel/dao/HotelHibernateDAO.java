package simonov.hotel.dao;

import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;
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
    public List<Hotel> getHotelsWithFreeRoom(Request request) {
        int index = 1;
        Query query = getCurrentSession().createQuery(request.getQuery());
        query.setParameter("startDate", request.getStartDate());
        query.setParameter("endDate", request.getEndDate());
        if (request.getHotelId() != 0) {
            query.setInteger("hotelId", request.getHotelId());
        } else if (request.getCityId() != 0) {
            query.setInteger("cityId", request.getCityId());
        } else if (request.getCountryId() != 0) {
            query.setInteger("countryId", request.getCountryId());
        }
        if (request.getStars() != 0){
            query.setInteger("stars", request.getStars());
        }
        if (request.getFirstResult() != 0){
            query.setFirstResult(request.getFirstResult());
        }
        if (request.getLimit() != 0){
            query.setMaxResults(request.getLimit());
        }

        if (request.getSeats()!=null){
            for (Map.Entry entry : request.getSeats().entrySet()) {
                query.setParameter("seats" + index, entry.getKey());
                query.setParameter("value" + index, ((Integer) entry.getValue()).longValue());
                index++;
            }

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
}

