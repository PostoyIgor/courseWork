package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.User;

import java.util.List;

@Service
public interface UserService {

    boolean save(User user);
    User get(Integer id);
    User getLoggedUser(String login, String password);
    boolean isLoginFree(String login);
    boolean isEmailFree(String email);
    void update(User user);
}
