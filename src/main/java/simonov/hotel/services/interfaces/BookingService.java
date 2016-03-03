package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.Booking;

import java.util.List;
@Service
public interface BookingService {
    List<Booking> getBookingsByUser(int userId);
    List<Booking> getBookingByRoom(int roomId);
    void save(Booking booking);
    void delete(Booking booking);
    List<Booking> getBookings();
    void update(Booking booking);
}
