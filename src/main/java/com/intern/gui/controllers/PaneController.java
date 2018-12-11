package com.intern.gui.controllers;

import com.intern.email.SSLEmailSender;
import com.intern.exceptions.AppException;
import com.intern.model.Message;
import com.intern.model.Recipients;
import com.intern.parsers.MessageParser;
import com.intern.parsers.RecipientsParser;
import com.sun.mail.iap.ParsingException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PaneController {
    @FXML
    private Button recipientsButton;
    @FXML
    private Button messageButton;
    @FXML
    private Button sendButton;
    @FXML
    private Label recipientsLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private Label logLabel;
    @FXML
    private Pane mainWindow;

    private FileChooser fileChooser;
    private Stage stage;

    private MessageParser messageParser;
    private RecipientsParser recipientsParser;

    private boolean isMessageFileParsed, isRecipientsFileParsed;

    @FXML
    void initialize() {
        recipientsButton.setText("Add recipients");
        messageButton.setText("Add content of message");
        sendButton.setText("Send");
        resetLabels();
        logLabel.setText("Welcome to EmailSender !");
        isMessageFileParsed = false;
        isRecipientsFileParsed = false;
    }

    private void resetLabels() {
        recipientsLabel.setText("Choose txt file with recipients");
        messageLabel.setText("Choose txt file with message content");
    }

    @FXML
    public void onMouseMessageButtonClicked() {
        stage = (Stage) mainWindow.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File with message");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            try {
                messageParser = new MessageParser(new FileInputStream(file));
                messageParser.parse();
                isMessageFileParsed = true;
                messageLabel.setText("File selected: " + file.getName());
                logLabel.setText("File with message successfully added !");
            } catch (FileNotFoundException | AppException e) {
                logLabel.setText(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onMouseRecipientsButtonClicked() {
        stage = (Stage) mainWindow.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file with recipients");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            try {
                recipientsParser = new RecipientsParser(new FileInputStream(file));
                recipientsParser.parse();
                isRecipientsFileParsed = true;
                recipientsLabel.setText("File selected: " + file.getName());
                logLabel.setText("File with recipients successfully added !");
            } catch (FileNotFoundException | AppException e) {
                logLabel.setText(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onMouseSendButtonClicked() {
        if(isRecipientsFileParsed && isMessageFileParsed) {
            Recipients recipients = recipientsParser.getRecipients();
            Message message = messageParser.getMessage();
            SSLEmailSender sslEmailSender = new SSLEmailSender(message, recipients);
            sslEmailSender.send("ppwjj.andrzejkowalski@gmail.com");
            logLabel.setText("Message was send successfully !");
            isMessageFileParsed = false;
            isRecipientsFileParsed = false;
            resetLabels();
        } else {
            logLabel.setText("Please add files with recipients and message !");
        }
    }
}
