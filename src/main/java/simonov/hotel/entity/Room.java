package simonov.hotel.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String type;

    @Column
    private int price;

    @Column
    private int seats;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hotel_id" )
    private Hotel hotel;
//    public boolean isFree(LocalDate start, LocalDate end){
//        for(Booking booking: getBookings()){
//            if(booking.getEndDate().before(start))
//                continue;
//            if(booking.getStartDate().after(end))
//                continue;
//            return false;
//        }
//        return true;
//    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
