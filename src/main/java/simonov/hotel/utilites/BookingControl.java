package simonov.hotel.utilites;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simonov.hotel.dao.interfaces.BookingDAO;
import simonov.hotel.entity.Booking;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class BookingControl {

    @Autowired
    BookingDAO bookingDAO;

    @Autowired
    EmailSender emailSender;

    private final ConcurrentHashMap<Integer, Booking> map = new ConcurrentHashMap<>();

    public BookingControl(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay((Runnable) () -> {
            List<Booking> mailList = new LinkedList<>();
            synchronized (map){
                for (Map.Entry entry : map.entrySet()){
                    long creationTime = ((Booking)entry.getValue()).getCreationTime();
                    if ((System.currentTimeMillis() - creationTime) >= 86400000){
                        bookingDAO.delete((Booking) entry.getValue());
                        map.remove(entry.getKey());
                    }else
                    if ((System.currentTimeMillis() - creationTime) >= 82800000){
                        mailList.add((Booking) entry.getValue());
                    }
                }}

            emailSender.send(mailList);
        },0,1,TimeUnit.HOURS);
    }


    public void addAll(List<Booking> list){
        for (Booking b : list){
            map.put(b.getId(),b);
        }
    }

    public synchronized boolean removeAll(List<Booking> list){
        if (map.values().containsAll(list)){
            for (Booking b : list){
                map.remove(b.getId(),b);
            }
            return true;
        }else {
            return false;
        }
    }



}

