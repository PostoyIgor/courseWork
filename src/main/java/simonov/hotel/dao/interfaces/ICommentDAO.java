package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Comment;

import java.util.List;

public interface ICommentDAO extends GenericDAO<Comment, Integer> {
    List<Comment> getCommentsByHotel(Integer hotelId);
    Double getRatingByHotel(Integer hotelId);
}
