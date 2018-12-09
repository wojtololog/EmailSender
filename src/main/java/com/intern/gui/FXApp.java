package com.intern.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FXApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/StackPane.fxml"));
        StackPane stackPane = fxmlLoader.load();

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setTitle("EmailSender");
        stage.show();
    }

    public static void main(String[] args) {
        //sslEmailSender.send("ppwjj.andrzejkowalski@gmail.com", "wojtololog@gmail.com");
        launch(args);
    }
}
