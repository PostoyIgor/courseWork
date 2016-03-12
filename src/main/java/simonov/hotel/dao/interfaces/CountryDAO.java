package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Country;

import java.util.List;

public interface CountryDAO extends GenericDAO<Country, Integer> {
    Country getCountryByName(String countryName);

    List getCountriesByNameCriteria(String nameCriteria);

}
