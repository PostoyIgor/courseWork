package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.UserDAO;
import simonov.hotel.entity.User;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public void save(User user){
        userDAO.save(user);
    }

    public User getUser(int id){
       return userDAO.getUser(id);
    }

    public List<User> getUsers(){
        return userDAO.getUsers();
    }

    public User getLoggedUser(String login, String password){
       return userDAO.getLoggedUser(login,password);
    }
}
