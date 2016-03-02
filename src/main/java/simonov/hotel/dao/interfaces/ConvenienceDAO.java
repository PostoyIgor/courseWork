package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Convenience;

import java.util.List;

public interface ConvenienceDAO extends GenericDAO<Convenience, Integer> {
    List<Convenience> getConveniencesByHotel(Integer hotelId);

}
