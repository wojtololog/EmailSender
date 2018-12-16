package com.intern.email;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmailTimer {
    private final SSLEmailSender sslEmailSender;
    private final Date dateToSend;

    private final Timer timer;

    public EmailTimer(SSLEmailSender sslEmailSender, Date dateToSend) {
        this.sslEmailSender = sslEmailSender;
        this.dateToSend = dateToSend;
        timer = new Timer();
    }

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
