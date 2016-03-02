package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.IRoomDAO;
import simonov.hotel.entity.Room;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoomService {
    @Autowired
    IRoomDAO roomDAO;

    public void saveRoom(Room room){
        roomDAO.save(room);
    }

    public List<Room> getRooms(){
        return roomDAO.getAll();
    }

    public boolean isFree(LocalDate start, LocalDate end, int roomId){
        return roomDAO.isFree(start, end, roomId);
    }

    public Room getRoomById(int id) {
       return roomDAO.get(id);
    }

    public List<Room> getRoomsByHotel(int hotelId) {
        return roomDAO.getRoomsByHotel(hotelId);
    }
}
