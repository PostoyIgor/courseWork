package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.IHotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Map<Hotel,List<Room>> getHotels(String city, String hotelName,
                                           Integer stars, Date fromDate,
                                           Date toDate, Integer numOfTravelers) {

        if (hotelName.length()==0){ hotelName="";}
        if (city.length()==0){ city="";}
        return hotelDAO.getHotelsWithFreeRoom(city,hotelName,stars,fromDate,toDate,numOfTravelers);
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
