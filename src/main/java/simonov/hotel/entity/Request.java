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
    private int stars;
    private int firstResult;
    private int limit;

    public String getQuery() {
        StringBuilder query = new StringBuilder("select distinct h from Hotel as h inner join fetch h.rooms as room where");
        StringBuilder subQuery = new StringBuilder(" (");
        int mapSize = seats.size();

        if (cityId != 0) {
            query.append(" h.city.id = :cityId and");
        } else if (hotelId != 0) {
            query.append(" h.id = :hotelId and");
        } else if (countryId != 0) {
            query.append(" h.city in (select c.id from City as c where c.country.id = :countryId) and ");
        }
        for (int index = 1; index <= mapSize; index++) {
            query.append(" (select count(r.id) from Room as r where h.id = r.hotel.id " +
                    "and r.seats = :seats").append(index)
                    .append(" and not exists (select distinct b.room.id from Booking as b where r.id = b.room.id" +
                            " and (endDate>=:startDate and startDate<=:endDate)))>=:value").append(index).append(" and ");
            if (index == mapSize) {
                subQuery.append("room.seats = :seats").append(index).append(") and");
            } else {
                subQuery.append("room.seats = :seats").append(index).append(" or ");
            }
        }
        query.append(subQuery);
        if (stars != 0) {
            query.append(" h.stars = :stars and");
        }
        query.append(" not exists (select distinct b.room.id from Booking as b where room.id = b.room.id" +
                " and (endDate>=:startDate and startDate<=:endDate)) order by h.id");
        return query.toString();
    }

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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
