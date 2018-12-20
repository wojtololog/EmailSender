package com.intern.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main class where GUI is started
 */
public class FXApp extends Application {
    /**
     * It makes all necessary things when start of app occurs
     * @param stage it is one of the element of GUI window content
     * @see Stage
     * @throws Exception throws exception when something with start goes wrong
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/StackPane.fxml"));
        Pane pane = fxmlLoader.load();

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("EmailSender");
        stage.show();
    }

    /**
     * Main method which launch application
     * @param args app launching arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
