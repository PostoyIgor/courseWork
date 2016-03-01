package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HotelService {

    @Autowired
    IHotelDAO hotelDAO;

    public List<Hotel> getHotelList() {
        List<Hotel> hotelList = hotelDAO.getAll();
        return hotelList.stream().filter(hotel -> hotel.getRooms().size()>0).collect(Collectors.toList());
    }

    public List<Hotel> getHotelsWithFreeRoom(String city, String hotelName,
                                             Integer stars, LocalDate startDate,
                                             LocalDate endDate, Integer numOfTravelers) {
        return hotelDAO.getHotelsWithFreeRoom(city, hotelName, stars, startDate, endDate, numOfTravelers);
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.get(id);
    }

    public void saveHotel(Hotel hotel) {
        hotelDAO.save(hotel);
    }

    public List<Hotel> getHotelsByUser(int userId){
       return hotelDAO.getHotelsByUser(userId);
    }
}
