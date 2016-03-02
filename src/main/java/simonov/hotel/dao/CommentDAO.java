package simonov.hotel.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.ICommentDAO;
import simonov.hotel.entity.Comment;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CommentDAO extends AbstractDAO<Comment, Integer> implements ICommentDAO {
    @Override
    public List<Comment> getCommentsByHotel(Integer hotelId) {
        Query query = getCurrentSession().createQuery("from Comment where hotel.id = :hotelId");
        query.setInteger("hotelId", hotelId);
        return query.list();
    }

    @Override
    public Double getRatingByHotel(Integer hotelId) {
        Criteria criteria = getCurrentSession().createCriteria(Comment.class);
        criteria.add(Restrictions.eq("hotel.id", hotelId));
        criteria.setProjection(Projections.avg("rating"));
        return (Double) criteria.uniqueResult();
    }
}
