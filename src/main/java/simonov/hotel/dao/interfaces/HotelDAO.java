package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsWithFreeRoom(String country, String city, String hotelName,
                                      LocalDate starDate,LocalDate endDate, Integer numOfTravelers);
}
