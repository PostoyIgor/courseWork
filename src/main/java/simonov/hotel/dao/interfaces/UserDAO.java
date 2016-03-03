package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.User;

public interface UserDAO extends GenericDAO<User, Integer> {

    User getLoggedUser(String login, String password);
}
