package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.IUserDAO;
import simonov.hotel.entity.User;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    IUserDAO userDAO;

    public void save(User user){
        userDAO.save(user);
    }

    public User get(Integer id){
       return userDAO.get(id);
    }

    public List<User> getAll(){
        return userDAO.getAll();
    }

    public User getLoggedUser(String login, String password){
       return userDAO.getLoggedUser(login,password);
    }
}
