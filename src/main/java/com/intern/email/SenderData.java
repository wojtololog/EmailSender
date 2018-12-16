package com.intern.email;

public class SenderData {
    public static final String EMAIL = "ppwj.andrzejkowalski@gmail.com";
    public static final String PASSWORD = "WojciecHl@c69";

    public static String getUserNameFromEmail() {
        String[] splittedEmail = EMAIL.split("@");
        return splittedEmail[0];
    }
}
