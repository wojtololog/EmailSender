package com.intern.parsers;

import com.intern.exceptions.AppException;
import com.intern.exceptions.ExceptionMessages;
import com.intern.model.Message;
import com.sun.mail.iap.ParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class which parses e-mail message subject with content in .txt format
 */
public class MessageParser {
    /**
     * It holds text file as input stream.
     * @see InputStream
     */
    private InputStream inputWithMessage;

    /**
     * Instance of class Message which holds message subject and content
     */
    private Message message;

    /**
     * Primary constructor
     * @param inputWithMessage input stream which is created from text file
     */
    public MessageParser(InputStream inputWithMessage) {
        this.inputWithMessage = inputWithMessage;
    }

    /**
     * Parse input stream with message and create new Message instance
     * @throws AppException throws exceptions
     * @see ExceptionMessages
     */
    public void parse() throws AppException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputWithMessage));
        try {
            String nextLine = bufferedReader.readLine();
            if (nextLine == null) {
                throw new AppException(ExceptionMessages.EMPTY_FILE);
            }
            String parsedSubject = nextLine.trim();

            nextLine = bufferedReader.readLine();
            if (nextLine == null) {
                throw new AppException(ExceptionMessages.NO_SUBJECT);
            }

            StringBuilder stringBuilder = new StringBuilder();
            while (nextLine != null) {
                stringBuilder.append(nextLine).append("\n");
                nextLine = bufferedReader.readLine();
            }
            message = new Message(parsedSubject, stringBuilder.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return saved Message instance
     */
    public Message getMessage() {
        return message;
    }
}
