package simonov.hotel.dao.interfaces;

import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface IRoomDAO extends GenericDAO<Room, Integer> {
    boolean isFree(LocalDate start, LocalDate end, int roomId);

    List<Room> getRoomsByHotel(int hotelId);
}
