package simonov.hotel.utilites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import simonov.hotel.entity.Order;
import simonov.hotel.services.interfaces.OrderService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class BookingControl {
    private static final String MESSAGE = "If you do not pay within a hour, your reservation will be deleted";

    @Autowired
    EmailSender emailSender;
    @Autowired
    OrderService orderService;

    private final ConcurrentHashMap<Integer, Order> map = new ConcurrentHashMap<>();

    public BookingControl() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay((Runnable) () -> {
            List<Order> mailList = new LinkedList<>();
            try {
                synchronized (map) {
                    System.out.println("Every 30 sec");
                    for (Map.Entry<Integer, Order> entry : map.entrySet()) {
                        System.out.println("inside loop");
                        long creationTime = (entry.getValue()).getCreationTime();
                        if ((System.currentTimeMillis() - creationTime) >= 140000 /*86400000*/) {
                            System.out.println("Before deleting");
                            orderService.delete(entry.getValue(), "Your booking del");
                            map.remove(entry.getKey());
                        } else if ((System.currentTimeMillis() - creationTime) >= 120000 /*82800000*/) {
                            System.out.println("add to mailList");
                            mailList.add(entry.getValue());
                        }
                    }
                }
                if (!mailList.isEmpty()) {
                    System.out.println("SENDING");
                    emailSender.sendEmail(mailList, MESSAGE);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void addOrder(Order order) {
        map.put(order.getId(), order);
    }

    public synchronized boolean removeOrder(Order order) {
        System.out.println(map.size() + " MAP SIZE");
        if (map.values().contains(order)) {
            map.remove(order.getId());
            return true;
        } else {
            return false;
        }
    }
}

