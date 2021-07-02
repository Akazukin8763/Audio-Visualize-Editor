package com.visualize.gui;

import com.visualize.view.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

import javafx.scene.text.Font;
import javafx.geometry.HPos;

import javafx.collections.FXCollections;

public class ParamUI extends ScrollPane {

    private final double width;
    private final double height;

    private final GridPane paramPane;

    private final GridPane groupEqualizer;

    private static final double WIDTH_OFFSET_LEFT = .35;
    private static final double WIDTH_OFFSET_RIGHT = .65;
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
        paramPane.setStyle("-fx-background-color: lightgray");
        setContent(paramPane);
        setPrefSize(width, height);

        paramPane.setHgap(10);
        paramPane.setVgap(10);
        //paramPane.setGridLinesVisible(true);
        paramPane.setStyle("-fx-background-color: transparent");

        // Title
        Label labelEqualizerTitle = new Label("Equalizer");
        labelEqualizerTitle.setFont(new Font(20));
        paramPane.add(labelEqualizerTitle, 0, 0, 2, 1);
        GridPane.setHalignment(labelEqualizerTitle, HPos.CENTER);

        Label labelBackgroundTitle = new Label("Background");
        labelBackgroundTitle.setFont(new Font(20));
        paramPane.add(labelBackgroundTitle, 0, 6, 2, 1);
        GridPane.setHalignment(labelBackgroundTitle, HPos.CENTER);

        // Selection
        Label labelEqualizerType = new Label("Equalizer type");
        ChoiceBox<VisualizeMode.View> choiceBoxEqualizerType = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_TYPE));
        labelEqualizerType.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerType.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        paramPane.add(labelEqualizerType, 0, 1);
        paramPane.add(choiceBoxEqualizerType, 1, 1);

        Label labelEqualizerDirection = new Label("Direction");
        ChoiceBox<VisualizeMode.Side> choiceBoxEqualizerDirection = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_SIDE));
        labelEqualizerDirection.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerDirection.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        paramPane.add(labelEqualizerDirection, 0, 3);
        paramPane.add(choiceBoxEqualizerDirection, 1, 3);

        Label labelEqualizerInverse = new Label("Inverse");
        ChoiceBox<VisualizeMode.Direct> choiceBoxEqualizerInverse = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_DIRECTION));
        labelEqualizerInverse.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerInverse.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        paramPane.add(labelEqualizerInverse, 0, 4);
        paramPane.add(choiceBoxEqualizerInverse, 1, 4);

        Label labelEqualizerStereo = new Label("Stereo");
        ChoiceBox<VisualizeMode.Stereo> choiceBoxEqualizerStereo = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_STEREO));
        labelEqualizerStereo.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerStereo.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        paramPane.add(labelEqualizerStereo, 0, 5);
        paramPane.add(choiceBoxEqualizerStereo, 1, 5);

        // Part
        Label labelBarNum = new Label("Bar Number");
        Slider sliderBarNum = new Slider();
        labelBarNum.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBarNum.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelSize = new Label("Size");
        Slider sliderSize = new Slider();
        labelSize.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSize.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRotation = new Label("Rotation");
        Slider sliderRotation = new Slider();
        labelRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelGap = new Label("Gap");
        Slider sliderGap = new Slider();
        labelGap.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderGap.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRadius = new Label("Radius");
        Slider sliderRadius = new Slider();
        labelRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // Group
        groupEqualizer = new GridPane();
        paramPane.add(groupEqualizer, 0, 2, 2, 1);

        HBox groupBarNum = new HBox(labelBarNum, sliderBarNum);
        HBox groupSize = new HBox(labelSize, sliderSize);
        HBox groupRotation = new HBox(labelRotation, sliderRotation);
        HBox groupGap = new HBox(labelGap, sliderGap);
        HBox groupRadius = new HBox(labelRadius, sliderRadius);
        HBox[] groupEqualizerParam = new HBox[] {groupBarNum, groupSize, groupRotation, groupGap, groupRadius};

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
        choiceBoxEqualizerDirection.setOnAction(event -> System.out.println(choiceBoxEqualizerDirection.getValue()));
        //  └ Equalizer Inverse
        choiceBoxEqualizerInverse.setOnAction(event -> System.out.println(choiceBoxEqualizerInverse.getValue()));
        //  └ Equalizer Stereo
        choiceBoxEqualizerStereo.setOnAction(event -> System.out.println(choiceBoxEqualizerStereo.getValue()));
        // └ Slider
        //  └ Bar Number
        sliderBarNum.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderBarNum.lookup(".track").setStyle(String.format(
                    "-fx-background-color: linear-gradient(to right, lightseagreen %d%%, #444444 %d%%);",
                    newValue.intValue(), newValue.intValue()));
        });
        sliderSize.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderSize.lookup(".track").setStyle(String.format(
                    "-fx-background-color: linear-gradient(to right, lightseagreen %d%%, #444444 %d%%);",
                    newValue.intValue(), newValue.intValue()));
        });
        sliderRotation.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRotation.lookup(".track").setStyle(String.format(
                    "-fx-background-color: linear-gradient(to right, lightseagreen %d%%, #444444 %d%%);",
                    newValue.intValue(), newValue.intValue()));
        });
        sliderGap.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderGap.lookup(".track").setStyle(String.format(
                    "-fx-background-color: linear-gradient(to right, lightseagreen %d%%, #444444 %d%%);",
                    newValue.intValue(), newValue.intValue()));
        });
        sliderRadius.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRadius.lookup(".track").setStyle(String.format(
                    "-fx-background-color: linear-gradient(to right, lightseagreen %d%%, #444444 %d%%);",
                    newValue.intValue(), newValue.intValue()));
        });

    }

}
