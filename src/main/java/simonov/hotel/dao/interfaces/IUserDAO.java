package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.User;

public interface IUserDAO extends GenericDAO<User, Integer> {

    User getLoggedUser(String login, String password);
}
