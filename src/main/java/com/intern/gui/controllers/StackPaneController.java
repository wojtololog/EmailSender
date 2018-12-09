package com.intern.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StackPaneController {
    @FXML
    private Button exampleButton;

    @FXML
    void initialize() {
        exampleButton.setText("Halo Koty!");
    }
}
