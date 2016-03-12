package simonov.hotel.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import simonov.hotel.dao.interfaces.CommentDAO;
import simonov.hotel.entity.Comment;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class CommentHibernateDAO extends AbstractDAO<Comment, Integer> implements CommentDAO {

    public CommentHibernateDAO() {
        super(Comment.class);
    }

    @Override
    public List<Comment> getCommentsByHotel(int hotelId) {
        Query query = getCurrentSession().createQuery("from Comment where hotel.id = :hotelId");
        query.setInteger("hotelId", hotelId);
        return query.list();
    }

}
