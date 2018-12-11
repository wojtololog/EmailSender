package com.intern.parsers;

import com.intern.exceptions.AppException;
import com.intern.exceptions.ExceptionMessages;
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

    public void parse() throws AppException {
        bufferedReader = new BufferedReader(new InputStreamReader(this.inputWithRecipients));
        try {
            nextLine = bufferedReader.readLine();
            if (nextLine == null) {
                throw new AppException(ExceptionMessages.EMPTY_FILE);
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

    private void addNewRecipient(String textLine) throws AppException {
        String[] splittedLine = textLine.split(";");
        if(splittedLine.length != 2) {
            throw new AppException(ExceptionMessages.WRONG_RECIPIENT_FORMAT);
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
