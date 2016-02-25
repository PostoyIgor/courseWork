package simonov.hotel.dao.repository;

import simonov.hotel.entity.Booking;

import java.util.List;

public interface IBookingDAO extends GenericDAO<Booking, Integer> {
    List<Booking> getBookingsByUser(int userId);
    List<Booking> getBookingByRoom(int roomId);
}
