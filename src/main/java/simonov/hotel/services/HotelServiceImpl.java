package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.HotelDAO;
import simonov.hotel.entity.Hotel;
import simonov.hotel.services.interfaces.HotelService;

import java.time.LocalDate;
import java.util.List;
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
    public List<Hotel> getHotelsWithFreeRoom(String country, String city, String hotelName,
                                             LocalDate startDate, LocalDate endDate, Integer numOfTravelers) {
        return hotelDAO.getHotelsWithFreeRoom(country,country, hotelName, startDate, endDate, numOfTravelers);
    }
    @Override
    public Hotel getHotelById(int id) {
        return hotelDAO.get(id);
    }
    @Override
    public void saveHotel(Hotel hotel) {
        hotelDAO.save(hotel);
    }
    @Override
    public List<Hotel> getHotelsByUser(int userId) {
        return hotelDAO.getHotelsByUser(userId);
    }

    @Override
    public List<Hotel> getHotelListByPage(int page, int pageSize) {
        return hotelDAO.getListByPage(page * pageSize - pageSize,pageSize);
    }

    public Long getCount(){
        return hotelDAO.getTotalCount();
    }
}
