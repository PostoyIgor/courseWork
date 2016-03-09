package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.HotelHibernateDAO;

import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Role;
import simonov.hotel.services.interfaces.HotelService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    @Autowired
    HotelDAO hotelDAO;

    @Override
    public List<Hotel> getHotelList() {
        List<Hotel> hotelList = hotelDAO.getAll();
        return hotelList.stream().filter(hotel -> hotel.getRooms().size() > 0).collect(Collectors.toList());
    }

    @Override
    public Hotel getHotelById(int id) {
        return hotelDAO.get(id);
    }

    @Override
    public void saveHotel(Hotel hotel) {
        if (hotel.getUser().getRole() == Role.HotelOwner) {
            hotelDAO.save(hotel);
        }
    }

    @Override
    public List<Hotel> getHotelsByUser(int userId) {
        return hotelDAO.getHotelsByUser(userId);
    }

    @Override
    public Long getHotelCount() {
        return null;
    }

    @Override
    public List<Hotel> getHotelsByCountry(int countryId) {
        return hotelDAO.getHotelsByCountry(countryId);
    }

    @Override
    public List<Hotel> getHotelsByCity(int city) {
        return hotelDAO.getHotelsByCity(city);
    }

    @Override
    public List<Hotel> getHotelsWithFreeRoom(int countryId, int cityId, int hotelId,
                                             LocalDate startDate, LocalDate endDate, Map<Integer, Integer> seats) {
        if (countryId == 0 && cityId == 0 && hotelId == 0) {
            return null;
        } else if (hotelId != 0) {
            return hotelDAO.getHotelsWithFreeRoom(0, 0, hotelId, startDate, endDate, seats);
        } else if (cityId != 0) {
            return hotelDAO.getHotelsWithFreeRoom(0, cityId, 0, startDate, endDate, seats);
        } else {
            return hotelDAO.getHotelsWithFreeRoom(countryId, 0, 0, startDate, endDate, seats);
        }
    }

    @Override
    public List<Hotel> sortByStars(List<Hotel> hotels) {
        hotels.sort((Hotel o1, Hotel o2) -> o1.getStars() - o2.getStars());
        return hotels;
    }

    @Override
    public List<Hotel> sortByRating(List<Hotel> hotels) {
        hotels.sort((Hotel o1, Hotel o2) -> (o1.getRating().compareTo(o2.getRating())));
        return hotels;
    }

}
