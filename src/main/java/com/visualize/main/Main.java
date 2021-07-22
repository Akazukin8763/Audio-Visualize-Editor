package com.visualize.main;

//import com.visualize.Jar;
import com.visualize.gui.*;
import com.visualize.file.*;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;

import javafx.scene.Scene;
import javafx.scene.image.Image;

import javafx.geometry.Rectangle2D;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Stage stage = new Stage();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Scene scene = new Scene(new AudioVisualizeUI(bounds.getWidth(), bounds.getHeight()));
        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image(new File(DefaultPath.ICON_PATH).toURI().toString()));
        //stage.getIcons().add(new javafx.scene.image.Image(new java.io.File(Jar.getJarPath() + "/icon/icon.png").toURI().toString()));
        //stage.setMaximized(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
