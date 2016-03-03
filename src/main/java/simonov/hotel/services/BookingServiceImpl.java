package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.services.interfaces.BookingService;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingDAO bookingDAO;

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        return bookingDAO.getBookingsByUser(userId);
    }

    @Override
    public List<Booking> getBookingByRoom(int roomId) {
        return bookingDAO.getBookingByRoom(roomId);
    }

    @Override
    public void save(Booking booking) {
        bookingDAO.save(booking);
    }

    @Override
    public void delete(Booking booking) {
        bookingDAO.delete(booking);
    }

    @Override
    public void update(Booking booking){
        bookingDAO.update(booking);
    }

    @Override
    public List<Booking> getBookings() {
        return bookingDAO.getAll();
    }


}
