package parser;

import com.intern.parsers.MessageParser;
import com.sun.mail.iap.ParsingException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MessagesTest {

    @Test
    public void fullMessageParsing() throws FileNotFoundException, ParsingException {
        File initialFile = new File("src/test/resources/parsers/message/fullMessage.txt");
        InputStream targetStream = new FileInputStream(initialFile);
        MessageParser messageParser = new MessageParser(targetStream);
        messageParser.parse();
        String subject = messageParser.getMessage().getSubject();
        String content = messageParser.getMessage().getContent();

        Assert.assertEquals("Praca inzynierska", subject);
        Assert.assertEquals("Szanowni panstwo,\n" +
                "prosze pospieszyc sie z pisaniem pracy.\n" +
                "Pozdrawiam,\n" +
                "Wojciech Lacheta", content);
    }

    @Test(expected = ParsingException.class)
    public void emptyMessageParsing() throws FileNotFoundException, ParsingException {
        File initialFile = new File("src/test/resources/parsers/message/emptyMessage.txt");
        InputStream targetStream = new FileInputStream(initialFile);
        MessageParser messageParser = new MessageParser(targetStream);
        messageParser.parse();
    }

    @Test(expected = ParsingException.class)
    public void noContentInMessage() throws FileNotFoundException, ParsingException {
        File initialFile = new File("src/test/resources/parsers/message/noContentMessage.txt");
        InputStream targetStream = new FileInputStream(initialFile);
        MessageParser messageParser = new MessageParser(targetStream);
        messageParser.parse();
    }

}
