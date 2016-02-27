package simonov.hotel.dao.repository;

import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IHotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsWithPattern(String city, String hotelName,
                                     Integer stars, LocalDate fromDate,
                                     LocalDate toDate, Integer numOfTravelers);

    List<Hotel> getHotelsByUser(Integer userId);
    Map<Hotel,List<Room>> getHotelsWithFreeRoom(String city, String hotelName,
                                                Integer stars, Date fromDate,
                                                Date toDate, Integer numOfTravelers);
}
