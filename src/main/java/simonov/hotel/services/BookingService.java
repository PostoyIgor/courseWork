package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.IBookingDAO;
import simonov.hotel.entity.Booking;

import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    IBookingDAO bookingDAO;

    public List<Booking> getBookingsByUser(int userId){
        return bookingDAO.getBookingsByUser(userId);
    }
    public List<Booking> getBookingByRoom(int roomId) {
        return bookingDAO.getBookingByRoom(roomId);
    }

    public void save(Booking booking){
       bookingDAO.save(booking);
    }

    public void delete(Booking booking){
        bookingDAO.delete(booking);
    }

    public List<Booking> getBookings(){
        return bookingDAO.getAll();
    }


}
