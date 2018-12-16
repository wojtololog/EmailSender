package com.intern.gui.controllers;

import com.intern.email.EmailTimer;
import com.intern.email.SSLEmailSender;
import com.intern.email.SenderData;
import com.intern.exceptions.AppException;
import com.intern.exceptions.ExceptionMessages;
import com.intern.model.AttachmentParameters;
import com.intern.model.Message;
import com.intern.model.Recipients;
import com.intern.parsers.MessageParser;
import com.intern.parsers.RecipientsParser;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class PaneController {
    @FXML
    private Button recipientsButton, messageButton, sendButton, attachmentButton, tooglePickersButton;
    @FXML
    private Label recipientsLabel,messageLabel, logLabel, attachmentLabel;
    @FXML
    private Pane mainWindow;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;

    private FileChooser fileChooser;
    private Stage stage;

    private MessageParser messageParser;
    private RecipientsParser recipientsParser;
    private AttachmentParameters attachmentParameters;

    private boolean isMessageFileParsed, isRecipientsFileParsed;

    @FXML
    void initialize() {
        setButtonLabels();
        setInitialLabelsValue();
        logLabel.setText("Welcome to EmailSender !");
        isMessageFileParsed = false;
        isRecipientsFileParsed = false;
        hideDateAndTimePickers();
    }

    private void hideDateAndTimePickers() {
        datePicker.setVisible(false);
        timePicker.setVisible(false);
        datePicker.setValue(null);
        timePicker.setValue(null);
    }

    private void showDateAndTimePickers() {
        datePicker.setVisible(true);
        timePicker.setVisible(true);
    }

    private void setButtonLabels() {
        recipientsButton.setText("Add recipients");
        messageButton.setText("Add content of message");
        sendButton.setText("Send");
        attachmentButton.setText("Add attachment");
        tooglePickersButton.setText("Toogle pickers");
    }

    private void setInitialLabelsValue() {
        recipientsLabel.setText("Choose txt file with recipients");
        messageLabel.setText("Choose txt file with message content");
        attachmentLabel.setText("Choose file as attachment");
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
    public void onMouseAttachmentButtonClicked() {
        stage = (Stage) mainWindow.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file as attachment");
        File attachment = fileChooser.showOpenDialog(stage);
        if(attachment != null) {
            attachmentParameters = new AttachmentParameters(attachment.getAbsolutePath(), attachment.getName());
            attachmentLabel.setText("File selected: " + attachment.getAbsolutePath());
            logLabel.setText("Attachment added to message !");
        }
    }

    @FXML
    public void onMouseToogleButtonClicked() {
        if(datePicker.isVisible() && timePicker.isVisible()) {
            hideDateAndTimePickers();
        } else {
            showDateAndTimePickers();
        }
    }

    @FXML
    public void onMouseSendButtonClicked() {
        if(isRecipientsFileParsed && isMessageFileParsed) {
            Recipients recipients = recipientsParser.getRecipients();
            Message message = messageParser.getMessage();
            Date dateToSend;
            try {
                dateToSend = createDate(datePicker.getValue(), timePicker.getValue());
                SSLEmailSender sslEmailSender = new SSLEmailSender(message, recipients, attachmentParameters);
                if(dateToSend != null) {
                    EmailTimer emailTimer = new EmailTimer(sslEmailSender, dateToSend);
                    emailTimer.start();
                } else {
                    sslEmailSender.send(SenderData.EMAIL);
                }
                logLabel.setText("Message was send successfully !");
                isMessageFileParsed = false;
                isRecipientsFileParsed = false;
                attachmentParameters = null;
                setInitialLabelsValue();
            } catch (AppException e) {
                logLabel.setText(e.getMessage());
                e.printStackTrace();
            }
        } else {
            logLabel.setText("Please add files with recipients and message !");
        }
    }

    private Date createDate(LocalDate localDate, LocalTime localTime) throws AppException {
        if(localDate == null && localTime == null) {
            return null;
        } else if(localDate == null || localTime == null) {
            throw new AppException(ExceptionMessages.WRONG_SET_OF_DATETIME);
        } else {
            LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
            Date out = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            return out;
        }
    }
}
