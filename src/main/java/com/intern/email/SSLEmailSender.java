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

/**
 * handling sending email message via ssl
 */
public class SSLEmailSender {
    /**
     * stores content and subject of email message
     * @see com.intern.model.Message
     */
    private com.intern.model.Message message;
    /**
     * stores recipients of message
     * @see Recipients
     */
    private Recipients recipients;
    /**
     * stores attachment parameters
     * @see AttachmentParameters
     */
    private AttachmentParameters attachmentParameters;

    /**
     * Initialize class fields
     * @param message message to send
     * @param recipients lists of recipients
     * @param attachmentParameters attachment params
     */
    public SSLEmailSender(com.intern.model.Message message, Recipients recipients, AttachmentParameters attachmentParameters) {
        this.message = message;
        this.recipients = recipients;
        this.attachmentParameters = attachmentParameters;
    }

    /**
     * Send email message
     * @param from email address of sender
     */
    public void send(String from) {
        Session session = Session.getInstance(createSessionProperties(),
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SenderData.getUserNameFromEmail(),SenderData.PASSWORD);
                    }
                });

        try {
            Message messageToSend = buildMessage(session, from);

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",SenderData.getUserNameFromEmail(), SenderData.PASSWORD);
            Transport.send(messageToSend, messageToSend.getAllRecipients());
            transport.close();

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * build email message to send
     * @param session session parameters
     * @see Session
     * @param from email address of sender
     * @return message object ready to send
     */
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
       return message;
    }

    /**
     * if there is attachment we have to build multipart message (MIME standard)
     * @return multipart message object
     * @throws MessagingException throws MessagingExceptions
     */
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

    /**
     * set recipients of message from recipients object (class field)
     * @param message message to send
     * @throws MessagingException throws MessagingExceptions
     */
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

    /**
     * create session properties like host, port etc.
     * @return properties object
     */
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
