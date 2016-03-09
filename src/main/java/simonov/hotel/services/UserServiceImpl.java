package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.UserDAO;
import simonov.hotel.entity.User;
import simonov.hotel.services.interfaces.UserService;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean save(User user) {
        if (isLoginFree(user.getLogin()) && isEmailFree(user.getEmail())) {
            userDAO.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User get(Integer id) {
        return userDAO.get(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User getLoggedUser(String login, String password) {
        return userDAO.getLoggedUser(login, password);
    }

    @Override
    public boolean isLoginFree(String login) {
        return (login != null && !login.isEmpty()) && userDAO.isLoginFree(login);
    }

    @Override
    public boolean isEmailFree(String email) {
        return (email != null && !email.isEmpty()) && userDAO.isEmailFree(email);
    }
}
