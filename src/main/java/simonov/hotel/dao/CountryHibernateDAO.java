package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import simonov.hotel.dao.interfaces.CountryDAO;
import simonov.hotel.entity.Country;

public class CountryHibernateDAO extends AbstractDAO<Country,Integer> implements CountryDAO {

    public CountryHibernateDAO() {
        super(Country.class);
    }

    @Override
    public Country getCountryByName(String countryName) {
        Criteria criteria = getCurrentSession().createCriteria(Country.class);
        criteria.add(Restrictions.eq("name",countryName));
        return (Country) criteria.uniqueResult();
    }
}