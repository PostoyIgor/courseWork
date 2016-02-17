package simonov.hotel.entity;

import java.time.LocalDate;
import java.util.Set;

public class Reservation {
    private int id;
    private User user;
    private Set<Room> rooms;
    private LocalDate startDate;
    private LocalDate endDate;
}
