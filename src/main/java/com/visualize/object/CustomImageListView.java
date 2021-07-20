package com.visualize.object;

import com.visualize.file.DefaultPath;
import com.visualize.view.ImageFormat;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomImageListView {

    private final double width;
    private final double height;
    private int rangeWidth;
    private int rangeHeight;

    private static final double WIDTH_OFFSET_LEFT = .35;
    private static final double WIDTH_OFFSET_MIDDLE = .48;
    private static final double WIDTH_OFFSET_RIGHT = .17;

    private final List<GridPane> groupImageList;
    private final List<ImageFormat> fileList;

    // Property
    private final IntegerProperty rangeWidthProperty = new SimpleIntegerProperty();
    private final IntegerProperty rangeHeightProperty = new SimpleIntegerProperty();

    public final BooleanProperty orderChangedProperty = new SimpleBooleanProperty(false);
    public final BooleanProperty imageFormatProperty = new SimpleBooleanProperty(false);

    // Constructor
    public CustomImageListView(double width, double height, int rangeWidth, int rangeHeight) {
        this.width = width;
        this.height = height;
        this.rangeWidth = rangeWidth;
        this.rangeHeight = rangeHeight;
        this.rangeWidthProperty.setValue(rangeWidth);
        this.rangeHeightProperty.setValue(rangeHeight);

        groupImageList = new ArrayList<>();
        fileList = new ArrayList<>();
    }

    // Methods
    public void add(String filepath, double posX, double posY, double rotation, double scaleX, double scaleY) {
        GridPane gridImages = new GridPane();
        groupImageList.add(gridImages);
        fileList.add(new ImageFormat(filepath, posX, posY, rotation, scaleX, scaleY));

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
        Slider sliderImagePosX = new CustomSlider(-rangeWidth, 2 * rangeWidth, (int) posX);
        TextField textFieldImagePosX = new TextField(String.format("%d", (int) posX));
        labelImagePosX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderImagePosX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldImagePosX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderImagePosX.minProperty().bind(rangeWidthProperty.multiply(-1));
        sliderImagePosX.maxProperty().bind(rangeWidthProperty.multiply(2));

        Label labelImagePosY = new Label(" └ Position Y");
        Slider sliderImagePosY = new CustomSlider(-rangeHeight, 2 * rangeHeight, (int) posY);
        TextField textFieldImagePosY = new TextField(String.format("%d", (int) posY));
        labelImagePosY.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderImagePosY.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldImagePosY.setPrefWidth(width * WIDTH_OFFSET_RIGHT);
        sliderImagePosY.minProperty().bind(rangeHeightProperty.multiply(-1));
        sliderImagePosY.maxProperty().bind(rangeHeightProperty.multiply(2));

        Label labelImageRotation = new Label(" └ Rotation");
        Slider sliderImageRotation = new CustomSlider(0, 360, (int) rotation);
        TextField textFieldImageRotation = new TextField(String.format("%d", (int) rotation));
        labelImageRotation.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderImageRotation.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldImageRotation.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelImageScaleX = new Label(" └ Scale X");
        Slider sliderImageScaleX = new CustomSlider(0, 200, (int) scaleX);
        TextField textFieldImageScaleX = new TextField(String.format("%d", (int) scaleX));
        labelImageScaleX.setPrefWidth(width * WIDTH_OFFSET_LEFT);
        sliderImageScaleX.setPrefWidth(width * WIDTH_OFFSET_MIDDLE);
        textFieldImageScaleX.setPrefWidth(width * WIDTH_OFFSET_RIGHT);

        Label labelImageScaleY = new Label(" └ Scale Y");
        Slider sliderImageScaleY = new CustomSlider(0, 200, (int) scaleY);
        TextField textFieldImageScaleY = new TextField(String.format("%d", (int) scaleY));
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
            int value = textFieldStringToInt(textFieldImagePosX.getText(), (int) sliderImagePosX.getMin(), (int) sliderImagePosX.getMax());
            sliderImagePosX.valueProperty().setValue(value);
            textFieldImagePosX.textProperty().setValue(String.format("%d", value));

            fileList.get(groupImageList.indexOf(gridImages)).setPosX(value);
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        });
        //  └ Position Y
        sliderImagePosY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImagePosY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldImagePosY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldImagePosY.getText(), (int) sliderImagePosY.getMin(), (int) sliderImagePosY.getMax());
            sliderImagePosY.valueProperty().setValue(value);
            textFieldImagePosY.textProperty().setValue(String.format("%d", value));

            fileList.get(groupImageList.indexOf(gridImages)).setPosY(value);
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        });
        //  └ Rotation
        sliderImageRotation.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageRotation.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldImageRotation.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldImageRotation.getText(), (int) sliderImageRotation.getMin(), (int) sliderImageRotation.getMax());
            sliderImageRotation.valueProperty().setValue(value);
            textFieldImageRotation.textProperty().setValue(String.format("%d", value));

            fileList.get(groupImageList.indexOf(gridImages)).setRotation(value);
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        });
        //  └ Scale X
        sliderImageScaleX.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageScaleX.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldImageScaleX.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldImageScaleX.getText(), (int) sliderImageScaleX.getMin(), (int) sliderImageScaleX.getMax());
            sliderImageScaleX.valueProperty().setValue(value);
            textFieldImageScaleX.textProperty().setValue(String.format("%d", value));

            fileList.get(groupImageList.indexOf(gridImages)).setScaleX(value);
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        });
        //  └ Scale Y
        sliderImageScaleY.valueProperty().addListener((obs, oldValue, newValue) -> textFieldImageScaleY.textProperty().setValue(String.format("%d", newValue.intValue())));
        textFieldImageScaleY.textProperty().addListener((obs, oldValue, newValue) -> {
            int value = textFieldStringToInt(textFieldImageScaleY.getText(), (int) sliderImageScaleY.getMin(), (int) sliderImageScaleY.getMax());
            sliderImageScaleY.valueProperty().setValue(value);
            textFieldImageScaleY.textProperty().setValue(String.format("%d", value));

            fileList.get(groupImageList.indexOf(gridImages)).setScaleY(value);
            imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        });

        // Initialize
        imageFormatProperty.setValue(!imageFormatProperty.getValue()); // Property
        buttonShowAndHide();
    }

    public void addEqualizer() {
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

        // Initialize
        buttonShowAndHide();
    }

    public void clear() {
        groupImageList.clear();
        fileList.clear();
        imageFormatProperty.setValue(!imageFormatProperty.getValue());
    }

    private void buttonShowAndHide() {
        if (fileList.size() < 2)
            return;

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

    public void setRangeWidth(int width) {
        this.rangeWidth = width;
        this.rangeWidthProperty.setValue(width);
    }

    public void setRangeHeight(int height) {
        this.rangeHeight = height;
        this.rangeHeightProperty.setValue(height);
    }

    private int textFieldStringToInt(String value, int min, int max) {
        int result;
        try {
            result = Math.max(Math.min(Integer.parseInt(value), max), min);
        } catch (NumberFormatException ignored) {
            result = 0;
        }
        return result;
    }

}
