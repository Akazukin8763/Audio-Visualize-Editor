package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;

import javafx.stage.FileChooser;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.HPos;
import javafx.geometry.Pos;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import org.controlsfx.control.RangeSlider;

import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.collections.FXCollections;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ParamUI extends ScrollPane {

    private final double width;
    private final double height;
    private int rangeWidth;
    private int rangeHeight;

    private final GridPane paramPane;
    private final GridPane groupEqualizer;
    private final GridPane groupAdvance;
    private final GridPane groupBackgroundImagePos;
    private final HBox[] groupBackgroundImagePosParam;
    private final GridPane groupImage;
    private final CustomImageListView images;

    private final FileChooser fileChooser;

    private static final double WIDTH_OFFSET_LEFT = .35;
    private static final double WIDTH_OFFSET_MIDDLE = .48;
    private static final double WIDTH_OFFSET_RIGHT = .17;
    private static final int[][] GROUP_EQUALIZER_INDEX = {{0, 1, 2, 3, 5, 6, 7}, {0, 1, 2, 4, 5, 6, 7}, {0, 1, 2, 3, 5, 6, 7}};

    private static final VisualizeMode.View[] EQUALIZER_TYPE = VisualizeMode.View.values();
    private static final VisualizeMode.Side[] EQUALIZER_SIDE = VisualizeMode.Side.values();
    private static final VisualizeMode.Direct[] EQUALIZER_DIRECTION = VisualizeMode.Direct.values();
    private static final VisualizeMode.Stereo[] EQUALIZER_STEREO = VisualizeMode.Stereo.values();

    // Object
    private final ChoiceBox<VisualizeMode.View> choiceBoxEqualizerType;
    private final ChoiceBox<VisualizeMode.Side> choiceBoxEqualizerSide;
    private final ChoiceBox<VisualizeMode.Direct> choiceBoxEqualizerDirection;
    private final ChoiceBox<VisualizeMode.Stereo> choiceBoxEqualizerStereo;
    private final CheckBox checkBoxAdvance;

    private final Slider sliderBarNum;
    private final TextField textFieldBarNum;
    private final Slider sliderSize;
    private final TextField textFieldSize;
    private final Slider sliderRotation;
    private final TextField textFieldRotation;
    private final Slider sliderGap;
    private final TextField textFieldGap;
    private final Slider sliderRadius;
    private final TextField textFieldRadius;
    private final Slider sliderPosX;
    private final TextField textFieldPosX;
    private final Slider sliderPosY;
    private final TextField textFieldPosY;
    private final ColorPicker colorPickerColor;
    private final ColorPicker colorPickerColorShadow;
    private final Slider sliderColorShadowRadius;
    private final TextField textFieldColorShadowRadius;
    private final Slider sliderColorShadowSpread;
    private final TextField textFieldColorShadowSpread;
    private final Slider sliderColorShadowOffsetX;
    private final TextField textFieldColorShadowOffsetX;
    private final Slider sliderColorShadowOffsetY;
    private final TextField textFieldColorShadowOffsetY;
    private final Slider sliderSensitivity;
    private final TextField textFieldSensitivity;
    private final RangeSlider rangeSliderFreq;
    private final TextField textFieldMinFreq;
    private final TextField textFieldMaxFreq;

    private final ColorPicker colorPickerBackgroundColor;
    private final Label labelBackgroundImageName;
    private final Button buttonBackgroundImageImport;
    private final Button buttonBackgroundImageClear;
    private final Slider sliderBackgroundImagePosX;
    private final TextField textFieldBackgroundImagePosX;
    private final Slider sliderBackgroundImagePosY;
    private final TextField textFieldBackgroundImagePosY;

    private final Button buttonImageImport;
    private final Button buttonImageClear;

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
    public final StringProperty backgroundImageProperty = new SimpleStringProperty(null);
    public final IntegerProperty backgroundImagePositionXProperty = new SimpleIntegerProperty();
    public final IntegerProperty backgroundImagePositionYProperty = new SimpleIntegerProperty();

    //private final IntegerProperty channelsProperty = new SimpleIntegerProperty(); // 影響 Stereo
    //private final DoubleProperty frameRateProperty = new SimpleDoubleProperty(); // 影響 Frequency

    public final BooleanProperty imageFormatProperty;

    public ParamUI(double width, double height, int rangeWidth, int rangeHeight) {
        this.width = width;
        this.height = height;
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.images = new CustomImageListView(width, height);
        this.imageFormatProperty = images.imageFormatProperty;

        paramPane = new GridPane();
        paramPane.setPrefWidth(width);
        setContent(paramPane);
        setPrefSize(width, height);

        paramPane.setHgap(10);
        paramPane.setVgap(10);
        //paramPane.setGridLinesVisible(true);
        paramPane.setStyle("-fx-background-color: transparent");

        this.fileChooser = new FileChooser();
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png")); // 只抓 jpg, png

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
        paramPane.add(labelImageTitle, 0, 11, 3, 1);
        GridPane.setHalignment(labelImageTitle, HPos.CENTER);

        // Selection
        Label labelEqualizerType = new Label("Equalizer Type");
        choiceBoxEqualizerType = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_TYPE));
        labelEqualizerType.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerType.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerType, 0, 1);
        paramPane.add(choiceBoxEqualizerType, 1, 1);

        Label labelEqualizerSide = new Label("Side");
        choiceBoxEqualizerSide = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_SIDE));
        labelEqualizerSide.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerSide.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerSide, 0, 3);
        paramPane.add(choiceBoxEqualizerSide, 1, 3);

        Label labelEqualizerDirection = new Label("Direction");
        choiceBoxEqualizerDirection = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_DIRECTION));
        labelEqualizerDirection.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerDirection.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        paramPane.add(labelEqualizerDirection, 0, 4);
        paramPane.add(choiceBoxEqualizerDirection, 1, 4);

        Label labelAdvance = new Label("Advance");
        checkBoxAdvance = new CheckBox();
        labelAdvance.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        //checkBoxAdvance.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        GridPane.setHalignment(checkBoxAdvance, HPos.RIGHT);
        paramPane.add(labelAdvance, 0, 5, 2, 1);
        paramPane.add(checkBoxAdvance, 1, 5);

        Label labelEqualizerStereo = new Label("Stereo");
        choiceBoxEqualizerStereo = new ChoiceBox<>(FXCollections.observableArrayList(EQUALIZER_STEREO));
        labelEqualizerStereo.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        choiceBoxEqualizerStereo.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));
        //paramPane.add(labelEqualizerStereo, 0, ?);
        //paramPane.add(choiceBoxEqualizerStereo, 1, ?);

        // Part
        // └ Equalizer
        Label labelBarNum = new Label("Bar Number");
        sliderBarNum = new CustomSlider(0, 256, 0);
        textFieldBarNum = new TextField("0");
        labelBarNum.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBarNum.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBarNum.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelSize = new Label("Size");
        sliderSize = new CustomSlider(0, 10, 0);
        textFieldSize = new TextField("0");
        labelSize.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSize.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldSize.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRotation = new Label("Rotation");
        sliderRotation = new CustomSlider(0, 360, 0);
        textFieldRotation = new TextField("0");
        labelRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRotation.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelGap = new Label("Gap");
        sliderGap = new CustomSlider(0, 10, 0);
        textFieldGap = new TextField("0");
        labelGap.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderGap.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldGap.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelRadius = new Label("Radius");
        sliderRadius = new CustomSlider(0, rangeHeight, 0);
        textFieldRadius = new TextField("0");
        labelRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelPosX = new Label("Position X");
        sliderPosX = new CustomSlider(-rangeWidth, 2 * rangeWidth, -rangeWidth);
        textFieldPosX = new TextField(String.format("%d", -rangeWidth));
        labelPosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelPosY = new Label("Position Y");
        sliderPosY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldPosY = new TextField(String.format("%d", -rangeHeight));
        labelPosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColor = new Label("Color");
        colorPickerColor = new ColorPicker();
        labelColor.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerColor.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadow = new Label("Color Shadow");
        colorPickerColorShadow = new ColorPicker();
        labelColorShadow.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerColorShadow.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowRadius = new Label(" └ Radius");
        sliderColorShadowRadius = new CustomSlider(0, 127, 0);
        textFieldColorShadowRadius = new TextField("0");
        labelColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowSpread = new Label(" └ Spread");
        sliderColorShadowSpread = new CustomSlider(0, 100, 0);
        textFieldColorShadowSpread = new TextField("0");
        labelColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowSpread.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowOffsetX = new Label(" └ Offset X");
        sliderColorShadowOffsetX = new CustomSlider(-rangeWidth, 2 * rangeWidth, -rangeWidth);
        textFieldColorShadowOffsetX = new TextField(String.format("%d", -rangeWidth));
        labelColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowOffsetX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelColorShadowOffsetY = new Label(" └ Offset Y");
        sliderColorShadowOffsetY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldColorShadowOffsetY = new TextField(String.format("%d", -rangeHeight));
        labelColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelSensitivity = new Label("Sensitivity");
        sliderSensitivity = new CustomSlider(0, 100, 0);
        textFieldSensitivity = new TextField("0");
        labelSensitivity.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderSensitivity.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldSensitivity.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelFreq = new Label("Frequency");
        rangeSliderFreq = new RangeSlider(0, 24000, 0, 24000);
        labelFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        rangeSliderFreq.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE + WIDTH_OFFSET_RIGHT));

        Label labelMinMaxFreq = new Label(" └ Min / Max");
        Label labelFreqNull = new Label();
        textFieldMinFreq = new TextField("0");
        textFieldMaxFreq = new TextField("24000");
        labelMinMaxFreq.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        labelFreqNull.setPrefWidth(width * (WIDTH_OFFSET_MIDDLE - WIDTH_OFFSET_RIGHT));
        textFieldMinFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        textFieldMaxFreq.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // └ Background
        Label labelBackgroundColor = new Label("Color");
        colorPickerBackgroundColor = new ColorPicker();
        labelBackgroundColor.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE));
        colorPickerBackgroundColor.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelBackgroundImage = new Label("Image");
        labelBackgroundImageName = new Label();
        Label labelBackgroundImageNull = new Label();
        buttonBackgroundImageImport = new Button();
        buttonBackgroundImageClear = new Button();
        ImageView imageViewBackgroundImageImport = new ImageView(new Image(new File(DefaultPath.FOLDER_ICON_PATH).toURI().toString()));
        ImageView imageViewBackgroundImageClear = new ImageView(new Image(new File(DefaultPath.CANCEL_ICON_PATH).toURI().toString()));
        buttonBackgroundImageImport.setGraphic(imageViewBackgroundImageImport);
        buttonBackgroundImageClear.setGraphic(imageViewBackgroundImageClear);
        labelBackgroundImage.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        labelBackgroundImageName.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        labelBackgroundImageNull.setPrefWidth(14);
        buttonBackgroundImageImport.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);
        buttonBackgroundImageClear.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);
        labelBackgroundImageName.setAlignment(Pos.CENTER_RIGHT);

        Label labelBackgroundImagePosX = new Label(" └ Position X");
        sliderBackgroundImagePosX = new CustomSlider(-rangeWidth, 2 * rangeWidth, -rangeWidth);
        textFieldBackgroundImagePosX = new TextField(String.format("%d", -rangeWidth));
        labelBackgroundImagePosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBackgroundImagePosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBackgroundImagePosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelBackgroundImagePosY = new Label(" └ Position Y");
        sliderBackgroundImagePosY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldBackgroundImagePosY = new TextField(String.format("%d", -rangeHeight));
        labelBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        // └ Image
        Label labelImage = new Label("Image");
        Label labelImageNull1 = new Label();
        Label labelImageNull2 = new Label();
        buttonImageImport = new Button();
        buttonImageClear = new Button();
        ImageView imageViewImageImport = new ImageView(new Image(new File(DefaultPath.FOLDER_ICON_PATH).toURI().toString()));
        ImageView imageViewImageClear = new ImageView(new Image(new File(DefaultPath.CANCEL_ICON_PATH).toURI().toString()));
        buttonImageImport.setGraphic(imageViewImageImport);
        buttonImageClear.setGraphic(imageViewImageClear);
        labelImage.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        labelImageNull1.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        labelImageNull2.setPrefWidth(14);
        buttonImageImport.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);
        buttonImageClear.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);

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
        groupBackgroundImagePos = new GridPane();
        groupBackgroundImagePos.setHgap(10);
        groupBackgroundImagePos.setVgap(5);
        paramPane.add(groupBackgroundImagePos, 0, 10, 2, 1);

        HBox groupBackgroundColor = new HBox(labelBackgroundColor, colorPickerBackgroundColor);
        HBox groupBackgroundImage = new HBox(labelBackgroundImage, labelBackgroundImageName, buttonBackgroundImageImport, labelBackgroundImageNull, buttonBackgroundImageClear);
        HBox groupBackgroundImagePosX = new HBox(labelBackgroundImagePosX, sliderBackgroundImagePosX, textFieldBackgroundImagePosX);
        HBox groupBackgroundImagePosY = new HBox(labelBackgroundImagePosY, sliderBackgroundImagePosY, textFieldBackgroundImagePosY);
        groupBackgroundImagePosParam = new HBox[] {groupBackgroundImagePosX, groupBackgroundImagePosY};
        paramPane.add(groupBackgroundColor, 0, 8, 2, 1);
        paramPane.add(groupBackgroundImage, 0, 9, 2, 1);
        //paramPane.add(groupBackgroundImagePosX, 0, 10, 2, 1);
        //paramPane.add(groupBackgroundImagePosY, 0, 11, 2, 1);

        // └ Image
        groupImage = new GridPane();
        groupImage.setHgap(10);
        groupImage.setVgap(5);
        paramPane.add(groupImage, 0, 13, 2, 1);

        HBox groupImageControl = new HBox(labelImage, labelImageNull1, buttonImageImport, labelImageNull2, buttonImageClear);
        paramPane.add(groupImageControl, 0, 12, 2, 1);

        // Event
        // └ this
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        //this.setFitToWidth(true);
        //this.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        /*this.addEventFilter(ScrollEvent.SCROLL, event -> {
            System.out.println(event.getDeltaX() + " " + event.getDeltaY());
            if (event.getDeltaX() != 0)
                event.consume();
        });*/
        this.setOnMouseEntered(event -> this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED));
        this.setOnMouseExited(event -> this.setVbarPolicy(ScrollBarPolicy.NEVER));
        // └ Equalizer
        //  └ Choice Box
        //   └ Equalizer Type
        choiceBoxEqualizerType.setOnAction(event -> {
            VisualizeMode.View type = choiceBoxEqualizerType.getValue();
            equalizerTypeProperty.setValue(type.toString()); // Property

            groupEqualizer.getChildren().clear(); // 清空所有可能選單
            int groupRow = 0;
            for (int index: GROUP_EQUALIZER_INDEX[type.value()])
                groupEqualizer.add(groupEqualizerParam[index], 0, groupRow++, 2, 1);
        });
        //   └ Equalizer Side
        choiceBoxEqualizerSide.setOnAction(event -> {
            VisualizeMode.Side type = choiceBoxEqualizerSide.getValue();
            equalizerSideProperty.setValue(type.toString()); // Property
        });
        //   └ Equalizer Direction
        choiceBoxEqualizerDirection.setOnAction(event -> {
            VisualizeMode.Direct type = choiceBoxEqualizerDirection.getValue();
            equalizerDirectionProperty.setValue(type.toString()); // Property
        });
        //   └ Equalizer Stereo
        choiceBoxEqualizerStereo.valueProperty().addListener((obs, oldValue, newValue) -> {
            VisualizeMode.Stereo type = (newValue == null ? oldValue : newValue);
            equalizerStereoProperty.setValue(type.toString()); // Property
        });
        //  └ Check Box
        //   └ Advance
        checkBoxAdvance.selectedProperty().addListener((obs, oldValue, newValue) -> {
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
            }
            else { // 保留內部資料，重製音波資料，因為處於未啟用狀態
                // 預設值
                colorShadowProperty.setValue(Color.rgb(0, 0, 0, 0).toString());
                sensitivityProperty.setValue(100);
                minFrequencyProperty.setValue(0);
                maxFrequencyProperty.setValue(24000);
            }
        });
        //  └ Slider
        //   └ Bar Number
        sliderBarNum.valueProperty().addListener((obs, oldValue, newValue) -> textFieldBarNum.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldBarNum.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBarNum.getText(), (int) sliderBarNum.getMax());
            sliderBarNum.valueProperty().setValue(value);
            textFieldBarNum.textProperty().setValue(String.format("%d", value));
            barNumberProperty.setValue(value); // Property
        });
        //   └ Size
        sliderSize.valueProperty().addListener((obs, oldValue, newValue) -> textFieldSize.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldSize.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSize.getText(), (int) sliderSize.getMax());
            sliderSize.valueProperty().setValue(value);
            textFieldSize.textProperty().setValue(String.format("%d", value));
            sizeProperty.setValue(value); // Property
        });
        //   └ Rotation
        sliderRotation.valueProperty().addListener((obs, oldValue, newValue) -> textFieldRotation.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldRotation.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRotation.getText(), (int) sliderRotation.getMax());
            sliderRotation.valueProperty().setValue(value);
            textFieldRotation.textProperty().setValue(String.format("%d", value));
            rotationProperty.setValue(value); // Property
        });
        //   └ Gap
        sliderGap.valueProperty().addListener((obs, oldValue, newValue) -> textFieldGap.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldGap.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldGap.getText(), (int) sliderGap.getMax());
            sliderGap.valueProperty().setValue(value);
            textFieldGap.textProperty().setValue(String.format("%d", value));
            gapProperty.setValue(value); // Property
        });
        //   └ Radius
        sliderRadius.valueProperty().addListener((obs, oldValue, newValue) -> textFieldRadius.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRadius.getText(), (int) sliderRadius.getMax());
            sliderRadius.valueProperty().setValue(value);
            textFieldRadius.textProperty().setValue(String.format("%d", value));
            radiusProperty.setValue(value); // Property
        });
        //   └ Position X
        sliderPosX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldPosX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldPosX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosX.getText(), (int) sliderPosX.getMax());
            sliderPosX.valueProperty().setValue(value);
            textFieldPosX.textProperty().setValue(String.format("%d", value));
            positionXProperty.setValue(value); // Property
        });
        //   └ Position Y
        sliderPosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldPosY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldPosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosY.getText(), (int) sliderPosY.getMax());
            sliderPosY.valueProperty().setValue(value);
            textFieldPosY.textProperty().setValue(String.format("%d", value));
            positionYProperty.setValue(value); // Property
        });
        //   └ Sensitivity
        sliderSensitivity.valueProperty().addListener((obs, oldValue, newValue) -> textFieldSensitivity.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldSensitivity.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSensitivity.getText(), (int) sliderSensitivity.getMax());
            sliderSensitivity.valueProperty().setValue(value);
            textFieldSensitivity.textProperty().setValue(String.format("%d", value));
            sensitivityProperty.setValue(value); // Property
        });
        //   └ Frequency
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
        //   └ Color Shadow Radius
        sliderColorShadowRadius.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowRadius.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowRadius.getText(), (int) sliderColorShadowRadius.getMax());
            sliderColorShadowRadius.valueProperty().setValue(value);
            textFieldColorShadowRadius.textProperty().setValue(String.format("%d", value));
            colorShadowRadiusProperty.setValue(value); // Property
        });
        //   └ Color Shadow Spread
        sliderColorShadowSpread.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowSpread.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowSpread.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowSpread.getText(), (int) sliderColorShadowSpread.getMax());
            sliderColorShadowSpread.valueProperty().setValue(value);
            textFieldColorShadowSpread.textProperty().setValue(String.format("%d", value));
            colorShadowSpreadProperty.setValue(value); // Property
        });
        //   └ Color Shadow Offset X
        sliderColorShadowOffsetX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowOffsetX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetX.getText(), (int) sliderColorShadowOffsetX.getMax());
            sliderColorShadowOffsetX.valueProperty().setValue(value);
            textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetXProperty.setValue(value); // Property
        });
        //   └ Color Shadow Offset Y
        sliderColorShadowOffsetY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowOffsetY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetY.getText(), (int) sliderColorShadowOffsetY.getMax());
            sliderColorShadowOffsetY.valueProperty().setValue(value);
            textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetYProperty.setValue(value); // Property
        });
        //  └ Color Picker
        //   └ Color
        colorPickerColor.valueProperty().addListener((obs, oldValue, newValue) -> colorProperty.setValue(newValue.toString()));
        //   └ Color Shadow
        colorPickerColorShadow.valueProperty().addListener((obs, oldValue, newValue) -> colorShadowProperty.setValue(newValue.toString()));
        // └ Background
        //  └ Button
        //   └ Background Image
        buttonBackgroundImageImport.setOnAction(event -> {
            try {
                File file = fileChooser.showOpenDialog(null);
                fileChooser.setInitialDirectory(new File(file.getParent()));
                labelBackgroundImageName.setText(file.getName());
                backgroundImageProperty.setValue(file.getAbsolutePath());

                groupBackgroundImagePos.getChildren().clear(); // 清空所有可能選單
                int groupRow = 0;
                for (HBox param: groupBackgroundImagePosParam)
                    groupBackgroundImagePos.add(param, 0, groupRow++, 2, 1);
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
        buttonBackgroundImageClear.setOnAction(event -> {
            labelBackgroundImageName.setText("");
            backgroundImageProperty.setValue(null);

            groupBackgroundImagePos.getChildren().clear(); // 清空所有可能選單
        });
        //  └ Slider
        //   └ Position X
        sliderBackgroundImagePosX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldBackgroundImagePosX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldBackgroundImagePosX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBackgroundImagePosX.getText(), (int) sliderBackgroundImagePosX.getMax());
            sliderBackgroundImagePosX.valueProperty().setValue(value);
            textFieldBackgroundImagePosX.textProperty().setValue(String.format("%d", value));
            backgroundImagePositionXProperty.setValue(value); // Property
        });
        //   └ Position Y
        sliderBackgroundImagePosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldBackgroundImagePosY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldBackgroundImagePosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBackgroundImagePosY.getText(), (int) sliderBackgroundImagePosY.getMax());
            sliderBackgroundImagePosY.valueProperty().setValue(value);
            textFieldBackgroundImagePosY.textProperty().setValue(String.format("%d", value));
            backgroundImagePositionYProperty.setValue(value); // Property
        });
        //  └ Color Picker
        //   └ Background Color
        colorPickerBackgroundColor.valueProperty().addListener((obs, oldValue, newValue) -> backgroundColorProperty.setValue(newValue.toString()));
        // └ Image
        //  └ Button
        //   └ Image
        buttonImageImport.setOnAction(event -> {
            try {
                List<File> files = fileChooser.showOpenMultipleDialog(null);
                fileChooser.setInitialDirectory(new File(files.get(0).getParent()));

                groupImage.getChildren().clear(); // 清空所有可能選單

                for (File file: files)
                    images.add(file.getAbsolutePath());

                int groupRow = 0;
                for (GridPane param: images.getGridPane())
                    groupImage.add(param, 0, groupRow++, 2, 1);
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
        buttonImageClear.setOnAction(event -> {
            images.clear();
            groupImage.getChildren().clear();
        });
        images.orderChangedProperty.addListener(event -> {
            groupImage.getChildren().clear(); // 清空所有可能選單

            int groupRow = 0;
            for (GridPane param: images.getGridPane())
                groupImage.add(param, 0, groupRow++, 2, 1);
        });

        // Initialize
        choiceBoxEqualizerType.setValue(EQUALIZER_TYPE[0]); // Line
        choiceBoxEqualizerSide.setValue(EQUALIZER_SIDE[0]); // Out
        choiceBoxEqualizerDirection.setValue(EQUALIZER_DIRECTION[0]); // Normal
        choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[3]); // Both

    }

    // Methods
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
        // Audio File Changed Affect On Stereo and Frequency
        if (channels == 1) {
            choiceBoxEqualizerStereo.getItems().setAll(EQUALIZER_STEREO[0]);
            choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[0]);
        }
        else if (channels == 2) {
            choiceBoxEqualizerStereo.getItems().setAll(EQUALIZER_STEREO[1], EQUALIZER_STEREO[2], EQUALIZER_STEREO[3]);
            choiceBoxEqualizerStereo.setValue(EQUALIZER_STEREO[3]);
        }
    }

    public void setRangeWidth(int width) {
        this.rangeWidth = width;

        sliderRadius.setMax(width);
        sliderPosX.setMin(-width);
        sliderPosX.setMax(2 * width);
        sliderColorShadowOffsetX.setMin(-width);
        sliderColorShadowOffsetX.setMax(2 * width);
        sliderBackgroundImagePosX.setMin(-width);
        sliderBackgroundImagePosX.setMax(2 * width);

    }

    public void setRangeHeight(int height) {
        this.rangeHeight = height;

        sliderPosY.setMin(-height);
        sliderPosY.setMax(2 * height);
        sliderColorShadowOffsetY.setMin(-height);
        sliderColorShadowOffsetY.setMax(2 * height);
        sliderBackgroundImagePosY.setMin(-height);
        sliderBackgroundImagePosY.setMax(2 * height);
    }

    public void setEnable(boolean enable) {
        boolean disable = !enable;

        choiceBoxEqualizerType.setDisable(disable);
        choiceBoxEqualizerSide.setDisable(disable);
        choiceBoxEqualizerDirection.setDisable(disable);
        choiceBoxEqualizerStereo.setDisable(disable);
        checkBoxAdvance.setDisable(disable);

        sliderBarNum.setDisable(disable);
        textFieldBarNum.setDisable(disable);
        sliderSize.setDisable(disable);
        textFieldSize.setDisable(disable);
        sliderRotation.setDisable(disable);
        textFieldRotation.setDisable(disable);
        sliderGap.setDisable(disable);
        textFieldGap.setDisable(disable);
        sliderRadius.setDisable(disable);
        textFieldRadius.setDisable(disable);
        sliderPosX.setDisable(disable);
        textFieldPosX.setDisable(disable);
        sliderPosY.setDisable(disable);
        textFieldPosY.setDisable(disable);
        colorPickerColor.setDisable(disable);
        colorPickerColorShadow.setDisable(disable);
        sliderColorShadowRadius.setDisable(disable);
        textFieldColorShadowRadius.setDisable(disable);
        sliderColorShadowSpread.setDisable(disable);
        textFieldColorShadowSpread.setDisable(disable);
        sliderColorShadowOffsetX.setDisable(disable);
        textFieldColorShadowOffsetX.setDisable(disable);
        sliderColorShadowOffsetY.setDisable(disable);
        textFieldColorShadowOffsetY.setDisable(disable);
        sliderSensitivity.setDisable(disable);
        textFieldSensitivity.setDisable(disable);
        rangeSliderFreq.setDisable(disable);
        textFieldMinFreq.setDisable(disable);
        textFieldMaxFreq.setDisable(disable);

        colorPickerBackgroundColor.setDisable(disable);
        buttonBackgroundImageImport.setDisable(disable);
        buttonBackgroundImageClear.setDisable(disable);
        sliderBackgroundImagePosX.setDisable(disable);
        textFieldBackgroundImagePosX.setDisable(disable);
        sliderBackgroundImagePosY.setDisable(disable);
        textFieldBackgroundImagePosY.setDisable(disable);

        buttonImageImport.setDisable(disable);
        buttonImageClear.setDisable(disable);
    }

    public boolean isAdvancedEnable() {
        return checkBoxAdvance.isSelected();
    }

    public void setAdvancedEnable(boolean enable) {
        checkBoxAdvance.setSelected(enable);
    }

    // UI Get & Set
    public VisualizeMode.View getView() {
        return choiceBoxEqualizerType.getValue();
    }

    public void setView(VisualizeMode.View view) {
        choiceBoxEqualizerType.setValue(view);
    }

    public VisualizeMode.Side getSide() {
        return choiceBoxEqualizerSide.getValue();
    }

    public void setSide(VisualizeMode.Side side) {
        choiceBoxEqualizerSide.setValue(side);
    }

    public VisualizeMode.Direct getDirect() {
        return choiceBoxEqualizerDirection.getValue();
    }

    public void setDirect(VisualizeMode.Direct direct) {
        choiceBoxEqualizerDirection.setValue(direct);
    }

    public VisualizeMode.Stereo getStereo() {
        return choiceBoxEqualizerStereo.getValue();
    }

    public void setStereo(VisualizeMode.Stereo stereo) {
        choiceBoxEqualizerStereo.setValue(stereo);
    }

    public int getBarNum() {
        return (int) sliderBarNum.getValue();
    }

    public void setBarNum(int barNum) {
        sliderBarNum.setValue(barNum);
    }

    public int getSize() {
        return (int) sliderSize.getValue();
    }

    public void setSize(int size) {
        sliderSize.setValue(size);
    }

    public int getGap() {
        return (int) sliderGap.getValue();
    }

    public void setGap(int gap) {
        sliderGap.setValue(gap);
    }

    public int getRadius() {
        return (int) sliderRadius.getValue();
    }

    public void setRadius(int radius) {
        sliderRadius.setValue(radius);
    }

    public double getPosX() {
        return sliderPosX.getValue();
    }

    public void setPosX(double posX) {
        sliderPosX.setValue(posX);
    }

    public double getPosY() {
        return sliderPosY.getValue();
    }

    public void setPosY(double posY) {
        sliderPosY.setValue(posY);
    }

    public double getRotation() {
        return sliderRotation.getValue();
    }

    public void setRotation(double rotation) {
        sliderRotation.setValue(rotation);
    }

    public Color getColor() {
        return colorPickerColor.getValue();
    }

    public void setColor(Color color) {
        colorPickerColor.setValue(color);
    }

    public Color getColorShadow() {
        return colorPickerColorShadow.getValue();
    }

    public void setColorShadow(Color colorShadow) {
        colorPickerColorShadow.setValue(colorShadow);
    }

    public int getColorShadowRadius() {
        return (int) sliderColorShadowRadius.getValue();
    }

    public void setColorShadowRadius(int colorShadowRadius) {
        sliderColorShadowRadius.setValue(colorShadowRadius);
    }

    public double getColorShadowSpread() {
        return sliderColorShadowSpread.getValue();
    }

    public void setColorShadowSpread(double colorShadowSpread) {
        sliderColorShadowSpread.setValue(colorShadowSpread);
    }

    public double getColorShadowOffsetX() {
        return sliderColorShadowOffsetX.getValue();
    }

    public void setColorShadowOffsetX(double colorShadowOffsetX) {
        sliderColorShadowOffsetX.setValue(colorShadowOffsetX);
    }

    public double getColorShadowOffsetY() {
        return sliderColorShadowOffsetY.getValue();
    }

    public void setColorShadowOffsetY(double colorShadowOffsetY) {
        sliderColorShadowOffsetY.setValue(colorShadowOffsetY);
    }

    public double getSensitivity() {
        return sliderSensitivity.getValue();
    }

    public void setSensitivity(double sensitivity) {
        sliderSensitivity.setValue(sensitivity);
    }

    public double getMinFreq() {
        return rangeSliderFreq.getLowValue();
    }

    public void setMinFreq(double minFreq) {
        rangeSliderFreq.setLowValue(minFreq);
    }

    public double getMaxFreq() {
        return rangeSliderFreq.getHighValue();
    }

    public void setMaxFreq(double maxFreq) {
        rangeSliderFreq.setHighValue(maxFreq);
    }

    public Color getBackgroundColor() {
        return colorPickerBackgroundColor.getValue();
    }

    public void setBackgroundColor(Color backgroundColor) {
        colorPickerBackgroundColor.setValue(backgroundColor);
    }

    public String getBackgroundImage() {
        return backgroundImageProperty.getValue();
    }

    public void setBackgroundImage(String backgroundImage) {
        groupBackgroundImagePos.getChildren().clear(); // 清空所有可能選單

        if (backgroundImage != null) {
            labelBackgroundImageName.setText(new File(backgroundImage).getName());

            int groupRow = 0;
            for (HBox param: groupBackgroundImagePosParam)
                groupBackgroundImagePos.add(param, 0, groupRow++, 2, 1);
        } else {
            labelBackgroundImageName.setText("");
        }

        backgroundImageProperty.setValue(backgroundImage);
    }

    public int getBackgroundImagePosX() {
        return (int) sliderBackgroundImagePosX.getValue();
    }

    public void setBackgroundImagePosX(int backgroundImagePosX) {
        sliderBackgroundImagePosX.setValue(backgroundImagePosX);
    }

    public int getBackgroundImagePosY() {
        return (int) sliderBackgroundImagePosY.getValue();
    }

    public void setBackgroundImagePosY(int backgroundImagePosY) {
        sliderBackgroundImagePosY.setValue(backgroundImagePosY);
    }

    public List<ImageFormat> getImageFormat() {
        return images.getImageFormat();
    }

    // Class
    private static class CustomSlider extends Slider {

        public CustomSlider (int min, int max, int value) {
            super(min, max, value);

            this.valueProperty().addListener((obs, oldValue, newValue) -> {
                try {
                    this.lookup(".track").setStyle(sliderTrackStyle((newValue.doubleValue() - getMin()) / (getMax() - getMin()) * 100));
                } catch (NullPointerException ignored) {
                    // 不做任何事
                }
            });
        }

        private String sliderTrackStyle(double value) {
            return String.format("-fx-background-color: linear-gradient(to right, lightseagreen %f%%, #444444 %f%%);", value, value);
        }

    }

    private static class CustomImageListView {

        private final double width;
        private final double height;

        private final List<GridPane> groupImageList;
        private final List<ImageFormat> fileList;

        public final BooleanProperty orderChangedProperty = new SimpleBooleanProperty(false);
        public final BooleanProperty imageFormatProperty = new SimpleBooleanProperty(false);

        // Constructor
        public CustomImageListView(double width, double height) {
            this.width = width;
            this.height = height;

            groupImageList = new ArrayList<>();
            fileList = new ArrayList<>();
            addEqualizer();
        }

        // Methods
        public void add(String filepath) {
            GridPane gridImages = new GridPane();
            groupImageList.add(gridImages);
            fileList.add(new ImageFormat(filepath, 0, 0, 0, 100, 100));

            // Selection
            Label labelImages = new Label(new File(filepath).getName());
            Label labelImagesNull1 = new Label();
            Label labelImagesNull2 = new Label();
            Button buttonMoveUps = new Button();
            Button buttonMoveDowns = new Button();
            ImageView imageViewMoveUps = new ImageView(new Image(new File(DefaultPath.ARROW_UP_ICON_PATH).toURI().toString()));
            ImageView imageViewMoveDowns = new ImageView(new Image(new File(DefaultPath.ARROW_DOWN_ICON_PATH).toURI().toString()));
            buttonMoveUps.setGraphic(imageViewMoveUps);
            buttonMoveDowns.setGraphic(imageViewMoveDowns);
            labelImages.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE) - 14);
            labelImagesNull1.setPrefWidth(14);
            labelImagesNull2.setPrefWidth(14);
            buttonMoveUps.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);
            buttonMoveDowns.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);

            HBox groupImages = new HBox(labelImages, labelImagesNull1, buttonMoveUps, labelImagesNull2, buttonMoveDowns);
            gridImages.add(groupImages, 0, 0);

            // Part
            GridPane gridImagesParam = new GridPane();
            gridImages.add(gridImagesParam, 0, 1, 2, 1);

            Label labelImagePosX = new Label(" └ Position X");
            Slider sliderImagePosX = new CustomSlider(0, 1920, 0);
            TextField textFieldImagePosX = new TextField(String.format("%d", 0));
            labelImagePosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
            sliderImagePosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
            textFieldImagePosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

            Label labelImagePosY = new Label(" └ Position Y");
            Slider sliderImagePosY = new CustomSlider(0, 1920, 0);
            TextField textFieldImagePosY = new TextField(String.format("%d", 0));
            labelImagePosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
            sliderImagePosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
            textFieldImagePosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

            Label labelImageRotation = new Label(" └ Rotation");
            Slider sliderImageRotation = new CustomSlider(0, 360, 0);
            TextField textFieldImageRotation = new TextField("0");
            labelImageRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
            sliderImageRotation.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
            textFieldImageRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

            Label labelImageScaleX = new Label(" └ Scale X");
            Slider sliderImageScaleX = new CustomSlider(0, 200, 100);
            TextField textFieldImageScaleX = new TextField("100");
            labelImageScaleX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
            sliderImageScaleX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
            textFieldImageScaleX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

            Label labelImageScaleY = new Label(" └ Scale Y");
            Slider sliderImageScaleY = new CustomSlider(0, 200, 100);
            TextField textFieldImageScaleY = new TextField("100");
            labelImageScaleY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
            sliderImageScaleY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
            textFieldImageScaleY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

            // Group
            HBox groupImagePosX = new HBox(labelImagePosX, sliderImagePosX, textFieldImagePosX);
            HBox groupImagePosY = new HBox(labelImagePosY, sliderImagePosY, textFieldImagePosY);
            HBox groupImageRotation = new HBox(labelImageRotation, sliderImageRotation, textFieldImageRotation);
            HBox groupImageScaleX = new HBox(labelImageScaleX, sliderImageScaleX, textFieldImageScaleX);
            HBox groupImageScaleY = new HBox(labelImageScaleY, sliderImageScaleY, textFieldImageScaleY);
            HBox[] groupImageParam = new HBox[] {groupImagePosX, groupImagePosY, groupImageRotation, groupImageScaleX, groupImageScaleY};

            // 上下按鈕隱藏
            // └ 上按鈕
            if (groupImageList.size() == 1) // 只有當是第一個的時候才隱藏
                buttonMoveUps.setVisible(false);
            // └ 下按鈕
            buttonMoveDowns.setVisible(false); // 新加入的必定隱藏，前一個則顯示出來
            if (groupImageList.size() != 1)
                ((HBox) groupImageList.get(groupImageList.indexOf(gridImages) - 1).getChildren().get(0)).getChildren().get(4).setVisible(true);

            // Event
            // └ Param
            gridImages.setOnMouseClicked(event -> {
                gridImagesParam.getChildren().clear(); // 清空所有可能選單
                labelImages.setStyle("-fx-text-fill: lightseagreen;");

                int groupRow = 0;
                for (HBox param: groupImageParam)
                    gridImagesParam.add(param, 0, groupRow++, 2, 1);
            });
            gridImages.setOnMouseExited(event -> {
                gridImagesParam.getChildren().clear();
                labelImages.setStyle("");
            });
            // └ Order
            buttonMoveUps.setOnAction(event -> {
                int index = groupImageList.indexOf(gridImages);

                if (index != 0) {
                    Collections.swap(groupImageList, index - 1, index);
                    Collections.swap(fileList, index - 1, index);
                    buttonShowAndHide();
                    orderChangedProperty.setValue(!orderChangedProperty.getValue()); // Property
                    imageFormatProperty.setValue(!imageFormatProperty.getValue());
                }
            });
            buttonMoveDowns.setOnAction(event -> {
                int index = groupImageList.indexOf(gridImages);

                if (index != groupImageList.size() - 1) {
                    Collections.swap(groupImageList, index, index + 1);
                    Collections.swap(fileList, index, index + 1);
                    buttonShowAndHide();
                    orderChangedProperty.setValue(!orderChangedProperty.getValue()); // Property
                    imageFormatProperty.setValue(!imageFormatProperty.getValue());
                }
            });
            // └ Slider
            //  └ Position X
            sliderImagePosX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImagePosX.textProperty().setValue(String.format("%d", newValue.intValue())));
            textFieldImagePosX.textProperty().addListener((obs, oldValue, newValue) -> {
                int value = textFieldStringToInt(textFieldImagePosX.getText(), (int) sliderImagePosX.getMax());
                sliderImagePosX.valueProperty().setValue(value);
                textFieldImagePosX.textProperty().setValue(String.format("%d", value));

                fileList.get(groupImageList.indexOf(gridImages)).setPosX(value);
                imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
            });
            //  └ Position Y
            sliderImagePosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImagePosY.textProperty().setValue(String.format("%d", newValue.intValue())));
            textFieldImagePosY.textProperty().addListener((obs, oldValue, newValue) -> {
                int value = textFieldStringToInt(textFieldImagePosY.getText(), (int) sliderImagePosY.getMax());
                sliderImagePosY.valueProperty().setValue(value);
                textFieldImagePosY.textProperty().setValue(String.format("%d", value));

                fileList.get(groupImageList.indexOf(gridImages)).setPosY(value);
                imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
            });
            //  └ Rotation
            sliderImageRotation.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageRotation.textProperty().setValue(String.format("%d", newValue.intValue())));
            textFieldImageRotation.textProperty().addListener((obs, oldValue, newValue) -> {
                int value = textFieldStringToInt(textFieldImageRotation.getText(), (int) sliderImageRotation.getMax());
                sliderImageRotation.valueProperty().setValue(value);
                textFieldImageRotation.textProperty().setValue(String.format("%d", value));

                fileList.get(groupImageList.indexOf(gridImages)).setRotation(value);
                imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
            });
            //  └ Scale X
            sliderImageScaleX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageScaleX.textProperty().setValue(String.format("%d", newValue.intValue())));
            textFieldImageScaleX.textProperty().addListener((obs, oldValue, newValue) -> {
                int value = textFieldStringToInt(textFieldImageScaleX.getText(), (int) sliderImageScaleX.getMax());
                sliderImageScaleX.valueProperty().setValue(value);
                textFieldImageScaleX.textProperty().setValue(String.format("%d", value));

                fileList.get(groupImageList.indexOf(gridImages)).setScaleX(value);
                imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
            });
            //  └ Scale Y
            sliderImageScaleY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageScaleY.textProperty().setValue(String.format("%d", newValue.intValue())));
            textFieldImageScaleY.textProperty().addListener((obs, oldValue, newValue) -> {
                int value = textFieldStringToInt(textFieldImageScaleY.getText(), (int) sliderImageScaleY.getMax());
                sliderImageScaleY.valueProperty().setValue(value);
                textFieldImageScaleY.textProperty().setValue(String.format("%d", value));

                fileList.get(groupImageList.indexOf(gridImages)).setScaleY(value);
                imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
            });

            // Initialize
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        }

        private void addEqualizer() {
            // Equalizer
            GridPane gridEqualizer = new GridPane();
            groupImageList.add(gridEqualizer);
            fileList.add(null);

            Label labelEqualizer = new Label("★ Equalizer");
            Label labelEqualizerNull1 = new Label();
            Label labelEqualizerNull2 = new Label();
            Button buttonMoveUps = new Button();
            Button buttonMoveDowns = new Button();
            labelEqualizer.setStyle("-fx-text-fill: #999999;");
            ImageView imageViewMoveUps = new ImageView(new Image(new File(DefaultPath.ARROW_UP_ICON_PATH).toURI().toString()));
            ImageView imageViewMoveDowns = new ImageView(new Image(new File(DefaultPath.ARROW_DOWN_ICON_PATH).toURI().toString()));
            buttonMoveUps.setGraphic(imageViewMoveUps);
            buttonMoveDowns.setGraphic(imageViewMoveDowns);
            buttonMoveUps.setVisible(false);
            buttonMoveDowns.setVisible(false);
            labelEqualizer.setPrefWidth(width * (WIDTH_OFFSET_LEFT + WIDTH_OFFSET_MIDDLE) - 14);
            labelEqualizerNull1.setPrefWidth(14);
            labelEqualizerNull2.setPrefWidth(14);
            buttonMoveUps.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);
            buttonMoveDowns.setPrefWidth(width * WIDTH_OFFSET_RIGHT * .5 - 7);

            HBox groupImages = new HBox(labelEqualizer, labelEqualizerNull1, buttonMoveUps, labelEqualizerNull2, buttonMoveDowns);
            gridEqualizer.add(groupImages, 0, 0);
            GridPane gridImagesParam = new GridPane(); // null
            gridEqualizer.add(gridImagesParam, 0, 1, 2, 1);

            buttonMoveUps.setOnAction(event -> {
                int index = groupImageList.indexOf(gridEqualizer);

                if (index != 0) {
                    Collections.swap(groupImageList, index - 1, index);
                    Collections.swap(fileList, index - 1, index);
                    buttonShowAndHide();
                    orderChangedProperty.setValue(!orderChangedProperty.getValue()); // Property
                    imageFormatProperty.setValue(!imageFormatProperty.getValue());
                }
            });
            buttonMoveDowns.setOnAction(event -> {
                int index = groupImageList.indexOf(gridEqualizer);

                if (index != groupImageList.size() - 1) {
                    Collections.swap(groupImageList, index, index + 1);
                    Collections.swap(fileList, index, index + 1);
                    buttonShowAndHide();
                    orderChangedProperty.setValue(!orderChangedProperty.getValue()); // Property
                    imageFormatProperty.setValue(!imageFormatProperty.getValue());
                }
            });
        }

        public void clear() {
            groupImageList.clear();
            fileList.clear();
            imageFormatProperty.setValue(!imageFormatProperty.getValue());
            addEqualizer();
        }

        private void buttonShowAndHide() {
            // 上按鈕，無論如何第一一定隱藏，之後一定顯示
            ((HBox) groupImageList.get(0).getChildren().get(0)).getChildren().get(2).setVisible(false);
            ((HBox) groupImageList.get(1).getChildren().get(0)).getChildren().get(2).setVisible(true);

            // 下按鈕，無論如何最後一定隱藏，之前一定顯示
            ((HBox) groupImageList.get(groupImageList.size() - 1).getChildren().get(0)).getChildren().get(4).setVisible(false);
            ((HBox) groupImageList.get(groupImageList.size() - 2).getChildren().get(0)).getChildren().get(4).setVisible(true);
        }

        public List<GridPane> getGridPane() {
            return groupImageList;
        }

        public List<ImageFormat> getImageFormat() {
            return fileList;
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

}
