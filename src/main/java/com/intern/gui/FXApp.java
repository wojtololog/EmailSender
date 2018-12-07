package com.intern.gui;

import com.intern.email.SSLEmailSender;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new Pane(),800, 600));
        stage.setTitle("Hallo Koty!");
        stage.show();
    }

    public static void main(String[] args) {
        SSLEmailSender sslEmailSender = new SSLEmailSender();
        sslEmailSender.send("ppwjj.andrzejkowalski@gmail.com", "wojtololog@gmail.com");
        launch(args);
    }
}
