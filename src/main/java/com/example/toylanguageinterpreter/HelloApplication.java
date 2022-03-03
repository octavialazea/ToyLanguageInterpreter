package com.example.toylanguageinterpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {

        // Read file fxml and draw interface.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainLayout.fxml")));
        Scene scene = new Scene(root, 1292, 632, Color.DARKBLUE);
        mainStage.setTitle("Toy Language - Please select a program");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}