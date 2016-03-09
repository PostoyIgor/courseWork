package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsWithFreeRoom(String country, String city, String hotelName,
                                      LocalDate starDate, LocalDate endDate, Integer numOfTravelers);


    List<Hotel> getHotelsByCriteria(Integer countryId, Integer cityId, String hotelName);

    List<Hotel> getHotelsByDate(String country, String city, String hotelName, LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats, int firstResult, int maxResult);
}
