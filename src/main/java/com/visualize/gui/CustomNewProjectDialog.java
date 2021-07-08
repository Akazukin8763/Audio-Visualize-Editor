package com.visualize.gui;

import com.visualize.file.*;

import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;

import javafx.scene.image.Image;

import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

import javafx.collections.FXCollections;

import javafx.util.Pair;

import java.io.File;

public class CustomNewProjectDialog extends Stage {

    private static final int WIDTH = 500;

    private static final SizePair[] SIZE = new SizePair[]{new SizePair(1920, 1080),
                                                            new SizePair(1440, 1080)};

    // Constructor
    public CustomNewProjectDialog() {
        super();

        GridPane dialog = new GridPane();
        dialog.setPrefWidth(WIDTH);
        //dialog.setGridLinesVisible(true);
        dialog.setPadding(new Insets(0, 20, 0, 20));
        dialog.setStyle("-fx-background-color: #333333; -fx-border-color: #444444");

        // Title
        Label labelProject = new Label("Project Name:   ");
        TextField textFieldProject = new TextField();
        textFieldProject.setPromptText("Name");
        textFieldProject.setPrefWidth(WIDTH * .25);
        HBox hBoxProject = new HBox(labelProject, textFieldProject);
        hBoxProject.setPrefWidth(WIDTH);
        hBoxProject.setAlignment(Pos.CENTER);
        hBoxProject.setPadding(new Insets(10, 0, 10, 0));
        dialog.add(hBoxProject, 0, 0, 2, 1);
        GridPane.setHalignment(hBoxProject, HPos.CENTER);

        // Setting
        GridPane setting = new GridPane();
        setting.setPrefWidth(WIDTH * .9);
        setting.setPadding(new Insets(5, 5, 5, 5));
        setting.setVgap(5);
        setting.setStyle("-fx-border-color: #444444");
        dialog.add(setting, 0, 1, 2, 1);
        GridPane.setHalignment(setting, HPos.CENTER);

        // Finish
        Label labelFinishNull1 = new Label();
        Label labelFinishNull2 = new Label();
        Button buttonOK = new Button("OK");
        Button buttonCancel = new Button("Cancel");
        labelFinishNull1.setPrefWidth(WIDTH * .7 - 20);
        labelFinishNull2.setPrefWidth(20);
        buttonOK.setPrefWidth(WIDTH * .15);
        buttonCancel.setPrefWidth(WIDTH * .15);
        HBox hBoxFinish = new HBox(labelFinishNull1, buttonOK, labelFinishNull2, buttonCancel);
        hBoxFinish.setPrefWidth(WIDTH);
        hBoxFinish.setPadding(new Insets(10, 0, 10, 0));
        dialog.add(hBoxFinish, 0, 2, 2, 1);

        // Part
        Label labelWidth = new Label("Width:   ");
        TextField textFieldWidth = new TextField("1920");
        setting.add(labelWidth, 0, 0);
        setting.add(textFieldWidth, 1, 0);

        Label labelHeight = new Label("Height:   ");
        TextField textFieldHeight = new TextField("1080");
        setting.add(labelHeight, 0, 1);
        setting.add(textFieldHeight, 1, 1);

        Label labelSize = new Label("Size:   ");
        ChoiceBox<SizePair> choiceBoxSize = new ChoiceBox<>(FXCollections.observableArrayList(SIZE));
        choiceBoxSize.setValue(SIZE[0]);
        //FXCollections.observableArrayList(SIZE).stream().map(size -> size.getKey() + " x " + size.getValue()).collect(Collectors.toList())
        HBox hBoxSize = new HBox(labelSize, choiceBoxSize);
        hBoxSize.setPrefWidth(WIDTH);
        hBoxSize.setAlignment(Pos.CENTER_RIGHT);
        setting.add(hBoxSize, 2, 0, 2, 2);
        GridPane.setHalignment(hBoxSize, HPos.RIGHT);

        // Combine
        Scene scene = new Scene(dialog);
        scene.getStylesheets().add(getClass().getResource("/style/CustomNewProjectDialog.css").toExternalForm());
        this.setScene(scene);
        this.setResizable(false);
        //this.setAlwaysOnTop(true);
        this.getIcons().add(new Image(new File(DefaultPath.ICON_PATH).toURI().toString()));

        // Event
        // └ Slider
        //  └ Size
        choiceBoxSize.setOnAction(event -> {
            SizePair size = choiceBoxSize.getValue();
            textFieldWidth.setText(String.format("%d", size.getKey()));
            textFieldHeight.setText(String.format("%d", size.getValue()));
        });

    }

    private static class SizePair extends Pair<Integer, Integer> {

        public SizePair(Integer i1, Integer i2) {
            super(i1, i2);
        }

        @Override
        public String toString() {
            return getKey() + " x " + getValue();
        }
    }

}
