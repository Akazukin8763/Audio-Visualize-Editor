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
import javafx.scene.control.ColorPicker;
import org.controlsfx.control.RangeSlider;

import javafx.scene.text.Font;
import javafx.geometry.HPos;

import javafx.collections.FXCollections;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ParamUI extends ScrollPane {

    private final double width;
    private final double height;

    private final GridPane paramPane;
    private final GridPane groupEqualizer;
    private final GridPane groupAdvance;

    private static final double WIDTH_OFFSET_LEFT = .35;
    private static final double WIDTH_OFFSET_MIDDLE = .48;
    private static final double WIDTH_OFFSET_RIGHT = .17;
    private static final int[][] GROUP_EQUALIZER_INDEX = {{0, 1, 2, 3, 5, 6, 7}, {0, 1, 2, 4, 5, 6, 7}, {0, 1, 2, 3, 5, 6, 7}};

    private static final VisualizeMode.View[] EQUALIZER_TYPE = VisualizeMode.View.values();
    private static final VisualizeMode.Side[] EQUALIZER_SIDE = VisualizeMode.Side.values();
    private static final VisualizeMode.Direct[] EQUALIZER_DIRECTION = VisualizeMode.Direct.values();
    private static final VisualizeMode.Stereo[] EQUALIZER_STEREO = VisualizeMode.Stereo.values();

    // Property
    public final StringProperty equalizerTypeProperty = new SimpleStringProperty(null);
    public final StringProperty equalizerSideProperty = new SimpleStringProperty(null);
    public final StringProperty equalizerDirectionProperty = new SimpleStringProperty(null);
    public final StringProperty equalizerStereoProperty = new SimpleStringProperty(null);

    public final IntegerProperty barNumberProperty = new SimpleIntegerProperty();
    public final IntegerProperty sizeProperty = new SimpleIntegerProperty();
    public final IntegerProperty rotationProperty = new SimpleIntegerProperty();
    public final IntegerProperty gapProperty = new SimpleIntegerProperty();
    public final IntegerProperty radiusProperty = new SimpleIntegerProperty();
    public final IntegerProperty positionXProperty = new SimpleIntegerProperty();
    public final IntegerProperty positionYProperty = new SimpleIntegerProperty();
    public final IntegerProperty sensitivityProperty = new SimpleIntegerProperty();
    public final IntegerProperty minFrequencyProperty = new SimpleIntegerProperty();
    public final IntegerProperty maxFrequencyProperty = new SimpleIntegerProperty();

    public final StringProperty colorProperty = new SimpleStringProperty(null);

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
        paramPane.add(labelBackgroundTitle, 0, 7, 3, 1);
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
        paramPane.add(labelEqualizerSide, 0, 3);
        paramPane.add(choiceBoxEqualizerSide, 1, 3);

        Label labelEqualizerDirection = new Label("Direction");
        ChoiceBox<VisualizeMode.Direct> choiceBoxEqualizerDirection = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_DIRECTION));
        labelEqualizerDirection.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerDirection.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerDirection, 0, 4);
        paramPane.add(choiceBoxEqualizerDirection, 1, 4);

        Label labelAdvance = new Label("Advance");
        CheckBox checkBoxAdvance = new CheckBox();
        labelAdvance.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        //checkBoxAdvance.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        GridPane.setHalignment(checkBoxAdvance, HPos.RIGHT);
        paramPane.add(labelAdvance, 0, 5, 2, 1);
        paramPane.add(checkBoxAdvance, 1, 5);

        Label labelEqualizerStereo = new Label("Stereo");
        ChoiceBox<VisualizeMode.Stereo> choiceBoxEqualizerStereo = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_STEREO));
        labelEqualizerStereo.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerStereo.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        //paramPane.add(labelEqualizerStereo, 0, ?);
        //paramPane.add(choiceBoxEqualizerStereo, 1, ?);

        // Part
        Label labelBarNum = new Label("Bar Number");
        Slider sliderBarNum = new Slider(0, 256, 0);
        TextField textFieldBarNum = new TextField("0");
        labelBarNum.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBarNum.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBarNum.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelSize = new Label("Size");
        Slider sliderSize = new Slider(0, 10, 0);
        TextField textFieldSize = new TextField("0");
        labelSize.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSize.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldSize.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRotation = new Label("Rotation");
        Slider sliderRotation = new Slider(0, 360, 0);
        TextField textFieldRotation = new TextField("0");
        labelRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRotation.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelGap = new Label("Gap");
        Slider sliderGap = new Slider(0, 10, 0);
        TextField textFieldGap = new TextField("0");
        labelGap.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderGap.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldGap.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRadius = new Label("Radius");
        Slider sliderRadius = new Slider(0, 512, 0);
        TextField textFieldRadius = new TextField("0");
        labelRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelPosX = new Label("Position X");
        Slider sliderPosX = new Slider(0, 1920, 0);
        TextField textFieldPosX = new TextField("0");
        labelPosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelPosY = new Label("Position Y");
        Slider sliderPosY = new Slider(0, 1080, 0);
        TextField textFieldPosY = new TextField("0");
        labelPosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColor = new Label("Color");
        ColorPicker colorPickerColor = new ColorPicker();
        labelColor.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerColor.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelSensitivity = new Label("Sensitivity");
        Slider sliderSensitivity = new Slider(0, 100, 0);
        TextField textFieldSensitivity = new TextField("0");
        labelSensitivity.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSensitivity.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldSensitivity.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelFreq = new Label("Frequency");
        RangeSlider rangeSliderFreq = new RangeSlider(0, 24000, 0, 24000);
        labelFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        rangeSliderFreq.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));

        Label labelMinMaxFreq = new Label("Min / Max");
        Label labelFreqNull = new Label();
        TextField textFieldMinFreq = new TextField("0");
        TextField textFieldMaxFreq = new TextField("24000");
        labelMinMaxFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        labelFreqNull.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE - WIDTH_OFFSET_RIGHT));
        textFieldMinFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
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
        HBox groupPosX = new HBox(labelPosX, sliderPosX, textFieldPosX);
        HBox groupPosY = new HBox(labelPosY, sliderPosY, textFieldPosY);
        HBox groupColor = new HBox(labelColor, colorPickerColor);
        HBox[] groupEqualizerParam = new HBox[] {groupBarNum, groupSize, groupRotation, groupGap, groupRadius, groupPosX, groupPosY, groupColor};

        groupAdvance = new GridPane();
        groupAdvance.setHgap(10);
        groupAdvance.setVgap(5);
        paramPane.add(groupAdvance, 0, 6, 2, 1);

        HBox groupSensitivity = new HBox(labelSensitivity, sliderSensitivity, textFieldSensitivity);
        HBox groupFreq = new HBox(labelFreq, rangeSliderFreq);
        HBox groupMinMaxFreq = new HBox(labelMinMaxFreq, textFieldMinFreq, labelFreqNull, textFieldMaxFreq);
        HBox[] groupAdvanceParam = new HBox[] {groupSensitivity, groupFreq, groupMinMaxFreq};

        // Event
        // └ this
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setOnMouseEntered(event -> this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED));
        this.setOnMouseExited(event -> this.setVbarPolicy(ScrollBarPolicy.NEVER));
        // └ Choice Box
        //  └ Equalizer Type
        choiceBoxEqualizerType.setOnAction(event -> {
            VisualizeMode.View type = choiceBoxEqualizerType.getValue();
            equalizerTypeProperty.setValue(type.toString()); // Property

            groupEqualizer.getChildren().clear(); // 清空所有可能選單
            int groupRow = 0;
            for (int index: GROUP_EQUALIZER_INDEX[type.value()])
                groupEqualizer.add(groupEqualizerParam[index], 0, groupRow++, 2, 1);
        });
        //  └ Equalizer Side
        choiceBoxEqualizerSide.setOnAction(event -> {
            VisualizeMode.Side type = choiceBoxEqualizerSide.getValue();
            equalizerSideProperty.setValue(type.toString()); // Property
        });
        //  └ Equalizer Direction
        choiceBoxEqualizerDirection.setOnAction(event -> {
            VisualizeMode.Direct type = choiceBoxEqualizerDirection.getValue();
            equalizerDirectionProperty.setValue(type.toString()); // Property
        });
        //  └ Equalizer Stereo
        choiceBoxEqualizerStereo.setOnAction(event -> {
            VisualizeMode.Stereo type = choiceBoxEqualizerStereo.getValue();
            equalizerStereoProperty.setValue(type.toString()); // Property
        });
        // └ Check Box
        //  └ Advance
        checkBoxAdvance.setOnAction(event -> {
            groupAdvance.getChildren().clear(); // 清空所有可能選單
            if (checkBoxAdvance.isSelected()) {
                int groupRow = 0;
                for (HBox param: groupAdvanceParam)
                    groupAdvance.add(param, 0, groupRow++, 2, 1);
                groupAdvance.add(labelEqualizerStereo, 0, groupRow);
                groupAdvance.add(choiceBoxEqualizerStereo, 1, groupRow);
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
            barNumberProperty.setValue(value); // Property
        });
        //  └ Size
        sliderSize.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderSize.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderSize.getMax() * 100));
            textFieldSize.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldSize.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSize.getText(), (int) sliderSize.getMax());
            sliderSize.valueProperty().setValue(value);
            textFieldSize.textProperty().setValue(String.format("%d", value));
            sizeProperty.setValue(value); // Property
        });
        //  └ Rotation
        sliderRotation.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRotation.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderRotation.getMax() * 100));
            textFieldRotation.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldRotation.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRotation.getText(), (int) sliderRotation.getMax());
            sliderRotation.valueProperty().setValue(value);
            textFieldRotation.textProperty().setValue(String.format("%d", value));
            rotationProperty.setValue(value); // Property
        });
        //  └ Gap
        sliderGap.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderGap.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderGap.getMax() * 100));
            textFieldGap.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldGap.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldGap.getText(), (int) sliderGap.getMax());
            sliderGap.valueProperty().setValue(value);
            textFieldGap.textProperty().setValue(String.format("%d", value));
            gapProperty.setValue(value); // Property
        });
        //  └ Radius
        sliderRadius.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderRadius.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderRadius.getMax() * 100));
            textFieldRadius.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRadius.getText(), (int) sliderRadius.getMax());
            sliderRadius.valueProperty().setValue(value);
            textFieldRadius.textProperty().setValue(String.format("%d", value));
            radiusProperty.setValue(value); // Property
        });
        //  └ Position X
        sliderPosX.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderPosX.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderPosX.getMax() * 100));
            textFieldPosX.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldPosX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosX.getText(), (int) sliderPosX.getMax());
            sliderPosX.valueProperty().setValue(value);
            textFieldPosX.textProperty().setValue(String.format("%d", value));
            positionXProperty.setValue(value); // Property
        });
        //  └ Position Y
        sliderPosY.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderPosY.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderPosY.getMax() * 100));
            textFieldPosY.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldPosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosY.getText(), (int) sliderPosY.getMax());
            sliderPosY.valueProperty().setValue(value);
            textFieldPosY.textProperty().setValue(String.format("%d", value));
            positionYProperty.setValue(value); // Property
        });
        //  └ Sensitivity
        sliderSensitivity.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderSensitivity.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderSensitivity.getMax() * 100));
            textFieldSensitivity.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldSensitivity.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSensitivity.getText(), (int) sliderSensitivity.getMax());
            sliderSensitivity.valueProperty().setValue(value);
            textFieldSensitivity.textProperty().setValue(String.format("%d", value));
            sensitivityProperty.setValue(value); // Property
        });
        //  └ Frequency
        rangeSliderFreq.lowValueProperty().addListener((obs, oldValue, newValue) -> {
            textFieldMinFreq.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        rangeSliderFreq.highValueProperty().addListener((obs, oldValue, newValue) -> {
            textFieldMaxFreq.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldMinFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMinFreq.getText(), (int) rangeSliderFreq.getMax());
            rangeSliderFreq.lowValueProperty().setValue(value);
            textFieldMinFreq.textProperty().setValue(String.format("%d", value));
            minFrequencyProperty.setValue(value); // Property
        });
        textFieldMaxFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMaxFreq.getText(), (int) rangeSliderFreq.getMax());
            rangeSliderFreq.highValueProperty().setValue(value);
            textFieldMaxFreq.textProperty().setValue(String.format("%d", value));
            maxFrequencyProperty.setValue(value); // Property
        });
        // └ Color Picker
        //  └ Color
        colorPickerColor.valueProperty().addListener((obs, oldValue, newValue) -> {
            colorProperty.setValue(newValue.toString());
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
