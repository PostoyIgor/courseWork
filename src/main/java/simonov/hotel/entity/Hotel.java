package simonov.hotel.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@FilterDef(name="RoomFilter",
        parameters={
                @ParamDef( name = "startDate", type ="java.time.LocalDate"),
                @ParamDef( name = "endDate", type ="java.time.LocalDate"),
                @ParamDef( name = "seats", type ="integer")
        } )
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String city;
    @Column
    private String name;
    @Column
    private int stars;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @Filter(name = "RoomFilter", condition = "id NOT IN (select b.room_id from Booking b " +
            "where b.startDate<=:endDate and b.endDate>=:startDate) AND seats >= :seats")
    private List<Room> rooms;

    @ManyToOne
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<Room> getRooms() {
        if (rooms==null){
            rooms = new ArrayList<>();
        }
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room){
        getRooms().add(room);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
