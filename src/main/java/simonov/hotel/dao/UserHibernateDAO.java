package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.UserDAO;
import simonov.hotel.entity.User;

import java.util.List;

@Repository
public class UserHibernateDAO extends AbstractDAO<User,Integer> implements UserDAO {

    public UserHibernateDAO() {
        super(User.class);
    }

    public User getLoggedUser(String login, String password) {
        return (User) getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("login", login))
                .add(Restrictions.eq("password", password)).uniqueResult();
    }

    @Override
    public boolean isLoginFree(String login) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        return criteria.add(Restrictions.eq("login",login)).uniqueResult()==null;
    }

    @Override
    public boolean isEmailFree(String email) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        return criteria.add(Restrictions.eq("email",email)).uniqueResult()==null;
    }
}
