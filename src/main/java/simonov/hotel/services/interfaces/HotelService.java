package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public interface HotelService {

    List<Hotel> getHotelList();

    Hotel getHotelById(int id);

    void saveHotel(Hotel hotel);

    List<Hotel> getHotelsByUser(int userId);

    Long getHotelCount();

    List<Hotel> getHotelsByCountry(int countryId);

    List<Hotel> getHotelsByCity(int city);

    List<Hotel> getHotelsWithFreeRoom(int countryId, int cityId, int hotelId,
                                      LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats);

    List<Hotel> sortByStars(List<Hotel> hotels);

    List<Hotel> sortByRating(List<Hotel> hotels);
}
