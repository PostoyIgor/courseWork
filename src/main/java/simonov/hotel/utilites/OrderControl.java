package simonov.hotel.utilites;

import org.apache.log4j.Logger;
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
public class OrderControl {
    private static final String MESSAGE = "If you do not pay within a hour, your reservation will be deleted";
    private static final ConcurrentHashMap<Integer, Order> ORDER_MAP = new ConcurrentHashMap<>();
    private Logger logger = Logger.getLogger(OrderControl.class);
    @Autowired
    EmailSender emailSender;
    @Autowired
    OrderService orderService;

    public OrderControl() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay((Runnable) () -> {
            List<Order> mailList = new LinkedList<>();
            synchronized (ORDER_MAP) {
                for (Map.Entry<Integer, Order> entry : ORDER_MAP.entrySet()) {
                    try {
                        long creationTime = (entry.getValue()).getCreationTime();
                        if ((System.currentTimeMillis() - creationTime) >= 120000 /*86400000*/) {
                            orderService.delete(entry.getValue());
                            emailSender.sendEmail(entry.getValue().getUser().getEmail(), "Your bookings is removed");
                            ORDER_MAP.remove(entry.getKey());
                        } else if ((System.currentTimeMillis() - creationTime) >= 60000 /*82800000*/) {
                            mailList.add(entry.getValue());
                        }
                    } catch (Exception e) {
                        logger.error("OrderControl exception:" + e);
                    }
                }
                if (!mailList.isEmpty()) {
                    emailSender.sendEmail(mailList, MESSAGE);
                }
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void addOrder(Order order) {
        ORDER_MAP.put(order.getId(), order);
    }

    public synchronized boolean removeOrder(Order order) {
        if (ORDER_MAP.values().contains(order)) {
            ORDER_MAP.remove(order.getId());
            return true;
        } else {
            return false;
        }
    }
}

