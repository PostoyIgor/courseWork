package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.*;

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

    public Map<Hotel,List<Room>> getHotelsWithFreeRoom(String city, String hotelName,
                                                       Integer stars, Date fromDate,
                                                       Date toDate, Integer numOfTravelers){

//        StringBuilder hotelQuery = new StringBuilder();
//        hotelQuery.append("select id FROM Hotel ");

        Query queryFreeRoom = getCurrentSession().createQuery("from Room where id NOT IN " +
                "(select room.id from Booking where startDate<=:toDate and endDate>=:fromDate)" +
                " and seats=:numOfTravelers and hotel.id in " +
                "(select id FROM Hotel WHERE city LIKE :city and name LIKE :hotelName)") ;
        queryFreeRoom.setDate("toDate", toDate);
        queryFreeRoom.setDate("fromDate", fromDate);
        queryFreeRoom.setInteger("numOfTravelers", numOfTravelers);
        System.out.println("CITY: "+city);
        System.out.println("HOTEL: "+hotelName);
        queryFreeRoom.setString("city",city+"%");
        queryFreeRoom.setString("hotelName",hotelName+"%");
        List<Room> rooms = queryFreeRoom.list();
        Map<Hotel,List<Room>> hotelListMap = new HashMap<>();
        rooms.stream().forEach(room-> {
            Hotel hotel = room.getHotel();
            if (hotelListMap.get(hotel)==null){
                List<Room> freeRoom = new ArrayList<>();
                freeRoom.add(room);
                hotelListMap.put(hotel,freeRoom);
            } else {
                hotelListMap.get(hotel).add(room);
            }
        });
        return hotelListMap;
    }
}
