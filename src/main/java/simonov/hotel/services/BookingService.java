package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.BookingDAO;
import simonov.hotel.entity.Booking;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingDAO bookingDAO;

    public LocalDate getBooking(){
        return bookingDAO.getBooking().getStartDate();
    }

    public List<Booking> getBookingsByUser(int userId){
        return bookingDAO.getBookingsByUser(userId);
    }
}
