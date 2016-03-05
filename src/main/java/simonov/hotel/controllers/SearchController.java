package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.City;
import simonov.hotel.entity.Country;
import simonov.hotel.entity.Hotel;
import simonov.hotel.services.interfaces.*;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping( "/search")
public class SearchController {

    @Autowired
    HotelService hotelService;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    BookingService bookingService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    @RequestMapping(value = "/")
    public String search(@RequestParam(required = false) String country,
                         @RequestParam(required = false) String city,
                         @RequestParam(required = false) String hotel,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                         @RequestParam(required = false) Integer numOfTravelers,
                         Model model) {
        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(country, city, hotel, fromDate, toDate, numOfTravelers));
        return "main";
    }

    @RequestMapping(value = "/", params = "country")
    public @ResponseBody List<Country> getCountries(@RequestParam String country){
        return countryService.getCountriesByNameCriteria(country);
    }

    @RequestMapping(value = "/", params = {"city","countryId"})
    public @ResponseBody List<City> getCities(@RequestParam String city, @RequestParam Integer countryId){
        return cityService.getCitiesByCriteria(city,countryId);
    }

    @RequestMapping(value = "/", params = {"hotel","cityId","countryId"})
    public @ResponseBody List<Hotel> getHotels(@RequestParam String hotel, @RequestParam Integer city, @RequestParam Integer countryId){
        return hotelService.getHotelsByCriteria(countryId,city,hotel);
    }

}
