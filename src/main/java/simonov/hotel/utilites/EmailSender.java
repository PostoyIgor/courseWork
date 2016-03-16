package simonov.hotel.utilites;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import simonov.hotel.entity.Order;

import java.util.List;

@Component
public class EmailSender {
    private static Logger logger = Logger.getLogger(EmailSender.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    public void sendEmail(String to, String message) {
        templateMessage.setTo(to);
        templateMessage.setText(message);
        try {
            mailSender.send(templateMessage);
        } catch (NullPointerException | MailException e) {
            logger.error("Email sending failed due to exception:", e);
        }
    }

    public void sendEmail(List<Order> orders, String message) {
        orders.stream().forEach(order -> sendEmail(order.getUser().getEmail(), message));
    }
}
