package com.intern.email;

import com.intern.model.AttachmentParameters;
import com.intern.model.Recipients;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class SSLEmailSender {
    private com.intern.model.Message message;
    private Recipients recipients;
    private AttachmentParameters attachmentParameters;
    private Date dateToSend;

    public SSLEmailSender(com.intern.model.Message message, Recipients recipients, AttachmentParameters attachmentParameters, Date dateToSend) {
        this.message = message;
        this.recipients = recipients;
        this.attachmentParameters = attachmentParameters;
        this.dateToSend = dateToSend;
    }

    public void send(String from) {
        Session session = Session.getInstance(createSessionProperties(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ppwj.andrzejkowalski","WojciecHl@c69");
                    }
                });

        try {
            Message messageToSend = buildMessage(session, from);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com","ppwj.andrzejkowalski", "WojciecHl@c69");
            Transport.send(messageToSend, messageToSend.getAllRecipients());
            transport.close();

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message buildMessage(Session session, String from) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            setAllRecipients(message);
            message.setSubject(this.message.getSubject());
            if(attachmentParameters != null) {
                message.setContent(createMultipartMessage());
            } else {
                message.setText(this.message.getContent());
            }
            if(dateToSend != null) {
                message.setSentDate(dateToSend);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
       return message;
    }

    private Multipart createMultipartMessage() throws MessagingException {
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(this.message.getContent());
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        String file = attachmentParameters.getPath();
        String fileName = attachmentParameters.getFileName();
        DataSource source = new FileDataSource(file);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
        return multipart;
    }

    private void setAllRecipients(Message message) throws MessagingException {
        ArrayList<String> normalRecipients = recipients.getNormalSenders();
        if(normalRecipients != null) {
            for (String normalRecipient : normalRecipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(normalRecipient));
            }
        }
        ArrayList<String> carbonCopySenders = recipients.getCarbonCopy();
        if(carbonCopySenders != null) {
            for (String carbonCopySender : carbonCopySenders) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(carbonCopySender));
            }
        }
        ArrayList<String> blindCarbonCopySenders = recipients.getBlindCarbonCopy();
        if(blindCarbonCopySenders != null) {
            for (String blindCarbonCopySender : blindCarbonCopySenders) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(blindCarbonCopySender));
            }
        }
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
