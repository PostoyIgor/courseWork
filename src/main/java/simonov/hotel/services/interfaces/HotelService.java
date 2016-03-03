package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

@Service
public interface HotelService {

    List<Hotel> getHotelList();

    List<Hotel> getHotelsWithFreeRoom(String country, String city, String hotelName,
                                      LocalDate startDate, LocalDate endDate, Integer numOfTravelers);

    Hotel getHotelById(int id);

    void saveHotel(Hotel hotel);

    List<Hotel> getHotelsByUser(int userId);

    List<Hotel> getHotelListByPage(int page, int pageSize);
}
