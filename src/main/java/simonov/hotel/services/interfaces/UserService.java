package simonov.hotel.services.interfaces;

import org.springframework.stereotype.Service;
import simonov.hotel.entity.User;

import java.util.List;

@Service
public interface UserService {

    void save(User user);
    User get(Integer id);
    List<User> getAll();
    User getLoggedUser(String login, String password);
}
