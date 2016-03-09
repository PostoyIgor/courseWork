package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsWithFreeRoom(int country, int city, int hotelName,
                                      LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats);

    List<Hotel> getHotelsByCity(int city);

    List<Hotel> getHotelsByCountry(int country);
}
