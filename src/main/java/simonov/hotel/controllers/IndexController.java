package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.*;
import simonov.hotel.services.interfaces.*;
import simonov.hotel.utilites.EmailSender;
import simonov.hotel.utilites.FileUpLoader;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc
@SessionAttributes({"user", "hotels"})
public class IndexController {

    @Autowired
    HotelService hotelService;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    BookingService bookingService;
    @Autowired
    CommentService commentService;
    @Autowired
    ServletContext servletContext;
    @Autowired
    CityService cityService;
    @Autowired
    OrderService orderService;
    @Autowired
    ConvenienceService convenienceService;
    @Autowired
    EmailSender emailSender;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute User user, @ModelAttribute ArrayList<Hotel> hotels, Model model) {
        model.addAttribute("hotels", hotelService.getFirstTenHotels());
//        Request request = new Request();
//        request.setCountryId(1);
//        request.setCityId(1);
//        request.setHotelId(1);
//        request.setStartDate(LocalDate.parse("2016-03-05"));
//        request.setEndDate(LocalDate.parse("2016-05-19"));
//        Map<Integer, Integer> seats = new HashMap<>();
//        seats.put(2, 4);
//        seats.put(1,1);
//        request.setSeats(seats);
//        request.setFirstResult(0);
//        request.setLimit(10);
//        model.addAttribute("hotels", hotelService.getHotelsWithFreeRoom(request));
//        hotelService.getHotelsWithFreeRoom(request).stream().forEach(hotel -> System.out.println(hotel.getRooms().size()));
        return "main";
    }


    @RequestMapping(value = "/hotel/{id}")
    public String searchHotel(@PathVariable int id, Model model, @ModelAttribute User user,
                              @ModelAttribute("hotels") ArrayList<Hotel> hotels) {
        Hotel hotel = hotelService.getHotelById(id);
        model.addAttribute("hotel", hotel);
        List<Room> rooms = roomService.getRoomsByHotel(id);
        model.addAttribute("rooms", rooms);
        List<Booking> bookings = new ArrayList<>();
        for (int i = id * 3 + 1; i < id * 3 + 7; i++) {
            Booking booking = new Booking();
            booking.setUser(user);
            booking.setRoom(roomService.getRoomById(i));
            booking.setStartDate(LocalDate.parse("2016-03-16"));
            booking.setEndDate(LocalDate.parse("2016-03-19"));
            bookings.add(booking);
        }
        orderService.createOrder(bookings, user);
        return "hotelInfo";
    }

    @RequestMapping(value = "/hotel/{hotelId}/roomDetails/{roomId}")
    public String roomInfo(@PathVariable int hotelId, @PathVariable int roomId, Model model, @ModelAttribute User user) {
        Room room = roomService.getRoomById(roomId);
        if (hotelId != room.getHotel().getId()) {
            return "error";
        }
        model.addAttribute("hotel", room.getHotel());
        model.addAttribute("room", room);
        return "roomInfo";
    }


    @RequestMapping(value = "/check-date", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkDate(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                      @RequestParam int roomId, @ModelAttribute User user, Model model) {
        if (user.getRole() != Role.NotAuthorized) {
            if (fromDate.isBefore(toDate) && roomService.isFree(fromDate, toDate, roomId)) {
                Booking booking = new Booking();
                booking.setStartDate(fromDate);
                booking.setEndDate(toDate);
                booking.setRoom(roomService.getRoomById(roomId));
                booking.setUser(user);
//                booking.setStatus(Status.NotConfirmed);
                bookingService.save(booking);
                return true;
            } else return false;
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/comment/{hotelId}")
    public String saveComment(@PathVariable int hotelId, @ModelAttribute User user) {
        Comment comment = new Comment();
        comment.setHotel(hotelService.getHotelById(hotelId));
        comment.setRating(4d);
        comment.setComment("Bad");
        comment.setUser(user);
        commentService.save(comment);
        return "userProfile";
    }

    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    public String addRoom(@RequestParam String type,
                          @RequestParam int number,
                          @RequestParam String description,
                          @RequestParam Double price,
                          @RequestParam int seats,
                          @RequestParam int hotelId,
                          @RequestParam MultipartFile image) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setSeats(seats);
        room.setDescription(description);
        room.setNumber(number);
        Hotel currentHotel = hotelService.getHotelById(hotelId);
        room.setHotel(currentHotel);
        roomService.saveRoom(room);
        String subPath = servletContext.getRealPath("/resources/images/rooms/") + currentHotel.getName() + room.getId() + ".jpg";
        FileUpLoader.uploadImage(image, subPath);
        return "redirect:/hotel/" + currentHotel.getId();
    }

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public String addHotel(@RequestParam String name,
                           @RequestParam String city,
                           @RequestParam int stars,
                           @RequestParam int owner,
                           @RequestParam MultipartFile image) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setCity(cityService.getCityByName(city));
        newHotel.setStars(stars);
        newHotel.setUser(userService.get(owner));
        hotelService.saveHotel(newHotel);
        if (image.getContentType().equals("image/jpeg")) {
            String subPath = servletContext.getRealPath("/resources/images/hotels/") + newHotel.getId() + ".jpg";
            FileUpLoader.uploadImage(image, subPath);
        }
        return "redirect:/profile";
    }

    @RequestMapping("/profile")
    public String userProfile(@ModelAttribute User user, Model model) {
        if (user.getRole() == Role.HotelOwner) {
            List<Hotel> hotels = hotelService.getHotelsByUser(user.getId());
            model.addAttribute("hotels", hotels);
            return "hotelsOwner";
        } else if (user.getRole() == Role.CLIENT) {
            model.addAttribute("bookings", bookingService.getActualBookingsByUser(user.getId()));
            return "userReservation";
        } else return "redirect:/";
    }

    @ModelAttribute("user")
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }

    @ModelAttribute("hotels")
    public ArrayList<Hotel> getHotels() {
        return new ArrayList<>();
    }

    @ModelAttribute
    public Room createRoom() {
        return new Room();
    }
}
