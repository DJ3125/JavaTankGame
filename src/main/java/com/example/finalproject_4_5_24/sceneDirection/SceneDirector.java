package com.example.finalproject_4_5_24.sceneDirection;

import com.example.finalproject_4_5_24.controllerAndInput.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public final class SceneDirector extends Application {
    @Override public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneDirector.class.getResource("game-scene.fxml"));
        File x = new File("src/main/resources/com/example/finalproject_4_5_24/game-scene.fxml");
        fxmlLoader.setLocation(x.toURI().toURL());
        Scene scene = new Scene(fxmlLoader.load());
        ((GameController)fxmlLoader.getController()).initializeScene();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}