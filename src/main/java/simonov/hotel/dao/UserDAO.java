package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IUserDAO;
import simonov.hotel.entity.User;

import java.util.List;

@Repository
public class UserDAO extends AbstractDAO<User,Integer> implements IUserDAO{

    public User getLoggedUser(String login, String password) {
        Criteria authCriteria = getCurrentSession().createCriteria(User.class);
        List users = authCriteria.add(Restrictions.eq("login", login))
                .add(Restrictions.eq("password", password)).list();
        return users.size() == 1 ? (User) users.get(0) : null;
    }
}
