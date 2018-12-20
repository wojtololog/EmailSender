package com.intern.email;

/**
 * class which holds Email address and password of sender (hard coded)
 */
public class SenderData {
    /**
     * Email of sender
     */
    public static final String EMAIL = "ppwj.andrzejkowalski@gmail.com";
    /**
     * password to account
     */
    public static final String PASSWORD = "WojciecHl@c69";

    /**
     * it split EMAIL by "@" for example abc@com.mail; it returns abc
     * @return username
     */
    public static String getUserNameFromEmail() {
        String[] splittedEmail = EMAIL.split("@");
        return splittedEmail[0];
    }
}
