package com.intern.email;

import com.intern.model.IntervalParameters;

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
     * it stores sending interval parameters like how many message to send and time interval in seconds
     */
    private IntervalParameters intervalParameters;

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
     * optional constructor; used when user activate interval parameters
     * @param sslEmailSender data with mail to send
     * @param dateToSend delivery message date
     * @param intervalParameters how many message to send with interval
     */
    public EmailTimer(SSLEmailSender sslEmailSender, Date dateToSend, IntervalParameters intervalParameters) {
        this.sslEmailSender = sslEmailSender;
        this.dateToSend = dateToSend;
        this.intervalParameters = intervalParameters;
        timer = new Timer();
    }

    /**
     * start counting to dateToSend by Timer in background thread and if done it send email and stops timer
     */
    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(intervalParameters != null) {
                    for(int i = 0; i < intervalParameters.getRepetitions(); i++) {
                        sslEmailSender.send(SenderData.EMAIL);
                        try {
                            Thread.sleep((long)(intervalParameters.getIntervalInSeconds() * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    sslEmailSender.send(SenderData.EMAIL);
                }
                timer.cancel();
            }
        }, dateToSend);
    }
}
