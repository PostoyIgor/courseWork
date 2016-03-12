package simonov.hotel.entity;

import java.time.LocalDate;
import java.util.Map;

public class Request {

    private int countryId;
    private int cityId;
    private int hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Map<Integer, Integer> seats;


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Map<Integer, Integer> getSeats() {
        return seats;
    }

    public void setSeats(Map<Integer, Integer> seats) {
        this.seats = seats;
    }
}
