package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsWithFreeRoom(String city, String hotelName,
                                      Integer stars, LocalDate starDate,
                                      LocalDate endDate, Integer numOfTravelers);

}
