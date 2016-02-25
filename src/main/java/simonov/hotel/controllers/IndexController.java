package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.User;
import simonov.hotel.services.BookingService;
import simonov.hotel.services.HotelService;
import simonov.hotel.services.RoomService;
import simonov.hotel.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@EnableWebMvc
@SessionAttributes(names = "user", types = User.class)
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
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute User user, Model model) {
        model.addAttribute("hotels", hotelService.getHotelList());
        model.addAttribute("user", user);
        return "main";
    }

    @RequestMapping(value = "/search")
    public String searchRooms(@RequestParam(required = false) String city,
                              @RequestParam(required = false) String hotel,
                              @RequestParam(required = false) Integer stars,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                              @RequestParam(required = false) Integer numOfTravelers, Model model) {
        model.addAttribute("hotels", hotelService.getHotelsWithPattern(city, hotel, stars, fromDate, toDate, numOfTravelers));
        return "main";
    }


    @RequestMapping(value = "/hotel/{id}")
    public String hotelInfo(@PathVariable int id, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "hotelInfo";
    }

    @RequestMapping(value = "/hotel/{hotelId}/roomDetails/{roomId}")
    public String roomInfo(@PathVariable int hotelId, @PathVariable int roomId, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(hotelId));
        model.addAttribute("room", roomService.getRoomById(roomId));
        return "roomInfo";
    }


    @RequestMapping(value = "/check-date", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean checkUser(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
                      @RequestParam int roomId) {
        return fromDate.isBefore(toDate) && roomService.isFree(fromDate, toDate, roomId);
    }

    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    public String addRoom(@RequestParam String type,
                          @RequestParam int number,
                          @RequestParam String description,
                          @RequestParam int price,
                          @RequestParam int seats,
                          @RequestParam int hotel,
                          @RequestParam MultipartFile image, HttpSession session) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setSeats(seats);
        room.setDescription(description);
        room.setNumber(number);
        Hotel currentHotel = hotelService.getHotelById(hotel);
        room.setHotel(currentHotel);
        roomService.saveRoom(room);
        if (!image.isEmpty()) {
            try {
                File imageRoom = new File(servletContext.getRealPath("/resources/images/rooms/")
                        + File.separator + currentHotel.getName() + room.getId() + ".jpg");
                image.transferTo(imageRoom);
            } catch (IOException e) {
                return "You failed to upload " + image + " => " + e.getMessage();
            }
        }
        return "redirect:/hotel/" + currentHotel.getId();
    }

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public String addHotel(@RequestParam String name,
                           @RequestParam String city,
                           @RequestParam int stars,
                           @RequestParam int owner,
                           @RequestParam MultipartFile image, HttpSession session) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setCity(city);
        newHotel.setStars(stars);
        newHotel.setUser(userService.get(owner));
        hotelService.saveHotel(newHotel);
        if (!image.isEmpty()) {
            try {
                File imageHotel = new File(servletContext.getRealPath("/resources/images/hotels/")
                        + File.separator + newHotel.getId() + ".jpg");
                image.transferTo(imageHotel);
            } catch (IOException e) {
                return "You failed to upload " + image + " => " + e.getMessage();
            }
        }

        return "redirect:/profile";
    }

    @RequestMapping("/profile")
    public String userProfile(ModelMap modelMap) {
        User user = (User) modelMap.get("user");
        if (user.isAdmin()) {
            //TODO retrieve hotels from user or not ? like userService.getHotelsByUser(user)
            List<Hotel> hotels = hotelService.getUserHotels(user.getId());

            modelMap.addAttribute("hotels", hotels);
            return "hotelsOwner";
        } else {
            modelMap.addAttribute("bookings", bookingService.getBookingsByUser(user.getId()));
            return "userReservation";
        }
    }


    @ModelAttribute
    public User createUser() {
        return new User();
    }

    @ModelAttribute
    public Room createRoom() {
        return new Room();
    }
}
