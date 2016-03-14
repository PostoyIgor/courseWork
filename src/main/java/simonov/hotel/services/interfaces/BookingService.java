package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;

import java.util.List;

@Service
public interface BookingService {
    List<Room> saveAll(List<Booking> bookings);

    List<Booking> getBookingsByUser(int userId);

    List<Booking> getActualBookingsByUser(int userId);

    List<Booking> getActualBookingsByHotel(int hotelId);

    List<Booking> getHistoryBookingsByUser(int userId);

    List<Booking> getHistoryBookingsByHotel(int hotelId);

    List<Booking> getBookingsByRoom(int roomId);

    List<Booking> getBookingsByHotel(int hotelId);

    Integer save(Booking booking);

    void delete(Booking booking, String message);

    List<Booking> getBookings();

    void update(Booking booking);

    boolean updateStatus(List<Booking> bookings);
}
