package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import simonov.hotel.dao.interfaces.CityDAO;
import simonov.hotel.entity.City;

public class CityHibernateDAO extends AbstractDAO<City,Integer> implements CityDAO {

    public CityHibernateDAO() {
        super(City.class);
    }

    @Override
    public City getCityByName(String cityName) {
        Criteria criteria = getCurrentSession().createCriteria(City.class);
        criteria.add(Restrictions.ilike("name",cityName, MatchMode.START));
        return (City) criteria.uniqueResult();
    }
}
