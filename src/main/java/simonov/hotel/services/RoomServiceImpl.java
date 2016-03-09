package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simonov.hotel.dao.interfaces.RoomDAO;
import simonov.hotel.entity.Room;
import simonov.hotel.services.interfaces.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomDAO roomDAO;
    @Override
    public void saveRoom(Room room){
        roomDAO.save(room);
    }
    @Override
    public boolean isFree(LocalDate start, LocalDate end, int roomId){
        return roomDAO.isFree(start, end, roomId);
    }
    @Override
    public Room getRoomById(int id) {
       return roomDAO.get(id);
    }
    @Override
    public List<Room> getRoomsByHotel(int hotelId) {
        return roomDAO.getRoomsByHotel(hotelId);
    }
}
