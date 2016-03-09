package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Booking;
import simonov.hotel.entity.Room;
import simonov.hotel.services.interfaces.BookingService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {


    @Autowired
    BookingDAO bookingDAO;
    @Autowired
    RoomDAO roomDAO;

    @Override
    public List<Booking> getBookingsByUser(int userId) {
        return bookingDAO.getBookingsByUser(userId);
    }

    @Override
    public List<Booking> getActualBookingsByUser(int userId) {
        return bookingDAO.getActualBookingsByUser(userId);
    }

    @Override
    public List<Booking> getBookingsByRoom(int roomId) {
        return bookingDAO.getBookingsByRoom(roomId);
    }

    @Override
    public List<Booking> getBookingsByHotel(int hotelId) {
        return bookingDAO.getBookingsByHotel(hotelId);
    }

    @Override
    public List<Booking> getActualBookingsByHotel(int hotelId) {
        return bookingDAO.getActualBookingsByHotel(hotelId);
    }

    @Override
    public List<Booking> getHistoryBookingsByUser(int userId) {
        return bookingDAO.getHistoryBookingsByUser(userId);
    }

    @Override
    public List<Booking> getHistoryBookingsByHotel(int hotelId) {
        return bookingDAO.getHistoryBookingsByHotel(hotelId);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Integer save(Booking booking) {
        if (roomDAO.isFree(booking.getStartDate(), booking.getEndDate(), booking.getRoom().getId())){
            return bookingDAO.save(booking);
        }
        return 0;
    }

    @Override
    public void delete(Booking booking) {
        bookingDAO.delete(booking);
    }

    @Override
    public void update(Booking booking) {
        bookingDAO.update(booking);
    }

    @Override
    public List<Booking> getBookings() {
        return bookingDAO.getAll();
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Room> saveAll(List<Booking> bookings) {
        List<Room> result = new ArrayList<>();
        for (Booking b : bookings){
            if (!roomDAO.isFree(b.getStartDate(),b.getEndDate(), b.getRoom().getId())){
                result.add(b.getRoom());
            }
        }
        if (result.isEmpty()){
            for (Booking booking : bookings) {
                bookingDAO.save(booking);
            }
        }
        return result;
    }


}
