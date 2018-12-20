package com.intern.model;

/**
 * It holds parsed from .txt file e-mail message subject and content
 */
public class Message {
    /**
     * subject of parsed message
     */
    private String subject;
    /**
     * content of parsed message
     */
    private String content;

    /**
     * Initialize class fields
     * @param subject subject of parsed message
     * @param content content of parsed message
     */
    public Message(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    /**
     *
     * @return subject of parsed message
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @return content of parsed message
     */
    public String getContent() {
        return content;
    }
}
