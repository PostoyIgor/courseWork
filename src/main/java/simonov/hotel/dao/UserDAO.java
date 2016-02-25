package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.repository.AbstractDAO;
import simonov.hotel.dao.repository.IUserDAO;
import simonov.hotel.entity.User;

import java.util.List;

@Repository
public class UserDAO extends AbstractDAO<User,Integer> implements IUserDAO{

//    @Autowired
//    SessionFactory sessionFactory;

//    private Session getCurrentSession() {
//        return sessionFactory.getCurrentSession();
//    }

//    public void save(User user) {
//        getCurrentSession().saveOrUpdate(user);
//    }

//    public User getUser(int id) {
//        return (User) getCurrentSession().get(User.class, id);
//    }

//    @SuppressWarnings("unchecked")
//    public List<User> getUsers() {
//        return getCurrentSession().createCriteria(User.class).list();
//    }

    public User getLoggedUser(String login, String password) {
        Criteria authCriteria = getCurrentSession().createCriteria(User.class);
        List users = authCriteria.add(Restrictions.eq("login", login))
                .add(Restrictions.eq("password", password)).list();
        return users.size() == 1 ? (User) users.get(0) : null;
    }
}
