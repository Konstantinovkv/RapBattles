package rapbattles.rap_battles.Util;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class EmailSender implements Runnable{
    static Logger log = Logger.getLogger(EmailSender.class.getName());

    private String email;
    private String name;

    public EmailSender(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String sendEmail(String email, String name) throws MessagingException {

        final String username = "FinalProjectITTnoReply@gmail.com";
        final String password = "A12345678@a";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("RapBattlesNoReply@gmail.com", true));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        msg.setSubject("Registration complete");
        msg.setContent("Congratulations "+name+"! You have successfully completed your registration for RapBattles.", "text/html");
        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("", "text/html");
        Transport.send(msg);
        return "Mail sent successfully.";
    }

    @Override
    public void run(){
        try {
            sendEmail(email,name);
        } catch (MessagingException e) {
            log.error(e.getMessage());
            e.getMessage();
        }
    }
}


