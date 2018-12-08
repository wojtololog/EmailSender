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
    public void fullMessageParsing() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/message/fullMessage.txt");
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
    public void emptyMessageParsing() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/message/emptyMessage.txt");
        MessageParser messageParser = new MessageParser(targetStream);
        messageParser.parse();
    }

    @Test(expected = ParsingException.class)
    public void noContentInMessage() throws ParsingException {
        InputStream targetStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("parsers/message/noContentMessage.txt");
        MessageParser messageParser = new MessageParser(targetStream);
        messageParser.parse();
    }

}
