package com.intern.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private Pane mainWindow;

    private FileChooser fileChooser;
    private Stage stage;

    @FXML
    void initialize() {
        recipientsButton.setText("Add recipients");
        messageButton.setText("Add content of message");
        sendButton.setText("Send");

        recipientsLabel.setText("Choose txt file with recipients");
        messageLabel.setText("Choose txt file with message content");
    }

    @FXML
    public void onMouseMessageButtonClicked() {
        stage = (Stage) mainWindow.getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.showOpenDialog(stage);
    }

    @FXML
    public void onMouseRecipientsButtonClicked() {

    }

    @FXML
    public void onMouseSendButtonClicked() {

    }
}
