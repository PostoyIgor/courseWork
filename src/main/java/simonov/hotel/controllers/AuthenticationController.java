package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.Role;
import simonov.hotel.entity.User;
import simonov.hotel.services.UserService;

@Controller
@EnableWebMvc
@SessionAttributes(types = User.class)
public class AuthenticationController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/save-user", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute User user, Model model){
        //TODO checkUser validation!!!
        userService.save(user);
        model.addAttribute("user",user);
        return "redirect:/";
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public @ResponseBody String checkUser(@ModelAttribute User user, Model model){
        User loggedUser = userService.getLoggedUser(user.getLogin(),user.getPassword());
        if (loggedUser!= null){
            model.addAttribute("user",loggedUser);
            return "logged";
        } else {
            return "Wrong login or password!";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
    @ModelAttribute
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return new User();
    }
}
