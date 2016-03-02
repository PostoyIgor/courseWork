package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Country;

public interface CountryDAO extends GenericDAO<Country, Integer>{
    Country getCountryByName(String countryName);
}
