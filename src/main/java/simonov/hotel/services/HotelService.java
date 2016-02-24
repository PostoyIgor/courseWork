package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.HotelDAO;
import simonov.hotel.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelDAO hotelDAO;

    public List<Hotel> getHotelList() {
        return hotelDAO.getHotels();
    }

    public List<Hotel> getHotelsWithPattern(String city, String hotelName, Integer stars, LocalDate fromDate, LocalDate toDate, Integer numOfTravelers) {

        return hotelDAO.getHotelsWithPattern(city,hotelName,stars,fromDate,toDate,numOfTravelers);
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.getHotelById(id);
    }

    public void saveHotel(Hotel hotel) {
        hotelDAO.saveHotel(hotel);
    }

    public List<Hotel> getUserHotels(int userId){
       return hotelDAO.getUserHotels(userId);
    }
}
