package simonov.hotel.dao.repository;

import simonov.hotel.entity.Room;

import java.time.LocalDate;

public interface IRoomDAO extends GenericDAO<Room, Integer> {
    boolean isFree(LocalDate start, LocalDate end, int roomId);
}
