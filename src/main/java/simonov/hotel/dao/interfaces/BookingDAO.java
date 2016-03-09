package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Booking;

import java.util.List;

public interface BookingDAO extends GenericDAO<Booking, Integer> {
    List<Booking> getBookingsByUser(int userId);
    List<Booking> getActualBookingsByUser(int userId);
    List<Booking> getActualBookingsByHotel(int hotelId);
    List<Booking> getHistoryBookingsByUser(int userId);
    List<Booking> getHistoryBookingsByHotel(int hotelId);
    List<Booking> getBookingsByRoom(int roomId);
    List<Booking> getBookingsByHotel(int hotelId);
}
