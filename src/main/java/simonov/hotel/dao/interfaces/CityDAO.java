package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.City;

public interface CityDAO extends GenericDAO<City, Integer> {

    City getCityByName(String cityName);
}
