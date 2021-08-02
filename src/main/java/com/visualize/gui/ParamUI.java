package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;
import com.visualize.object.*;
import com.visualize.gui.command.*;

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

import javafx.event.Event;
import javafx.scene.input.MouseEvent;

import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javafx.collections.FXCollections;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;
import java.util.List;

public class ParamUI extends ScrollPane {

    public static ParamUI paramUI;

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
    private final CommandManager commandManager;

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
    private final IntegerProperty rangeWidthProperty = new SimpleIntegerProperty();
    private final IntegerProperty rangeHeightProperty = new SimpleIntegerProperty();

    private final ObjectProperty<VisualizeMode.View> equalizerTypeProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<VisualizeMode.Side> equalizerSideProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<VisualizeMode.Direct> equalizerDirectionProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<VisualizeMode.Stereo> equalizerStereoProperty = new SimpleObjectProperty<>();

    private final IntegerProperty barNumberProperty = new SimpleIntegerProperty();
    private final IntegerProperty sizeProperty = new SimpleIntegerProperty();
    private final IntegerProperty rotationProperty = new SimpleIntegerProperty();
    private final IntegerProperty gapProperty = new SimpleIntegerProperty();
    private final IntegerProperty radiusProperty = new SimpleIntegerProperty();
    private final IntegerProperty positionXProperty = new SimpleIntegerProperty();
    private final IntegerProperty positionYProperty = new SimpleIntegerProperty();
    private final IntegerProperty sensitivityProperty = new SimpleIntegerProperty();
    private final IntegerProperty minFrequencyProperty = new SimpleIntegerProperty();
    private final IntegerProperty maxFrequencyProperty = new SimpleIntegerProperty();

    private final ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Color> colorShadowProperty = new SimpleObjectProperty<>();
    private final IntegerProperty colorShadowRadiusProperty = new SimpleIntegerProperty();
    private final IntegerProperty colorShadowSpreadProperty = new SimpleIntegerProperty();
    private final IntegerProperty colorShadowOffsetXProperty = new SimpleIntegerProperty();
    private final IntegerProperty colorShadowOffsetYProperty = new SimpleIntegerProperty();

    private final ObjectProperty<Color> backgroundColorProperty = new SimpleObjectProperty<>();
    private final StringProperty backgroundImageProperty = new SimpleStringProperty(null);
    private final IntegerProperty backgroundImagePositionXProperty = new SimpleIntegerProperty();
    private final IntegerProperty backgroundImagePositionYProperty = new SimpleIntegerProperty();

    //private final IntegerProperty channelsProperty = new SimpleIntegerProperty(); // 影響 Stereo
    //private final DoubleProperty frameRateProperty = new SimpleDoubleProperty(); // 影響 Frequency

    private final BooleanProperty imageFormatProperty;

    public ParamUI(double width, double height, int rangeWidth, int rangeHeight) {
        this.width = width;
        this.height = height;
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.rangeWidthProperty.setValue(rangeWidth);
        this.rangeHeightProperty.setValue(rangeHeight);
        this.images = new CustomImageListView(width, height, rangeWidth, rangeHeight);
        this.imageFormatProperty = images.imageFormatProperty();

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
        this.commandManager = new CommandManager();

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
        sliderRadius = new CustomSlider(0, rangeWidth, 0);
        textFieldRadius = new TextField("0");
        labelRadius.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderRadius.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldRadius.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderRadius.maxProperty().bind(rangeWidthProperty);

        Label labelPosX = new Label("Position X");
        sliderPosX = new CustomSlider(-rangeWidth, 2 * rangeWidth, -rangeWidth);
        textFieldPosX = new TextField(String.format("%d", -rangeWidth));
        labelPosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderPosX.minProperty().bind(rangeWidthProperty.multiply(-1));
        sliderPosX.maxProperty().bind(rangeWidthProperty.multiply(2));

        Label labelPosY = new Label("Position Y");
        sliderPosY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldPosY = new TextField(String.format("%d", -rangeHeight));
        labelPosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderPosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldPosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderPosY.minProperty().bind(rangeHeightProperty.multiply(-1));
        sliderPosY.maxProperty().bind(rangeHeightProperty.multiply(2));

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
        sliderColorShadowOffsetX.minProperty().bind(rangeWidthProperty.multiply(-1));
        sliderColorShadowOffsetX.maxProperty().bind(rangeWidthProperty.multiply(2));

        Label labelColorShadowOffsetY = new Label(" └ Offset Y");
        sliderColorShadowOffsetY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldColorShadowOffsetY = new TextField(String.format("%d", -rangeHeight));
        labelColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldColorShadowOffsetY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderColorShadowOffsetY.minProperty().bind(rangeHeightProperty.multiply(-1));
        sliderColorShadowOffsetY.maxProperty().bind(rangeHeightProperty.multiply(2));

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
        sliderBackgroundImagePosX.minProperty().bind(rangeWidthProperty.multiply(-1));
        sliderBackgroundImagePosX.maxProperty().bind(rangeWidthProperty.multiply(2));

        Label labelBackgroundImagePosY = new Label(" └ Position Y");
        sliderBackgroundImagePosY = new CustomSlider(-rangeHeight, 2 * rangeHeight, -rangeHeight);
        textFieldBackgroundImagePosY = new TextField(String.format("%d", -rangeHeight));
        labelBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldBackgroundImagePosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderBackgroundImagePosY.minProperty().bind(rangeHeightProperty.multiply(-1));
        sliderBackgroundImagePosY.maxProperty().bind(rangeHeightProperty.multiply(2));

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

        ProjectFormat commandFormat = new ProjectFormat(
                null, rangeWidth, rangeHeight, isAdvancedEnable(),
                getView(), getSide(), getDirect(), getStereo(), null,
                getBarNum(), getSize(), getGap(), getRadius(), getPosX(), getPosY(), getRotation(),
                getColor(), getColorShadow(), getColorShadowRadius(), getColorShadowSpread(), getColorShadowOffsetX(), getColorShadowOffsetY(),
                getSensitivity(), getMinFreq(), getMaxFreq(),
                getBackgroundColor(), getBackgroundImage(), getBackgroundImagePosX(), getBackgroundImagePosY(),
                getImageFormat());
        // Event
        // └ this
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.hvalueProperty().addListener((obs, oldValue, newValue) -> this.setHvalue(0)); // 清除圖層改變時造成的偏移
        this.setOnMouseEntered(event -> this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED));
        this.setOnMouseExited(event -> this.setVbarPolicy(ScrollBarPolicy.NEVER));
        // └ Equalizer
        //  └ Choice Box
        //   └ Equalizer Type
        choiceBoxEqualizerType.setOnAction(event -> {
            VisualizeMode.View type = choiceBoxEqualizerType.getValue();
            equalizerTypeProperty.setValue(type); // Property

            groupEqualizer.getChildren().clear(); // 清空所有可能選單
            int groupRow = 0;
            for (int index: GROUP_EQUALIZER_INDEX[type.value()])
                groupEqualizer.add(groupEqualizerParam[index], 0, groupRow++, 2, 1);
        });
        choiceBoxEqualizerType.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setView(choiceBoxEqualizerType.getValue());
            else // 更改後
                if (commandFormat.getView() != choiceBoxEqualizerType.getValue())
                    commandManager.execute(new EqualizerTypeCommand(commandFormat.getView(), choiceBoxEqualizerType.getValue()));
        });
        //   └ Equalizer Side
        choiceBoxEqualizerSide.setOnAction(event -> {
            VisualizeMode.Side side = choiceBoxEqualizerSide.getValue();
            equalizerSideProperty.setValue(side); // Property
        });
        choiceBoxEqualizerSide.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setSide(choiceBoxEqualizerSide.getValue());
            else // 更改後
                if (commandFormat.getSide() != choiceBoxEqualizerSide.getValue())
                    commandManager.execute(new EqualizerSideCommand(commandFormat.getSide(), choiceBoxEqualizerSide.getValue()));
        });
        //   └ Equalizer Direction
        choiceBoxEqualizerDirection.setOnAction(event -> {
            VisualizeMode.Direct direct = choiceBoxEqualizerDirection.getValue();
            equalizerDirectionProperty.setValue(direct); // Property
        });
        choiceBoxEqualizerDirection.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setDirect(choiceBoxEqualizerDirection.getValue());
            else // 更改後
                if (commandFormat.getDirect() != choiceBoxEqualizerDirection.getValue())
                    commandManager.execute(new EqualizerDirectCommand(commandFormat.getDirect(), choiceBoxEqualizerDirection.getValue()));
        });
        //   └ Equalizer Stereo
        choiceBoxEqualizerStereo.valueProperty().addListener((obs, oldValue, newValue) -> {
            VisualizeMode.Stereo stereo = (newValue == null ? oldValue : newValue);
            equalizerStereoProperty.setValue(stereo); // Property
        });
        choiceBoxEqualizerStereo.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setStereo(choiceBoxEqualizerStereo.getValue());
            else // 更改後
                if (commandFormat.getStereo() != choiceBoxEqualizerStereo.getValue())
                    commandManager.execute(new EqualizerStereoCommand(commandFormat.getStereo(), choiceBoxEqualizerStereo.getValue()));
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

                colorShadowProperty.setValue(colorPickerColorShadow.getValue());
                sensitivityProperty.setValue(sliderSensitivity.getValue());
                minFrequencyProperty.setValue(rangeSliderFreq.getLowValue());
                maxFrequencyProperty.setValue(rangeSliderFreq.getHighValue());
                //commandManager.execute(new AdvanceCommand(false, true));
            }
            else { // 保留內部資料，重製音波資料，因為處於未啟用狀態
                // 預設值
                colorShadowProperty.setValue(Color.rgb(0, 0, 0, 0));
                sensitivityProperty.setValue(50);
                minFrequencyProperty.setValue(0);
                maxFrequencyProperty.setValue(24000);
                //commandManager.execute(new AdvanceCommand(true, false));
            }
        });
        //  └ Slider
        //   └ Bar Number
        sliderBarNum.valueProperty().addListener((obs, oldValue, newValue) -> textFieldBarNum.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldBarNum.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBarNum.getText(), (int) sliderBarNum.getMin(), (int) sliderBarNum.getMax());
            sliderBarNum.valueProperty().setValue(value);
            textFieldBarNum.textProperty().setValue(String.format("%d", value));
            barNumberProperty.setValue(value); // Property
        });
        sliderBarNum.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setBarNum((int) sliderBarNum.getValue());
            else // 更改後
                if (commandFormat.getBarNum() != (int) sliderBarNum.getValue())
                    commandManager.execute(new BarNumCommand(commandFormat.getBarNum(), (int) sliderBarNum.getValue()));
        });
        sliderBarNum.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderBarNum.setValueChanging(true));
        sliderBarNum.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderBarNum.setValueChanging(false));
        textFieldBarNum.focusedProperty().addListener((obs, oldValue, newValue) -> sliderBarNum.setValueChanging(newValue));
        //   └ Size
        sliderSize.valueProperty().addListener((obs, oldValue, newValue) -> textFieldSize.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldSize.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSize.getText(), (int) sliderSize.getMin(), (int) sliderSize.getMax());
            sliderSize.valueProperty().setValue(value);
            textFieldSize.textProperty().setValue(String.format("%d", value));
            sizeProperty.setValue(value); // Property
        });
        sliderSize.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setSize((int) sliderSize.getValue());
            else // 更改後
                if (commandFormat.getSize() != (int) sliderSize.getValue())
                    commandManager.execute(new SizeCommand(commandFormat.getSize(), (int) sliderSize.getValue()));
        });
        sliderSize.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderSize.setValueChanging(true));
        sliderSize.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderSize.setValueChanging(false));
        textFieldSize.focusedProperty().addListener((obs, oldValue, newValue) -> sliderSize.setValueChanging(newValue));
        //   └ Rotation
        sliderRotation.valueProperty().addListener((obs, oldValue, newValue) -> textFieldRotation.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldRotation.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRotation.getText(), (int) sliderRotation.getMin(), (int) sliderRotation.getMax());
            sliderRotation.valueProperty().setValue(value);
            textFieldRotation.textProperty().setValue(String.format("%d", value));
            rotationProperty.setValue(value); // Property
        });
        sliderRotation.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setRotation(sliderRotation.getValue());
            else // 更改後
                if (commandFormat.getRotation() != sliderRotation.getValue())
                    commandManager.execute(new RotationCommand(commandFormat.getRotation(), sliderRotation.getValue()));
        });
        sliderRotation.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderRotation.setValueChanging(true));
        sliderRotation.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderRotation.setValueChanging(false));
        textFieldRotation.focusedProperty().addListener((obs, oldValue, newValue) -> sliderRotation.setValueChanging(newValue));
        //   └ Gap
        sliderGap.valueProperty().addListener((obs, oldValue, newValue) -> textFieldGap.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldGap.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldGap.getText(), (int) sliderGap.getMin(), (int) sliderGap.getMax());
            sliderGap.valueProperty().setValue(value);
            textFieldGap.textProperty().setValue(String.format("%d", value));
            gapProperty.setValue(value); // Property
        });
        sliderGap.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setGap((int) sliderGap.getValue());
            else // 更改後
                if (commandFormat.getGap() != (int) sliderGap.getValue())
                    commandManager.execute(new GapCommand(commandFormat.getGap(), (int) sliderGap.getValue()));
        });
        sliderGap.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderGap.setValueChanging(true));
        sliderGap.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderGap.setValueChanging(false));
        textFieldGap.focusedProperty().addListener((obs, oldValue, newValue) -> sliderGap.setValueChanging(newValue));
        //   └ Radius
        sliderRadius.valueProperty().addListener((obs, oldValue, newValue) -> textFieldRadius.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldRadius.getText(), (int) sliderRadius.getMin(), (int) sliderRadius.getMax());
            sliderRadius.valueProperty().setValue(value);
            textFieldRadius.textProperty().setValue(String.format("%d", value));
            radiusProperty.setValue(value); // Property
        });
        sliderRadius.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setRadius((int) sliderRadius.getValue());
            else // 更改後
                if (commandFormat.getRadius() != (int) sliderRadius.getValue())
                    commandManager.execute(new RadiusCommand(commandFormat.getRadius(), (int) sliderRadius.getValue()));
        });
        sliderRadius.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderRadius.setValueChanging(true));
        sliderRadius.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderRadius.setValueChanging(false));
        textFieldRadius.focusedProperty().addListener((obs, oldValue, newValue) -> sliderRadius.setValueChanging(newValue));
        //   └ Position X
        sliderPosX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldPosX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldPosX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosX.getText(), (int) sliderPosX.getMin(), (int) sliderPosX.getMax());
            sliderPosX.valueProperty().setValue(value);
            textFieldPosX.textProperty().setValue(String.format("%d", value));
            positionXProperty.setValue(value); // Property
        });
        sliderPosX.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setPosX(sliderPosX.getValue());
            else // 更改後
                if (commandFormat.getPosX() != sliderPosX.getValue())
                    commandManager.execute(new PosXCommand(commandFormat.getPosX(), sliderPosX.getValue()));
        });
        sliderPosX.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderPosX.setValueChanging(true));
        sliderPosX.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderPosX.setValueChanging(false));
        textFieldPosX.focusedProperty().addListener((obs, oldValue, newValue) -> sliderPosX.setValueChanging(newValue));
        //   └ Position Y
        sliderPosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldPosY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldPosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldPosY.getText(), (int) sliderPosY.getMin(), (int) sliderPosY.getMax());
            sliderPosY.valueProperty().setValue(value);
            textFieldPosY.textProperty().setValue(String.format("%d", value));
            positionYProperty.setValue(value); // Property
        });
        sliderPosY.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setPosY(sliderPosY.getValue());
            else // 更改後
                if (commandFormat.getPosY() != sliderPosY.getValue())
                    commandManager.execute(new PosYCommand(commandFormat.getPosY(), sliderPosY.getValue()));
        });
        sliderPosY.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderPosY.setValueChanging(true));
        sliderPosY.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderPosY.setValueChanging(false));
        textFieldPosY.focusedProperty().addListener((obs, oldValue, newValue) -> sliderPosY.setValueChanging(newValue));
        //   └ Sensitivity
        sliderSensitivity.valueProperty().addListener((obs, oldValue, newValue) -> textFieldSensitivity.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldSensitivity.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldSensitivity.getText(), (int) sliderSensitivity.getMin(), (int) sliderSensitivity.getMax());
            sliderSensitivity.valueProperty().setValue(value);
            textFieldSensitivity.textProperty().setValue(String.format("%d", value));
            sensitivityProperty.setValue(value); // Property
        });
        sliderSensitivity.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setSensitivity(sliderSensitivity.getValue());
            else // 更改後
                if (commandFormat.getSensitivity() != sliderSensitivity.getValue())
                    commandManager.execute(new SensitivityCommand(commandFormat.getSensitivity(), sliderSensitivity.getValue()));
        });
        sliderSensitivity.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderSensitivity.setValueChanging(true));
        sliderSensitivity.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderSensitivity.setValueChanging(false));
        textFieldSensitivity.focusedProperty().addListener((obs, oldValue, newValue) -> sliderSensitivity.setValueChanging(newValue));
        //   └ Frequency
        rangeSliderFreq.lowValueProperty().addListener((obs, oldValue, newValue) -> textFieldMinFreq.textProperty().setValue(String.format("%d", newValue.intValue())));
        rangeSliderFreq.highValueProperty().addListener((obs, oldValue, newValue) -> textFieldMaxFreq.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldMinFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMinFreq.getText(), (int) rangeSliderFreq.getMin(), (int) rangeSliderFreq.getMax());
            rangeSliderFreq.lowValueProperty().setValue(value);
            textFieldMinFreq.textProperty().setValue(String.format("%d", value));
            minFrequencyProperty.setValue(value); // Property
        });
        textFieldMaxFreq.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldMaxFreq.getText(), (int) rangeSliderFreq.getMin(), (int) rangeSliderFreq.getMax());
            rangeSliderFreq.highValueProperty().setValue(value);
            textFieldMaxFreq.textProperty().setValue(String.format("%d", value));
            maxFrequencyProperty.setValue(value); // Property
        });
        rangeSliderFreq.lowValueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setMinFreq(rangeSliderFreq.getLowValue());
            else // 更改後
                if (commandFormat.getMinFreq() != rangeSliderFreq.getLowValue())
                    commandManager.execute(new MinFreqCommand(commandFormat.getMinFreq(), rangeSliderFreq.getLowValue()));
        });
        rangeSliderFreq.highValueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setMaxFreq(rangeSliderFreq.getHighValue());
            else // 更改後
                if (commandFormat.getMaxFreq() != rangeSliderFreq.getHighValue())
                    commandManager.execute(new MaxFreqCommand(commandFormat.getMaxFreq(), rangeSliderFreq.getHighValue()));
        });
        rangeSliderFreq.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            rangeSliderFreq.setLowValueChanging(true);
            rangeSliderFreq.setHighValueChanging(true);
        });
        rangeSliderFreq.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            rangeSliderFreq.setLowValueChanging(false);
            rangeSliderFreq.setHighValueChanging(false);
        });
        textFieldMinFreq.focusedProperty().addListener((obs, oldValue, newValue) -> rangeSliderFreq.setLowValueChanging(newValue));
        textFieldMaxFreq.focusedProperty().addListener((obs, oldValue, newValue) -> rangeSliderFreq.setHighValueChanging(newValue));
        //   └ Color Shadow Radius
        sliderColorShadowRadius.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowRadius.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowRadius.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowRadius.getText(), (int) sliderColorShadowRadius.getMin(), (int) sliderColorShadowRadius.getMax());
            sliderColorShadowRadius.valueProperty().setValue(value);
            textFieldColorShadowRadius.textProperty().setValue(String.format("%d", value));
            colorShadowRadiusProperty.setValue(value); // Property
        });
        sliderColorShadowRadius.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColorShadowRadius((int) sliderColorShadowRadius.getValue());
            else // 更改後
                if (commandFormat.getColorShadowRadius() != (int) sliderColorShadowRadius.getValue())
                    commandManager.execute(new ColorShadowRadiusCommand(commandFormat.getColorShadowRadius(), (int) sliderColorShadowRadius.getValue()));
        });
        sliderColorShadowRadius.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderColorShadowRadius.setValueChanging(true));
        sliderColorShadowRadius.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderColorShadowRadius.setValueChanging(false));
        textFieldColorShadowRadius.focusedProperty().addListener((obs, oldValue, newValue) -> sliderColorShadowRadius.setValueChanging(newValue));
        //   └ Color Shadow Spread
        sliderColorShadowSpread.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowSpread.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowSpread.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowSpread.getText(), (int) sliderColorShadowSpread.getMin(), (int) sliderColorShadowSpread.getMax());
            sliderColorShadowSpread.valueProperty().setValue(value);
            textFieldColorShadowSpread.textProperty().setValue(String.format("%d", value));
            colorShadowSpreadProperty.setValue(value); // Property
        });
        sliderColorShadowSpread.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColorShadowSpread(sliderColorShadowSpread.getValue());
            else // 更改後
                if (commandFormat.getColorShadowSpread() != sliderColorShadowSpread.getValue())
                    commandManager.execute(new ColorShadowSpreadCommand(commandFormat.getColorShadowSpread(), sliderColorShadowSpread.getValue()));
        });
        sliderColorShadowSpread.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderColorShadowSpread.setValueChanging(true));
        sliderColorShadowSpread.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderColorShadowSpread.setValueChanging(false));
        textFieldColorShadowSpread.focusedProperty().addListener((obs, oldValue, newValue) -> sliderColorShadowSpread.setValueChanging(newValue));
        //   └ Color Shadow Offset X
        sliderColorShadowOffsetX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowOffsetX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetX.getText(), (int) sliderColorShadowOffsetX.getMin(), (int) sliderColorShadowOffsetX.getMax());
            sliderColorShadowOffsetX.valueProperty().setValue(value);
            textFieldColorShadowOffsetX.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetXProperty.setValue(value); // Property
        });
        sliderColorShadowOffsetX.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColorShadowOffsetX(sliderColorShadowOffsetX.getValue());
            else // 更改後
                if (commandFormat.getColorShadowOffsetX() != sliderColorShadowOffsetX.getValue())
                    commandManager.execute(new ColorShadowOffsetXCommand(commandFormat.getColorShadowOffsetX(), sliderColorShadowOffsetX.getValue()));
        });
        sliderColorShadowOffsetX.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderColorShadowOffsetX.setValueChanging(true));
        sliderColorShadowOffsetX.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderColorShadowOffsetX.setValueChanging(false));
        textFieldColorShadowOffsetX.focusedProperty().addListener((obs, oldValue, newValue) -> sliderColorShadowOffsetX.setValueChanging(newValue));
        //   └ Color Shadow Offset Y
        sliderColorShadowOffsetY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldColorShadowOffsetY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldColorShadowOffsetY.getText(), (int) sliderColorShadowOffsetY.getMin(), (int) sliderColorShadowOffsetY.getMax());
            sliderColorShadowOffsetY.valueProperty().setValue(value);
            textFieldColorShadowOffsetY.textProperty().setValue(String.format("%d", value));
            colorShadowOffsetYProperty.setValue(value); // Property
        });
        sliderColorShadowOffsetY.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColorShadowOffsetY(sliderColorShadowOffsetY.getValue());
            else // 更改後
                if (commandFormat.getColorShadowOffsetY() != sliderColorShadowOffsetY.getValue())
                    commandManager.execute(new ColorShadowOffsetYCommand(commandFormat.getColorShadowOffsetY(), sliderColorShadowOffsetY.getValue()));
        });
        sliderColorShadowOffsetY.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderColorShadowOffsetY.setValueChanging(true));
        sliderColorShadowOffsetY.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderColorShadowOffsetY.setValueChanging(false));
        textFieldColorShadowOffsetY.focusedProperty().addListener((obs, oldValue, newValue) -> sliderColorShadowOffsetY.setValueChanging(newValue));
        //  └ Color Picker
        //   └ Color
        colorPickerColor.valueProperty().addListener((obs, oldValue, newValue) -> colorProperty.setValue(newValue));
        colorPickerColor.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColor(colorPickerColor.getValue());
            else // 更改後
                if (commandFormat.getColor() != colorPickerColor.getValue())
                    commandManager.execute(new ColorCommand(commandFormat.getColor(), colorPickerColor.getValue()));
        });
        //   └ Color Shadow
        colorPickerColorShadow.valueProperty().addListener((obs, oldValue, newValue) -> colorShadowProperty.setValue(newValue));
        colorPickerColorShadow.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setColorShadow(colorPickerColorShadow.getValue());
            else // 更改後
                if (commandFormat.getColorShadow() != colorPickerColorShadow.getValue())
                    commandManager.execute(new ColorShadowCommand(commandFormat.getColorShadow(), colorPickerColorShadow.getValue()));
        });
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
            int value = textFieldStringToInt(textFieldBackgroundImagePosX.getText(), (int) sliderBackgroundImagePosX.getMin(), (int) sliderBackgroundImagePosX.getMax());
            sliderBackgroundImagePosX.valueProperty().setValue(value);
            textFieldBackgroundImagePosX.textProperty().setValue(String.format("%d", value));
            backgroundImagePositionXProperty.setValue(value); // Property
        });
        sliderBackgroundImagePosX.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setBackgroundImagePosX((int) sliderBackgroundImagePosX.getValue());
            else // 更改後
                if (commandFormat.getBackgroundImagePosX() != (int) sliderBackgroundImagePosX.getValue())
                    commandManager.execute(new BackgroundImagePosXCommand(commandFormat.getBackgroundImagePosX(), (int) sliderBackgroundImagePosX.getValue()));
        });
        sliderBackgroundImagePosX.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderBackgroundImagePosX.setValueChanging(true));
        sliderBackgroundImagePosX.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderBackgroundImagePosX.setValueChanging(false));
        textFieldBackgroundImagePosX.focusedProperty().addListener((obs, oldValue, newValue) -> sliderBackgroundImagePosX.setValueChanging(newValue));
        //   └ Position Y
        sliderBackgroundImagePosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldBackgroundImagePosY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldBackgroundImagePosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldBackgroundImagePosY.getText(), (int) sliderBackgroundImagePosY.getMin(), (int) sliderBackgroundImagePosY.getMax());
            sliderBackgroundImagePosY.valueProperty().setValue(value);
            textFieldBackgroundImagePosY.textProperty().setValue(String.format("%d", value));
            backgroundImagePositionYProperty.setValue(value); // Property
        });
        sliderBackgroundImagePosY.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setBackgroundImagePosY((int) sliderBackgroundImagePosY.getValue());
            else // 更改後
                if (commandFormat.getBackgroundImagePosY() != (int) sliderBackgroundImagePosY.getValue())
                    commandManager.execute(new BackgroundImagePosYCommand(commandFormat.getBackgroundImagePosY(), (int) sliderBackgroundImagePosY.getValue()));
        });
        sliderBackgroundImagePosY.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderBackgroundImagePosY.setValueChanging(true));
        sliderBackgroundImagePosY.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderBackgroundImagePosY.setValueChanging(false));
        textFieldBackgroundImagePosY.focusedProperty().addListener((obs, oldValue, newValue) -> sliderBackgroundImagePosY.setValueChanging(newValue));
        //  └ Color Picker
        //   └ Background Color
        colorPickerBackgroundColor.valueProperty().addListener((obs, oldValue, newValue) -> backgroundColorProperty.setValue(newValue));
        colorPickerBackgroundColor.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 更改前
                commandFormat.setBackgroundColor(colorPickerBackgroundColor.getValue());
            else // 更改後
                if (commandFormat.getBackgroundColor() != colorPickerBackgroundColor.getValue())
                    commandManager.execute(new BackgroundColorCommand(commandFormat.getBackgroundColor(), colorPickerBackgroundColor.getValue()));
        });
        // └ Image
        //  └ Button
        //   └ Image
        buttonImageImport.setOnAction(event -> {
            try {
                List<File> files = fileChooser.showOpenMultipleDialog(null);
                fileChooser.setInitialDirectory(new File(files.get(0).getParent()));

                groupImage.getChildren().clear(); // 清空所有可能選單

                if (images.getImageFormat().size() == 0)
                    images.addEqualizer();

                for (File file: files)
                    images.add(file.getAbsolutePath(), 0, 0, 0, 100, 100);

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
        images.orderChangedProperty().addListener(event -> {
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
    private int textFieldStringToInt(String value, int min, int max) {
        int result;
        try {
            result = Math.max(Math.min(Integer.parseInt(value), max), min);
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

        rangeWidthProperty.setValue(width);
        images.setRangeWidth(width);
    }

    public void setRangeHeight(int height) {
        this.rangeHeight = height;

        rangeHeightProperty.setValue(height);
        images.setRangeHeight(height);
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

    public void undo() {
        try {
            commandManager.undo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void redo() {
        try {
            commandManager.redo();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
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

    public void setImageFormat(List<ImageFormat> imageFormat) {
        images.clear();
        groupImage.getChildren().clear();

        for (ImageFormat format: imageFormat) {
            if (format == null)
                images.addEqualizer();
            else
                images.add(format.getFilepath(), format.getPosX(), format.getPosY(), format.getRotation(), format.getScaleX(), format.getScaleY());
        }

        int groupRow = 0;
        for (GridPane param: images.getGridPane())
            groupImage.add(param, 0, groupRow++, 2, 1);
    }

    // Property
    public ObjectProperty<VisualizeMode.View> equalizerTypeProperty() {
        return equalizerTypeProperty;
    }

    public ObjectProperty<VisualizeMode.Side> equalizerSideProperty() {
        return equalizerSideProperty;
    }

    public ObjectProperty<VisualizeMode.Direct> equalizerDirectionProperty() {
        return equalizerDirectionProperty;
    }

    public ObjectProperty<VisualizeMode.Stereo> equalizerStereoProperty() {
        return equalizerStereoProperty;
    }

    public IntegerProperty barNumberProperty() {
        return barNumberProperty;
    }

    public IntegerProperty sizeProperty() {
        return sizeProperty;
    }

    public IntegerProperty rotationProperty() {
        return rotationProperty;
    }

    public IntegerProperty gapProperty() {
        return gapProperty;
    }

    public IntegerProperty radiusProperty() {
        return radiusProperty;
    }

    public IntegerProperty positionXProperty() {
        return positionXProperty;
    }

    public IntegerProperty positionYProperty() {
        return positionYProperty;
    }

    public IntegerProperty sensitivityProperty() {
        return sensitivityProperty;
    }

    public IntegerProperty minFrequencyProperty() {
        return minFrequencyProperty;
    }

    public IntegerProperty maxFrequencyProperty() {
        return maxFrequencyProperty;
    }

    public ObjectProperty<Color> colorProperty() {
        return colorProperty;
    }

    public ObjectProperty<Color> colorShadowProperty() {
        return colorShadowProperty;
    }

    public IntegerProperty colorShadowRadiusProperty() {
        return colorShadowRadiusProperty;
    }

    public IntegerProperty colorShadowSpreadProperty() {
        return colorShadowSpreadProperty;
    }

    public IntegerProperty colorShadowOffsetXProperty() {
        return colorShadowOffsetXProperty;
    }

    public IntegerProperty colorShadowOffsetYProperty() {
        return colorShadowOffsetYProperty;
    }

    public ObjectProperty<Color> backgroundColorProperty() {
        return backgroundColorProperty;
    }

    public StringProperty backgroundImageProperty() {
        return backgroundImageProperty;
    }

    public IntegerProperty backgroundImagePositionXProperty() {
        return backgroundImagePositionXProperty;
    }

    public IntegerProperty backgroundImagePositionYProperty() {
        return backgroundImagePositionYProperty;
    }

    public BooleanProperty imageFormatProperty() {
        return imageFormatProperty;
    }

}
