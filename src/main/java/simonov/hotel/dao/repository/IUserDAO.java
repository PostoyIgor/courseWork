package simonov.hotel.dao.repository;

import simonov.hotel.entity.User;

public interface IUserDAO extends GenericDAO<User, Integer> {

    User getLoggedUser(String login, String password);
}
