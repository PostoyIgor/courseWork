package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Request;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HotelDAO extends GenericDAO<Hotel, Integer> {

    List<Hotel> getHotelsByUser(Integer userId);

    List<Hotel> getHotelsWithFreeRoom(Request request);

    List<Hotel> getHotelsByCity(int city);

    List<Hotel> getHotelsByCountry(int country);
}
