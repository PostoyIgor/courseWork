package simonov.hotel.dao.repository;

import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface IHotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsWithPattern(String city, String hotelName,
                                     Integer stars, LocalDate fromDate,
                                     LocalDate toDate, Integer numOfTravelers);

    List<Hotel> getHotelsByUser(Integer userId);
}
