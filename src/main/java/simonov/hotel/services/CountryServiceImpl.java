package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import simonov.hotel.dao.interfaces.CountryDAO;
import simonov.hotel.entity.Country;
import simonov.hotel.services.interfaces.CountryService;

import java.util.List;

public class CountryServiceImpl implements CountryService {
    @Autowired
    CountryDAO countryDAO;

    @Override
    public List<Country> getCountriesByNameCriteria(String name) {
        return countryDAO.getCountriesByNameCriteria(name);
    }
    @Override
    public Country getCountryByName(String name) {
        return countryDAO.getCountryByName(name);
    }

}
