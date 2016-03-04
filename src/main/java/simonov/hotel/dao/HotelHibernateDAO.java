package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Query;
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

    @Override
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
            hotelCriteria.createAlias("city.country", "country");
            hotelCriteria.add(Restrictions.eq("country.name", country));
        }
        if (hotelName.length() != 0) {
            hotelCriteria.add(Restrictions.like("name", hotelName + "%"));
        }

        List<Hotel> hotelList = hotelCriteria.list();

        return hotelList.stream().filter(hotel -> hotel.getRooms().size() > 0).collect(Collectors.toList());
    }

    @Override
    public List<Hotel> getHotelsByCriteria(String country, String city, String hotelName, int firstResult, int maxResult) {
        Criteria hotelCriteria = getCurrentSession().createCriteria(Hotel.class);

        if (city.length() != 0) {
            hotelCriteria.createAlias("city", "city");
            hotelCriteria.add(Restrictions.eq("city.name", city));
        }

        if (country.length() != 0) {
            hotelCriteria.createAlias("city", "city");
            hotelCriteria.createAlias("city.country", "country");
            hotelCriteria.add(Restrictions.eq("country.name", country));
        }
        if (hotelName.length() != 0) {
            hotelCriteria.add(Restrictions.like("name", hotelName + "%"));
        }
        hotelCriteria.setFirstResult(firstResult);
        hotelCriteria.setMaxResults(maxResult);
        List<Hotel> hotelList = hotelCriteria.list();
        hotelList.stream().forEach(hotel -> System.out.println(hotel.getName()));
        return hotelCriteria.list();
    }

    @Override
    public List<Hotel> getHotelsByDate(String country, String city, String hotelName,
                                       LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats,
                                       int firstResult, int maxResult) {

        Filter filter = getCurrentSession().enableFilter("RoomFilter");
        filter.setParameter("endDate", endDate);
        filter.setParameter("startDate", startDate);
        String sql = buildQuery(country, city, hotelName, seats.size());
        Query query = getCurrentSession().createQuery(sql);
        int index = 1;
        for (Map.Entry<Integer, Integer> entry : seats.entrySet()) {
            query.setParameter("seats" + index, entry.getKey());
            query.setParameter("value" + index, entry.getValue().longValue());
            index++;
        }
        if (country.length() != 0) {
            query.setParameter("country", country);
        }
        if (city.length() != 0) {
            query.setParameter("city", city);
        }
        if (hotelName.length() != 0) {
            query.setParameter("name", hotelName);
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResult);
        return query.list();
    }

    private String buildQuery(String country, String city, String hotelName, int mapSize) {
        StringBuilder query = new StringBuilder();
        StringBuilder subQuery = new StringBuilder(" and (");
        query.append("select distinct h from Hotel as h join fetch h.rooms as r where h.id=r.hotel.id ");
        if (city.length() != 0) {
            query.append("and h.city.id=(select id from City as c where c.name=:city)");
        }
        if (country.length() != 0) {
            query.append("and h.city.id in (select c.id from City as c where c.country.id=" +
                    "(select country.id from Country as country where country.name=:country))");
        }
        if (hotelName.length() != 0) {
            query.append("and h.name=:name");
        }
        for (int i = 1; i <= mapSize; i++) {
            query.append(" and (select count(*) from r where r.hotel.id=h.id and r.seats= :seats").append(i)
                    .append(")>=:value").append(i);
            if (i == mapSize) {
                subQuery.append("r.seats = :seats").append(i).append(") ");
            } else {
                subQuery.append("r.seats = :seats").append(i).append(" or ");
            }
        }
        query.append(subQuery);
        return query.toString();
    }
}
