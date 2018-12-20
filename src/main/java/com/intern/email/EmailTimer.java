package com.intern.email;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * it is used to deliver email with specific time and date
 */
public class EmailTimer {
    /**
     * stores all necessary data to send emial
     */
    private final SSLEmailSender sslEmailSender;
    /**
     * delivery email message date
     */
    private final Date dateToSend;

    /**
     * it counts to dateToSend
     */
    private final Timer timer;

    /**
     * initialize fields and create instance of Timer
     * @param sslEmailSender data with mail to send
     * @param dateToSend delivery message date
     */
    public EmailTimer(SSLEmailSender sslEmailSender, Date dateToSend) {
        this.sslEmailSender = sslEmailSender;
        this.dateToSend = dateToSend;
        timer = new Timer();
    }

    /**
     * start counting to dateToSend by Timer in background thread and if done it send email and stops timer
     */
    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sslEmailSender.send(SenderData.EMAIL);
                System.out.println("Message with timer was send");
                timer.cancel();
            }
        }, dateToSend);
    }
}
