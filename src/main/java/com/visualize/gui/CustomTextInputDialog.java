package com.visualize.gui;

import com.visualize.file.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.HPos;
import javafx.geometry.Insets;

import java.io.File;

public class CustomTextInputDialog extends Stage {

    private static final int WIDTH = 200;
    private String input = null;

    // Constructor
    public CustomTextInputDialog() {
        super();

        GridPane gridPane = new GridPane();
        gridPane.setPrefWidth(WIDTH);
        gridPane.setStyle("-fx-background-color: #333333; -fx-border-color: #444444");

        // Title
        Label labelTitle = new Label("Dialog");
        labelTitle.setPadding(new Insets(5, 0, 5, 0));
        gridPane.add(labelTitle, 0, 0, 2, 1);
        GridPane.setHalignment(labelTitle, HPos.CENTER);

        // Input
        ImageView imageView = new ImageView(new Image(new File(DefaultPath.MUSIC_ICON_PATH).toURI().toString()));
        gridPane.add(imageView, 0, 1);

        TextField textField = new TextField();
        textField.setPrefWidth(WIDTH);
        textField.setPromptText("Name");
        gridPane.add(textField, 1, 1);

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(getClass().getResource("/style/CustomTextInputDialog.css").toExternalForm());
        this.setScene(scene);
        this.initStyle(StageStyle.UNDECORATED);
        this.setResizable(false);

        // Event
        // └ If not focused, close
        this.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue)
                this.close();
        });
        // └ Title
        this.titleProperty().addListener((obs, oldValue, newValue) -> labelTitle.setText(newValue));
        // └ Text Enter
        textField.setOnAction(event -> {
            input = textField.getText();
            this.close();
        });
    }

    // Methods
    public String showAndReturn() {
        super.showAndWait();
        return input;
    }

}
