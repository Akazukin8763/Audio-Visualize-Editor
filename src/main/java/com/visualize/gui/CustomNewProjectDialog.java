package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;

import javafx.scene.image.Image;

import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

import javafx.collections.FXCollections;

import javafx.util.Pair;

import java.io.File;

public class CustomNewProjectDialog extends Stage {

    private static final int WIDTH = 500;

    private static final SizePair[] SIZE = new SizePair[]{
            new SizePair(1920, 1080),
            new SizePair(1680, 1050), new SizePair(1600, 900),
            new SizePair(1440, 900), new SizePair(1440, 1050),
            new SizePair(1366, 768),
            new SizePair(1360, 768),
            new SizePair(1280, 1024), new SizePair(1280, 960), new SizePair(1280, 800), new SizePair(1280, 768), new SizePair(1280, 600), new SizePair(1152, 864),
            new SizePair(1024, 768),
            new SizePair(800, 600)};

    private static final VisualizeMode.View[] EQUALIZER_TYPE = VisualizeMode.View.values();
    private static final VisualizeMode.Side[] EQUALIZER_SIDE = VisualizeMode.Side.values();
    private static final VisualizeMode.Direct[] EQUALIZER_DIRECTION = VisualizeMode.Direct.values();

    private CustomNewProjectDialogFormat format = null;

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
        labelWidth.setPrefWidth(WIDTH * .25);
        textFieldWidth.setPrefWidth(WIDTH * .25);
        labelWidth.setAlignment(Pos.CENTER_RIGHT);
        setting.add(labelWidth, 0, 0);
        setting.add(textFieldWidth, 1, 0);

        Label labelHeight = new Label("Height:   ");
        TextField textFieldHeight = new TextField("1080");
        labelHeight.setPrefWidth(WIDTH * .25);
        textFieldHeight.setPrefWidth(WIDTH * .25);
        labelHeight.setAlignment(Pos.CENTER_RIGHT);
        setting.add(labelHeight, 0, 1);
        setting.add(textFieldHeight, 1, 1);

        Label labelSize = new Label("Size:   ");
        ChoiceBox<SizePair> choiceBoxSize = new ChoiceBox<>(FXCollections.observableArrayList(SIZE));
        labelSize.setPrefWidth(WIDTH * .25);
        choiceBoxSize.setPrefWidth(WIDTH * .25);
        labelSize.setAlignment(Pos.CENTER_RIGHT);
        HBox hBoxSize = new HBox(labelSize, choiceBoxSize);
        hBoxSize.setPrefWidth(WIDTH);
        hBoxSize.setAlignment(Pos.CENTER_RIGHT);
        setting.add(hBoxSize, 2, 0, 2, 2);
        GridPane.setHalignment(hBoxSize, HPos.RIGHT);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(WIDTH);
        setting.add(separator1, 0, 2, 4, 1);

        Label labelEqualizerType = new Label("Equalizer Type:   ");
        ChoiceBox<VisualizeMode.View> choiceBoxEqualizerType = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_TYPE));
        labelEqualizerType.setPrefWidth(WIDTH * .25);
        choiceBoxEqualizerType.setPrefWidth(WIDTH * .25);
        labelEqualizerType.setAlignment(Pos.CENTER_RIGHT);
        setting.add(labelEqualizerType, 0, 3);
        setting.add(choiceBoxEqualizerType, 1, 3);

        Label labelEqualizerSide = new Label("Side:   ");
        ChoiceBox<VisualizeMode.Side> choiceBoxEqualizerSide = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_SIDE));
        labelEqualizerSide.setPrefWidth(WIDTH * .25);
        choiceBoxEqualizerSide.setPrefWidth(WIDTH * .25);
        labelEqualizerSide.setAlignment(Pos.CENTER_RIGHT);
        setting.add(labelEqualizerSide, 0, 4);
        setting.add(choiceBoxEqualizerSide, 1, 4);

        Label labelEqualizerDirection = new Label("Direction:   ");
        ChoiceBox<VisualizeMode.Direct> choiceBoxEqualizerDirection = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_DIRECTION));
        labelEqualizerDirection.setPrefWidth(WIDTH * .25);
        choiceBoxEqualizerDirection.setPrefWidth(WIDTH * .25);
        labelEqualizerDirection.setAlignment(Pos.CENTER_RIGHT);
        setting.add(labelEqualizerDirection, 0, 5);
        setting.add(choiceBoxEqualizerDirection, 1, 5);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(WIDTH);
        setting.add(separator2, 0, 6, 4, 1);

        Label labelBackgroundColor = new Label("Background Color:   ");
        ColorPicker colorPickerBackgroundColor = new ColorPicker();
        labelBackgroundColor.setPrefWidth(WIDTH * .25);
        colorPickerBackgroundColor.setPrefWidth(WIDTH * .1);
        labelBackgroundColor.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setHalignment(colorPickerBackgroundColor, HPos.RIGHT);
        setting.add(labelBackgroundColor, 0, 7);
        setting.add(colorPickerBackgroundColor, 1, 7);

        // Combine
        Scene scene = new Scene(dialog);
        scene.getStylesheets().add(getClass().getResource("/style/CustomNewProjectDialog.css").toExternalForm());
        this.setScene(scene);
        this.setResizable(false);
        //this.setAlwaysOnTop(true);
        this.initModality(Modality.APPLICATION_MODAL);
        this.getIcons().add(new Image(new File(DefaultPath.ICON_PATH).toURI().toString()));

        // Event
        // └ Choice Box
        //  └ Size
        choiceBoxSize.setOnAction(event -> {
            try {
                SizePair size = choiceBoxSize.getValue();
                textFieldWidth.setText(String.format("%d", size.getKey()));
                textFieldHeight.setText(String.format("%d", size.getValue()));
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
        textFieldWidth.textProperty().addListener((obs, oldValue, newValue) -> {
            int width = textFieldStringToInt(newValue);
            int height = textFieldStringToInt(textFieldHeight.getText());
            textFieldWidth.textProperty().setValue(String.format("%d", width));

            for (SizePair pair: SIZE) {
                if (pair.getKey() == width && pair.getValue() == height) {
                    choiceBoxSize.setValue(pair);
                    return;
                }
            }
            choiceBoxSize.getSelectionModel().clearSelection();
        });
        textFieldHeight.textProperty().addListener((obs, oldValue, newValue) -> {
            int width = textFieldStringToInt(textFieldWidth.getText());
            int height = textFieldStringToInt(newValue);
            textFieldHeight.textProperty().setValue(String.format("%d", height));

            for (SizePair pair: SIZE) {
                if (pair.getKey() == width && pair.getValue() == height) {
                    choiceBoxSize.setValue(pair);
                    return;
                }
            }
            choiceBoxSize.getSelectionModel().clearSelection();
        });
        // └ Button
        buttonOK.setOnAction(event -> {
            String projectName = textFieldProject.getText().trim();

            if (projectName.equals(""))
                textFieldProject.requestFocus();
            else {
                format = new CustomNewProjectDialogFormat(
                        projectName,
                        textFieldStringToInt(textFieldWidth.getText()), textFieldStringToInt(textFieldHeight.getText()),
                        choiceBoxEqualizerType.getValue(), choiceBoxEqualizerSide.getValue(), choiceBoxEqualizerDirection.getValue(),
                        colorPickerBackgroundColor.getValue());
                this.close();
            }
        });
        buttonCancel.setOnAction(event -> this.close());

        // Initialize
        choiceBoxSize.setValue(SIZE[0]); // 1920 x 1080
        choiceBoxEqualizerType.setValue(EQUALIZER_TYPE[0]); // Line
        choiceBoxEqualizerSide.setValue(EQUALIZER_SIDE[0]); // Out
        choiceBoxEqualizerDirection.setValue(EQUALIZER_DIRECTION[0]); // Normal

    }

    // Methods
    public CustomNewProjectDialogFormat showAndReturn() {
        super.showAndWait();
        return format;
    }

    private int textFieldStringToInt(String value) {
        int result;
        try {
            result = Math.max(Integer.parseInt(value), 0);
        } catch (NumberFormatException ignored) {
            result = 0;
        }
        return result;
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
