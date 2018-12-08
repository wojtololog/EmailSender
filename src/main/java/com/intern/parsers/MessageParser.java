package com.intern.parsers;

import com.intern.model.Message;
import com.sun.mail.iap.ParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MessageParser {
    private InputStream inputWithMessage;
    private BufferedReader bufferedReader;
    private String nextLine;
    private StringBuilder stringBuilder;

    private Message message;

    public MessageParser(InputStream inputWithMessage) {
        this.inputWithMessage = inputWithMessage;
    }

    public void parse() throws ParsingException {
        bufferedReader = new BufferedReader(new InputStreamReader(this.inputWithMessage));
        try {
            nextLine = bufferedReader.readLine();
            if (nextLine == null) {
                throw new ParsingException();
            }
            String parsedSubject = nextLine.trim();

            nextLine = bufferedReader.readLine();
            if (nextLine == null) {
                throw new ParsingException();
            }

            stringBuilder = new StringBuilder();
            while (nextLine != null) {
                stringBuilder.append(nextLine).append("\n");
                nextLine = bufferedReader.readLine();
            }
            message = new Message(parsedSubject, stringBuilder.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message getMessage() {
        return message;
    }
}
