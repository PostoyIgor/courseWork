package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.OrderDAO;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.BookingService;
import simonov.hotel.services.interfaces.OrderService;
import simonov.hotel.utilites.BookingControl;
import simonov.hotel.utilites.EmailSender;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    EmailSender emailSender;
    @Autowired
    BookingService bookingService;
    @Autowired
    BookingControl bookingControl;

    @Override
    public List<Room> createOrder(List<Booking> bookings, User user) {
        Order order = new Order();
        order.setCreationTime(System.currentTimeMillis());
        order.setStatus(Status.NotConfirmed);
        order.setUser(user);
        List<Room> rooms;
        bookings.stream().forEach(booking -> booking.setOrder(order));
        rooms = bookingService.saveAll(bookings);
        if (rooms.isEmpty()) {
            order.setBookings(bookings);
            orderDAO.save(order);
            bookingControl.addOrder(order);
        }
        return rooms;
    }

    @Override
    public Integer save(Order order) {
        return orderDAO.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.getOrdersByUser(userId);
    }

    @Override
    public Order getOrderById(int id) {
        return orderDAO.get(id);
    }

    @Override
    public boolean updateStatus(Order order) {
        if (bookingControl.removeOrder(order)) {
            order.setStatus(Status.Confirmed);
            orderDAO.update(order);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Order order, String message) {
        orderDAO.delete(order);
        emailSender.sendEmail(order.getUser().getEmail(), message);
    }
}
