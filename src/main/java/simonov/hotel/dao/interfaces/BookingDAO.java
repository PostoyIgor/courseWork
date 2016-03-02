package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Booking;

import java.util.List;

public interface BookingDAO extends GenericDAO<Booking, Integer> {
    List<Booking> getBookingsByUser(int userId);
    List<Booking> getBookingByRoom(int roomId);
}
