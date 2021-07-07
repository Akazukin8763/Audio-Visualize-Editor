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

import javafx.scene.paint.Color;

import javafx.collections.FXCollections;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

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
    public final StringProperty colorShadowProperty = new SimpleStringProperty(null);
    public final IntegerProperty colorShadowRadiusProperty = new SimpleIntegerProperty();
    public final IntegerProperty colorShadowSpreadProperty = new SimpleIntegerProperty();
    public final IntegerProperty colorShadowOffsetXProperty = new SimpleIntegerProperty();
    public final IntegerProperty colorShadowOffsetYProperty = new SimpleIntegerProperty();

    public final StringProperty backgroundColorProperty = new SimpleStringProperty(null);

    private final IntegerProperty channelsProperty = new SimpleIntegerProperty(); // 影響 Stereo
    //private final DoubleProperty frameRateProperty = new SimpleDoubleProperty(); // 影響 Frequency

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

        Label labelImageTitle = new Label("Image");
        labelImageTitle.setFont(new Font(20));
        paramPane.add(labelImageTitle, 0, 9, 3, 1);
        GridPane.setHalignment(labelImageTitle, HPos.CENTER);

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
        // └ Equalizer
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

        Label labelColorShadow = new Label("Color Shadow");
        ColorPicker colorPickerColorShadow = new ColorPicker();
        labelColorShadow.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerColorShadow.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowRadius = new Label(" └ Radius");
        Slider sliderColorShadowRadius = new Slider(0, 127, 0);
        TextField textFieldColorShadowRadius = new TextField("0");
        labelColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowSpread = new Label(" └ Spread");
        Slider sliderColorShadowSpread = new Slider(0, 100, 0);
        TextField textFieldColorShadowSpread = new TextField("0");
        labelColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowOffsetX = new Label(" └ Offset X");
        Slider sliderColorShadowOffsetX = new Slider(0, 1920, 0);
        TextField textFieldColorShadowOffsetX = new TextField("0");
        labelColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowOffsetY = new Label(" └ Offset Y");
        Slider sliderColorShadowOffsetY = new Slider(0, 1080, 0);
        TextField textFieldColorShadowOffsetY = new TextField("0");
        labelColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

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

        Label labelMinMaxFreq = new Label(" └ Min / Max");
        Label labelFreqNull = new Label();
        TextField textFieldMinFreq = new TextField("0");
        TextField textFieldMaxFreq = new TextField("24000");
        labelMinMaxFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        labelFreqNull.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE - WIDTH_OFFSET_RIGHT));
        textFieldMinFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        textFieldMaxFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // └ Background
        Label labelBackgroundColor = new Label("Color");
        ColorPicker colorPickerBackgroundColor = new ColorPicker();
        labelBackgroundColor.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerBackgroundColor.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // Group
        // └ Equalizer
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

        HBox groupColorShadow = new HBox(labelColorShadow, colorPickerColorShadow);
        HBox groupColorShadowRadius = new HBox(labelColorShadowRadius, sliderColorShadowRadius, textFieldColorShadowRadius);
        HBox groupColorShadowSpread = new HBox(labelColorShadowSpread, sliderColorShadowSpread, textFieldColorShadowSpread);
        HBox groupColorShadowOffsetX = new HBox(labelColorShadowOffsetX, sliderColorShadowOffsetX, textFieldColorShadowOffsetX);
        HBox groupColorShadowOffsetY = new HBox(labelColorShadowOffsetY, sliderColorShadowOffsetY, textFieldColorShadowOffsetY);
        HBox groupSensitivity = new HBox(labelSensitivity, sliderSensitivity, textFieldSensitivity);
        HBox groupFreq = new HBox(labelFreq, rangeSliderFreq);
        HBox groupMinMaxFreq = new HBox(labelMinMaxFreq, textFieldMinFreq, labelFreqNull, textFieldMaxFreq);
        HBox[] groupAdvanceParam = new HBox[] {groupColorShadow, groupColorShadowRadius, groupColorShadowSpread,
                                                groupColorShadowOffsetX, groupColorShadowOffsetY,
                                                groupSensitivity, groupFreq, groupMinMaxFreq};

        // └ Background
        HBox groupBackgroundColor = new HBox(labelBackgroundColor, colorPickerBackgroundColor);
        paramPane.add(groupBackgroundColor, 0, 8, 2, 1);

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

            // TEMP
            if (type == VisualizeMode.View.CIRCLE) {
                sliderColorShadowOffsetX.setDisable(true);
                sliderColorShadowOffsetY.setDisable(true);
                textFieldColorShadowOffsetX.setEditable(false);
                textFieldColorShadowOffsetY.setEditable(false);
            } else {
                sliderColorShadowOffsetX.setDisable(false);
                sliderColorShadowOffsetY.setDisable(false);
                textFieldColorShadowOffsetX.setEditable(true);
                textFieldColorShadowOffsetY.setEditable(true);
            }
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
        choiceBoxEqualizerStereo.valueProperty().addListener((obs, oldValue, newValue) -> {
            VisualizeMode.Stereo type = (newValue == null ? oldValue : newValue);
            equalizerStereoProperty.setValue(type.toString()); // Property
        });
        //   └ Audio File Changed Affect On Stereo and Frequency
        channelsProperty.addListener((obs, oldValue, newValue) -> {
            if (newValue.intValue() == 1) {
                choiceBoxEqualizerStereo.getItems().setAll(EQUALIZER_STEREO[0]);
                choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[0]);
            }
            else if (newValue.intValue() == 2) {
                choiceBoxEqualizerStereo.getItems().setAll(EQUALIZER_STEREO[1], EQUALIZER_STEREO[2], EQUALIZER_STEREO[3]);
                choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[3]);
            }
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

                colorShadowProperty.setValue(colorPickerColorShadow.getValue().toString());
                sensitivityProperty.setValue(sliderSensitivity.getValue());
                minFrequencyProperty.setValue(rangeSliderFreq.getLowValue());
                maxFrequencyProperty.setValue(rangeSliderFreq.getHighValue());
                //sliderSensitivity.lookup(".track").setStyle(sliderTrackStyle(sliderSensitivity.getValue() / sliderSensitivity.getMax() * 100));
            }
            else { // 保留內部資料，重製音波資料，因為處於未啟用狀態
                // 預設值
                colorShadowProperty.setValue(Color.rgb(0, 0, 0, 0).toString());
                sensitivityProperty.setValue(50);
                minFrequencyProperty.setValue(0);
                maxFrequencyProperty.setValue(24000);
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
        rangeSliderFreq.lowValueProperty().addListener((obs, oldValue, newValue) -> textFieldMinFreq.textProperty().setValue(String.format("%d", newValue.intValue())));
        rangeSliderFreq.highValueProperty().addListener((obs, oldValue, newValue) -> textFieldMaxFreq.textProperty().setValue(String.format("%d", newValue.intValue())));
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
        //  └ Color Shadow Radius
        sliderColorShadowRadius.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderColorShadowRadius.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderColorShadowRadius.getMax() * 100));
            textFieldColorShadowRadius.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldColorShadowRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowRadius.getText(), (int) sliderColorShadowRadius.getMax());
            sliderColorShadowRadius.valueProperty().setValue(value);
            textFieldColorShadowRadius.textProperty().setValue(String.format("%d", value));
            colorShadowRadiusProperty.setValue(value); // Property
        });
        //  └ Color Shadow Spread
        sliderColorShadowSpread.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderColorShadowSpread.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderColorShadowSpread.getMax() * 100));
            textFieldColorShadowSpread.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldColorShadowSpread.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowSpread.getText(), (int) sliderColorShadowSpread.getMax());
            sliderColorShadowSpread.valueProperty().setValue(value);
            textFieldColorShadowSpread.textProperty().setValue(String.format("%d", value));
            colorShadowSpreadProperty.setValue(value); // Property
        });
        //  └ Color Shadow Offset X
        sliderColorShadowOffsetX.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderColorShadowOffsetX.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderColorShadowOffsetX.getMax() * 100));
            textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldColorShadowOffsetX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetX.getText(), (int) sliderColorShadowOffsetX.getMax());
            sliderColorShadowOffsetX.valueProperty().setValue(value);
            textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetXProperty.setValue(value); // Property
        });
        //  └ Color Shadow Offset Y
        sliderColorShadowOffsetY.valueProperty().addListener((obs, oldValue, newValue) -> {
            sliderColorShadowOffsetY.lookup(".track").setStyle(sliderTrackStyle(newValue.doubleValue() / sliderColorShadowOffsetY.getMax() * 100));
            textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", newValue.intValue()));
        });
        textFieldColorShadowOffsetY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetY.getText(), (int) sliderColorShadowOffsetY.getMax());
            sliderColorShadowOffsetY.valueProperty().setValue(value);
            textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetYProperty.setValue(value); // Property
        });
        // └ Color Picker
        //  └ Color
        colorPickerColor.valueProperty().addListener((obs, oldValue, newValue) -> colorProperty.setValue(newValue.toString()));
        //  └ Color Shadow
        colorPickerColorShadow.valueProperty().addListener((obs, oldValue, newValue) -> colorShadowProperty.setValue(newValue.toString()));
        //  └ Background Color
        colorPickerBackgroundColor.valueProperty().addListener((obs, oldValue, newValue) -> backgroundColorProperty.setValue(newValue.toString()));

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

    public void setChannels(int channels) {
        if (channels == 1 || channels == 2)
            this.channelsProperty.setValue(channels);
    }

}
