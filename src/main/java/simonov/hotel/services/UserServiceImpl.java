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
    public void save(User user){
        userDAO.save(user);
    }
    @Override
    public User get(Integer id){
       return userDAO.get(id);
    }
    @Override
    public List<User> getAll(){
        return userDAO.getAll();
    }
    @Override
    public User getLoggedUser(String login, String password){
       return userDAO.getLoggedUser(login,password);
    }
}
