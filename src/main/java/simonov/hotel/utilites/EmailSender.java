package simonov.hotel.utilites;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import simonov.hotel.entity.Booking;

import java.util.List;

@Service
public class EmailSender {
    private static Log log = LogFactory.getLog(EmailSender.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    public void sendEmail(String to, String message) {
        templateMessage.setTo(to);
        templateMessage.setText(message);
        try {
            mailSender.send(templateMessage);
        } catch (MailException e) {
            log.error("Email sending failed due to exception:", e);
        }
    }

    public void sendEmail(List<Booking> bookingList, String message) {
        bookingList.stream().forEach(booking -> sendEmail(booking.getUser().getEmail(), message));
    }
}
