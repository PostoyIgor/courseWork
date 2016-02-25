package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HotelService {

    @Autowired
    IHotelDAO hotelDAO;

    public List<Hotel> getHotelList() {
        return hotelDAO.getAll();
    }

    public List<Hotel> getHotelsWithPattern(String city, String hotelName,
                                            Integer stars, LocalDate fromDate,
                                            LocalDate toDate, Integer numOfTravelers) {

        return hotelDAO.getHotelsWithPattern(city,hotelName,stars,fromDate,toDate,numOfTravelers);
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.get(id);
    }

    public void saveHotel(Hotel hotel) {
        hotelDAO.save(hotel);
    }

    public List<Hotel> getUserHotels(int userId){
       return hotelDAO.getHotelsByUser(userId);
    }
}
