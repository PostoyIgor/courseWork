package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import simonov.hotel.dao.interfaces.ConvenienceDAO;
import simonov.hotel.entity.Convenience;
import simonov.hotel.services.interfaces.ConvenienceService;

import java.util.List;

public class ConvenienceServiceImpl implements ConvenienceService {

    @Autowired
    ConvenienceDAO convenienceDAO;

    @Override
    public List<Convenience> getConveniencesByHotel(Integer hotelId) {
        return convenienceDAO.getConveniencesByHotel(hotelId);
    }
}
