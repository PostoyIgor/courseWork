package simonov.hotel.controllers;

import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.Hotel;
import simonov.hotel.entity.Room;
import simonov.hotel.entity.User;
import simonov.hotel.services.HotelService;
import simonov.hotel.services.RoomService;
import simonov.hotel.services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@EnableWebMvc
@SessionAttributes(value = "user",names = "user",types = User.class)
@RequestMapping("/")
public class IndexController {

    @Autowired
    UserService userService;
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;
    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String printHotels(@ModelAttribute User user, Model model) {
        model.addAttribute("hotels", hotelService.getHotelList());
        User mockUser = new User();
        model.addAttribute("user",user);
        return "main";
    }

    @RequestMapping(value = "/search")
    public String searchRooms(@RequestParam(required = false) String city,
                              @RequestParam(required = false) String hotel,
                              @RequestParam(required = false) Integer stars,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
                              @RequestParam(required = false) Integer numOfTravelers, Model model) {

            model.addAttribute("hotels", hotelService.getHotelsWithTempl(city,hotel,stars,fromDate,toDate,numOfTravelers));
            return "main";
    }


    @RequestMapping(value = "/hotel/{id}")
    public String hotelInfo(@PathVariable int id, Model model) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        return "hotelInfo";
    }

    @RequestMapping(value = "/addRoom", method = RequestMethod.POST)
    public String addRoom(@RequestParam String type,
                          @RequestParam int price,
                          @RequestParam int seats,
                          @RequestParam int hotel,
                          @RequestParam MultipartFile image, HttpSession session) {
        Room room = new Room();
        room.setType(type);
        room.setPrice(price);
        room.setSeats(seats);
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
                           @RequestParam MultipartFile image, HttpSession session) {
        Hotel newHotel = new Hotel();
        newHotel.setName(name);
        newHotel.setCity(city);
        newHotel.setStars(stars);
        newHotel.setId(44);
        hotelService.saveHotel(newHotel);
        if (!image.isEmpty()) {
            try {
                File imageRoom = new File(servletContext.getRealPath("/resources/images/hotels/")
                        + File.separator + newHotel.getId() + ".jpg");
                image.transferTo(imageRoom);
            } catch (IOException e) {
                return "You failed to upload " + image + " => " + e.getMessage();
            }
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/save-user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model){
        //TODO checkUser!!!
        userService.save(user);
        System.out.println("Login" + user.getLogin());
        model.addAttribute("user",user);
        return "redirect:/";
    }

    @ModelAttribute
    public User createUser(){
        return new User();
    }
}
