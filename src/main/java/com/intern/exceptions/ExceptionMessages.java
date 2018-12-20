package com.intern.exceptions;

/**
 * It stores all messages which app throws
 */
public class ExceptionMessages {
    /**
     * Message is showed when parsed file is empty
     */
    public static final String EMPTY_FILE = "File is empty !";
    /**
     * Message is showed when parsed file with message does not contain subject in first line
     */
    public static final String NO_SUBJECT = "File doesn't contain message subject !";
    /**
     * Message is showed when parsed file with recipients does is formatted in wrong way (for example does not contain ";" to separate recipient type from its mail address)
     */
    public static final String WRONG_RECIPIENT_FORMAT = "Recipient type with email is formatted in wrong way !";
    /**
     * Message is showed when user pick only delivery date or time but has to pick both
     */
    public static final String WRONG_SET_OF_DATETIME = "Please pick message delivery date AND time !";
}
