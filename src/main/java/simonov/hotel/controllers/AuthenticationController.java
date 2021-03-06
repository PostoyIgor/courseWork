package simonov.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import simonov.hotel.entity.Role;
import simonov.hotel.entity.User;
import simonov.hotel.services.interfaces.UserService;

import javax.validation.Valid;

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
    public String saveUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }
        if (userService.save(user)) {
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("message", "Error registration!");
            return "error";
        }
    }

    @RequestMapping(value = "/check-user", method = RequestMethod.POST)
    public
    @ResponseBody
    String checkUser(@ModelAttribute User user, Model model) {
        User loggedUser = userService.getLoggedUser(user.getLogin(), user.getPassword());
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
            return "logged";
        } else {
            return "Wrong login or password!";
        }
    }

    @RequestMapping(value = "/check-login")
    public
    @ResponseBody
    boolean checkLoginIsFree(@RequestParam String login) {
        return userService.isLoginFree(login);
    }

    @RequestMapping(value = "/check-email")
    public
    @ResponseBody
    boolean checkEmailIsFree(@RequestParam String email) {
        return userService.isEmailFree(email);
    }


    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute User user,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String phone) {
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPhone(phone);
        userService.update(user);
        return "userProfile";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(SessionStatus status) {
        //TODO ajax!
        status.setComplete();
        return "redirect:/";
    }

    @ModelAttribute
    public User createUser() {
        User user = new User();
        user.setRole(Role.NotAuthorized);
        return user;
    }
}
