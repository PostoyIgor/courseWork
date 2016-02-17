package simonov.hotel.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.entity.User;

import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    SessionFactory sessionFactory;

    public void save(User user){
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }


    public User getUser(int id) {
        return (User) sessionFactory.getCurrentSession().get(User.class,id);
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers(){
        return sessionFactory.getCurrentSession().createCriteria(User.class).list();
    }
}
