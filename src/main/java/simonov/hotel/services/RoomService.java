package simonov.hotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.RoomDAO;
import simonov.hotel.entity.Room;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    RoomDAO roomDAO;

    public void saveRoom(Room room){
        roomDAO.saveRoom(room);
    }

    public List<Room> getRooms(){
        return roomDAO.getRooms();
    }
}
