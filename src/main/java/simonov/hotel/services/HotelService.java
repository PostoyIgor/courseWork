package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    HotelDAO hotelDAO;

    public List<Hotel> getHotelList() {
        return hotelDAO.getHotels();
    }

    public List<Hotel> getHotelsWithTempl(String city, String hotelName, Integer stars, Date fromDate, Date toDate, Integer numOfTravelers) {

        return hotelDAO.getHotelsWithTempl(city,hotelName,stars,fromDate,toDate,numOfTravelers);
    }

    public Hotel getHotelById(int id) {
        return hotelDAO.getHotelById(id);
    }

    public void saveHotel(Hotel hotel) {
        hotelDAO.saveHotel(hotel);
    }
}
