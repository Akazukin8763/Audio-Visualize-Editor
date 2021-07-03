package com.visualize.gui;

import com.visualize.view.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javafx.scene.text.Font;
import javafx.geometry.HPos;

import javafx.collections.FXCollections;

public class ParamUI extends ScrollPane {

    private final double width;
    private final double height;

    private final GridPane paramPane;
    private final GridPane groupEqualizer;
    private final GridPane groupFreq;

    private static final double WIDTH_OFFSET_LEFT = .35;
    private static final double WIDTH_OFFSET_MIDDLE = .50;
    private static final double WIDTH_OFFSET_RIGHT = .13;
    private static final int[][] GROUP_EQUALIZER_INDEX = {{0, 1, 2, 3}, {0, 1, 2, 4}, {0, 1, 2, 3}};

    private static final VisualizeMode.View[] EQUALIZER_TYPE = {VisualizeMode.View.LINE, VisualizeMode.View.CIRCLE, VisualizeMode.View.ANALOGY};
    private static final VisualizeMode.Side[] EQUALIZER_SIDE = {VisualizeMode.Side.OUT, VisualizeMode.Side.IN, VisualizeMode.Side.BOTH};
    private static final VisualizeMode.Direct[] EQUALIZER_DIRECTION = {VisualizeMode.Direct.NORMAL, VisualizeMode.Direct.INVERSE};
    private static final VisualizeMode.Stereo[] EQUALIZER_STEREO = {VisualizeMode.Stereo.SINGLE, VisualizeMode.Stereo.LEFT, VisualizeMode.Stereo.RIGHT, VisualizeMode.Stereo.BOTH};

    public ParamUI(double width, double height) {
        this.width = width;
        this.height = height;

        paramPane = new GridPane();
        paramPane.setPrefWidth(width);
        setContent(paramPane);
        setPrefSize(width, height);

        paramPane.setHgap(10);
        paramPane.setVgap(10);
        //paramPane.setGridLinesVisible(true);
        paramPane.setStyle("-fx-background-color: transparent");

        // Title
        Label labelEqualizerTitle = new Label("Equalizer");
        labelEqualizerTitle.setFont(new Font(20));
        paramPane.add(labelEqualizerTitle, 0, 0, 3, 1);
        GridPane.setHalignment(labelEqualizerTitle, HPos.CENTER);

        Label labelBackgroundTitle = new Label("Background");
        labelBackgroundTitle.setFont(new Font(20));
        paramPane.add(labelBackgroundTitle, 0, 8, 3, 1);
        GridPane.setHalignment(labelBackgroundTitle, HPos.CENTER);

        // Selection
        Label labelEqualizerType = new Label("Equalizer Type");
        ChoiceBox<VisualizeMode.View> choiceBoxEqualizerType = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_TYPE));
        labelEqualizerType.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerType.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerType, 0, 1);
        paramPane.add(choiceBoxEqualizerType, 1, 1);

        Label labelEqualizerSide = new Label("Side");
        ChoiceBox<VisualizeMode.Side> choiceBoxEqualizerSide = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_SIDE));
        labelEqualizerSide.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerSide.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerSide, 0, 5);
        paramPane.add(choiceBoxEqualizerSide, 1, 5);

        Label labelEqualizerDirection = new Label("Direction");
        ChoiceBox<VisualizeMode.Direct> choiceBoxEqualizerDirection = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_DIRECTION));
        labelEqualizerDirection.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerDirection.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerDirection, 0, 6);
        paramPane.add(choiceBoxEqualizerDirection, 1, 6);

        Label labelEqualizerStereo = new Label("Stereo");
        ChoiceBox<VisualizeMode.Stereo> choiceBoxEqualizerStereo = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_STEREO));
        labelEqualizerStereo.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerStereo.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerStereo, 0, 7);
        paramPane.add(choiceBoxEqualizerStereo, 1, 7);

        Label labelCustomFreq = new Label("Customize Frequency");
        CheckBox checkBoxCustomFreq = new CheckBox();
        labelCustomFreq.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        //checkBoxCustomFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        GridPane.setHalignment(checkBoxCustomFreq, HPos.RIGHT);
        paramPane.add(labelCustomFreq, 0, 3, 2, 1);
        paramPane.add(checkBoxCustomFreq, 1, 3);

        // Part
        Label labelBarNum = new Label("Bar Number");
        Slider sliderBarNum = new Slider();
        TextField textFieldBarNum = new TextField();
        labelBarNum.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBarNum.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBarNum.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderBarNum.setMax(256);

        Label labelSize = new Label("Size");
        Slider sliderSize = new Slider();
        TextField textFieldSize = new TextField();
        labelSize.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSize.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldSize.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRotation = new Label("Rotation");
        Slider sliderRotation = new Slider();
        TextField textFieldRotation = new TextField();
        labelRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRotation.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelGap = new Label("Gap");
        Slider sliderGap = new Slider();
        TextField textFieldGap = new TextField();
        labelGap.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderGap.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldGap.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRadius = new Label("Radius");
        Slider sliderRadius = new Slider();
        TextField textFieldRadius = new TextField();
        labelRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelMinFreq = new Label("Min Frequency");
        Slider sliderMinFreq = new Slider();
        TextField textFieldMinFreq = new TextField();
        labelMinFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderMinFreq.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldMinFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelMaxFreq = new Label("Max Frequency");
        Slider sliderMaxFreq = new Slider();
        TextField textFieldMaxFreq = new TextField();
        labelMaxFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderMaxFreq.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldMaxFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // Group
        groupEqualizer = new GridPane();
        groupEqualizer.setHgap(10);
        groupEqualizer.setVgap(5);
        paramPane.add(groupEqualizer, 0, 2, 2, 1);

        HBox groupBarNum = new HBox(labelBarNum, sliderBarNum, textFieldBarNum);
        HBox groupSize = new HBox(labelSize, sliderSize, textFieldSize);
        HBox groupRotation = new HBox(labelRotation, sliderRotation, textFieldRotation);
        HBox groupGap = new HBox(labelGap, sliderGap, textFieldGap);
        HBox groupRadius = new HBox(labelRadius, sliderRadius, textFieldRadius);
        HBox[] groupEqualizerParam = new HBox[] {groupBarNum, groupSize, groupRotation, groupGap, groupRadius};

        groupFreq = new GridPane();
        groupFreq.setHgap(10);
        groupFreq.setVgap(5);
        paramPane.add(groupFreq, 0, 4, 2, 1);

        HBox groupMinFreq = new HBox(labelMinFreq, sliderMinFreq, textFieldMinFreq);
        HBox groupMaxFreq = new HBox(labelMaxFreq, sliderMaxFreq, textFieldMaxFreq);
        HBox[] groupFreqParam = new HBox[] {groupMinFreq, groupMaxFreq};

        // Event
        // └ this
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setOnMouseEntered(event -> this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED));
        this.setOnMouseExited(event -> this.setVbarPolicy(ScrollBarPolicy.NEVER));
        // └ Choice Box
        //  └ Equalizer Type
        choiceBoxEqualizerType.setOnAction(event -> {
            VisualizeMode.View type = choiceBoxEqualizerType.getValue();

            groupEqualizer.getChildren().clear(); // 清空所有可能選單
            int groupRow = 0;
            for (int index: GROUP_EQUALIZER_INDEX[type.value()])
                groupEqualizer.add(groupEqualizerParam[index], 0, groupRow++, 2, 1);
        });
        //  └ Equalizer Direction
        choiceBoxEqualizerSide.setOnAction(event -> System.out.println(choiceBoxEqualizerSide.getValue()));
        //  └ Equalizer Inverse
        choiceBoxEqualizerDirection.setOnAction(event -> System.out.println(choiceBoxEqualizerDirection.getValue()));
        //  └ Equalizer Stereo
        choiceBoxEqualizerStereo.setOnAction(event -> System.out.println(choiceBoxEqualizerStereo.getValue()));
        // └ Check Box
        //  └ Custom Frequency
        checkBoxCustomFreq.setOnAction(event -> {
            groupFreq.getChildren().clear(); // 清空所有可能選單
            if (checkBoxCustomFreq.isSelected()) {
                int groupRow = 0;
                for (HBox param: groupFreqParam)
                    groupFreq.add(param, 0, groupRow++, 2, 1);
            }
        });
        // └ Slider
        //  └ Bar Number
        sliderBarNum.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderBarNum.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderBarNum.getMax() * 100));
            textFieldBarNum.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldBarNum.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBarNum.getText(), (int) sliderBarNum.getMax());
            sliderBarNum.valueProperty().setValue(value);
            textFieldBarNum.textProperty().setValue(String.format("%d", value));
        });
        //  └ Size
        sliderSize.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderSize.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldSize.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldSize.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSize.getText(), (int) sliderSize.getMax());
            sliderSize.valueProperty().setValue(value);
            textFieldSize.textProperty().setValue(String.format("%d", value));
        });
        //  └ Rotation
        sliderRotation.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRotation.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldRotation.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldRotation.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRotation.getText(), (int) sliderRotation.getMax());
            sliderRotation.valueProperty().setValue(value);
            textFieldRotation.textProperty().setValue(String.format("%d", value));
        });
        //  └ Gap
        sliderGap.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderGap.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldGap.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldGap.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldGap.getText(), (int) sliderGap.getMax());
            sliderGap.valueProperty().setValue(value);
            textFieldGap.textProperty().setValue(String.format("%d", value));
        });
        //  └ Radius
        sliderRadius.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRadius.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldRadius.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRadius.getText(), (int) sliderRadius.getMax());
            sliderRadius.valueProperty().setValue(value);
            textFieldRadius.textProperty().setValue(String.format("%d", value));
        });
        //  └ Min Frequency
        sliderMinFreq.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderMinFreq.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldMinFreq.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldMinFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMinFreq.getText(), (int) sliderMinFreq.getMax());
            sliderMinFreq.valueProperty().setValue(value);
            textFieldMinFreq.textProperty().setValue(String.format("%d", value));
        });
        //  └ Max Frequency
        sliderMaxFreq.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderMaxFreq.lookup(".track").setStyle(sliderTrackStyle(newValue.intValue()));
            textFieldMaxFreq.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldMaxFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMaxFreq.getText(), (int) sliderMaxFreq.getMax());
            sliderMaxFreq.valueProperty().setValue(value);
            textFieldMaxFreq.textProperty().setValue(String.format("%d", value));
        });

        // Initialize
        choiceBoxEqualizerType.setValue(EQUALIZER_TYPE[0]); // Line
        choiceBoxEqualizerSide.setValue(EQUALIZER_SIDE[0]); // Out
        choiceBoxEqualizerDirection.setValue(EQUALIZER_DIRECTION[0]); // Normal
        choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[3]); // Both
    }

    // Methods
    private String sliderTrackStyle(double value) {
        return String.format("-fx-background-color: linear-gradient(to right, lightseagreen %f%%, #444444 %f%%);", value, value);
    }
    private int textFieldStringToInt(String value, int max) {
        int result;
        try {
            result = Math.min(Integer.parseInt(value), max);
        } catch (NumberFormatException ignored) {
            result = 0;
        }
        return result;
    }

}
