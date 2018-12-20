package com.intern.parsers;

import com.intern.exceptions.AppException;
import com.intern.exceptions.ExceptionMessages;
import com.intern.model.Recipients;
import com.sun.mail.iap.ParsingException;

import java.io.*;

/**
 * Class which parses e-mail recipients list in .txt format
 */
public class RecipientsParser {
    /**
     * It holds text file as input stream.
     * @see InputStream
     */
    private InputStream inputWithRecipients;

    /**
     * Instance of class Recipients which holds lists of recipients types (TO, CC, BCC)
     */
    private Recipients recipients;

    /**
     * Primary constructor; also create new instance of Recipients
     * @param fileWithRecipients input stream which is created from text file
     */
    public RecipientsParser(InputStream fileWithRecipients) {
        this.inputWithRecipients = fileWithRecipients;
        recipients = new Recipients();
    }

    /**
     * Parse input stream with recipients and create new Recipients instance
     * @throws AppException throws exceptions
     * @see ExceptionMessages
     */
    public void parse() throws AppException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.inputWithRecipients));
        try {
            String nextLine = bufferedReader.readLine();
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

    /**
     * It adds new recipient by its type (TO, CC, BCC) to the list in Recipients
     * @param textLine contains actual parsing text line
     * @throws AppException throws exceptions
     * @see ExceptionMessages
     */
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

    /**
     *
     * @return get Recipients instance
     */
    public Recipients getRecipients() {
        return recipients;
    }
}
