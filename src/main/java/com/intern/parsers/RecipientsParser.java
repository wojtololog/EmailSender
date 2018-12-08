package com.intern.parsers;

import com.intern.model.Recipients;
import com.sun.mail.iap.ParsingException;

import java.io.*;

public class RecipientsParser {
    private InputStream inputWithRecipients;
    private BufferedReader bufferedReader;
    private String nextLine;

    private Recipients recipients;

    public RecipientsParser(InputStream fileWithRecipients) {
        this.inputWithRecipients = fileWithRecipients;
        recipients = new Recipients();
    }

    public void parse() throws ParsingException {
        bufferedReader = new BufferedReader(new InputStreamReader(this.inputWithRecipients));
        try {
            nextLine = bufferedReader.readLine();
            if (nextLine.isEmpty()) {
                throw new ParsingException();
            }
            addNewRecipient(nextLine);

            nextLine = bufferedReader.readLine();
            while(nextLine != null) {
                addNewRecipient(nextLine);
                nextLine = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewRecipient(String textLine) {
        String[] splittedLine = textLine.split(";");
        if(splittedLine.length != 2) {
            throw new IllegalArgumentException("Recipient type with email is formatted in wrong way!");
        }

        String recipientInfo = splittedLine[0].trim();
        String recipientEmail = splittedLine[1].trim();

        switch (recipientInfo) {
            case "DO":
                recipients.addToNormalSenders(recipientEmail);
                break;
            case "DW":
                recipients.addToCarbonCoby(recipientEmail);
                break;
            case "UDW":
                recipients.addToBlindCarbonCoby(recipientEmail);
                break;
            default:
                break;
        }
    }

    public Recipients getRecipients() {
        return recipients;
    }
}
