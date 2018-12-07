package com.intern.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SSLEmailSender {

    public void send(String from, String to) {
        Session session = Session.getInstance(createSessionProperties(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ppwj.andrzejkowalski","WojciecHl@c69");
                    }
                });

        try {
            Message messageToSend = buildMessage(session, from, to);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com","ppwj.andrzejkowalski", "WojciecHl@c69");
            Transport.send(messageToSend, messageToSend.getAllRecipients());
            transport.close();

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message buildMessage(Session session, String from, String to) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setText("Dear Pawel Lacheta," +
                    "\n\n prosze przepisac zeszyty !");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
       return message;
    }

    private Properties createSessionProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
