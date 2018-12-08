package parser;

import com.intern.parsers.RecipientsParser;
import com.sun.mail.iap.ParsingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecipientsTest {

    @Test
    public void singleRecipientParse() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/recipients/singleRecipient.txt");
        RecipientsParser recipientsParser = new RecipientsParser(targetStream);
        recipientsParser.parse();
        ArrayList<String> toRecipients = recipientsParser.getRecipients().getNormalSenders();

        Assert.assertEquals("wojtololog@gmail.com", toRecipients.get(0));
    }

    @Test
    public void singleRecipientWithSpaceParse() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/recipients/singleRecipientWithSpace.txt");
        RecipientsParser recipientsParser = new RecipientsParser(targetStream);
        recipientsParser.parse();
        ArrayList<String> toRecipients = recipientsParser.getRecipients().getNormalSenders();

        Assert.assertEquals("rutkowski@detektyw.pl", toRecipients.get(0));
    }

    @Test
    public void multipleRecipientParse() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/recipients/multipleRecipient.txt");
        RecipientsParser recipientsParser = new RecipientsParser(targetStream);
        recipientsParser.parse();
        ArrayList<String> doRecipients = recipientsParser.getRecipients().getNormalSenders();
        ArrayList<String> dwRecipients = recipientsParser.getRecipients().getCarbonCopy();
        ArrayList<String> udwRecipients = recipientsParser.getRecipients().getBlindCarbonCopy();

        Assert.assertEquals("wojtololog@gmail.com", doRecipients.get(0));
        Assert.assertEquals("dariuszxx@interia.pl", doRecipients.get(1));

        Assert.assertEquals("maciejknot@xxx.pl", dwRecipients.get(1));

        Assert.assertEquals("pbednarz@trol.pl", udwRecipients.get(1));
    }
}
