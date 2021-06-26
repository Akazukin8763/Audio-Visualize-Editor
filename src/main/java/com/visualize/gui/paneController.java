package com.visualize.gui;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.visualize.view.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;

public class paneController {
    GridPane paneofControler = new GridPane();
    private String buttonParameter[] = {"Adjustable frequency"};
    private String sliderParameter[] = {"Number bar", "Rotation", "Size", "Radius", "Gap", "Sensitivity", "Position X", "Position Y"};
    private double sliderNumber[] = {64, 0, 4, 100, 2, 0.5, 0, 0};  //預設參數
    private String listParameter[] = {"Equlizer type", "Direction", "Stereo"};
    private String listInput[][] = {{"LINE", "CIRCLE", "ANALOGY"}, {"OUT", "IN", "BOTH"}, {"SINGLE", "LEFT", "RIGHT", "BOTH"},{"no-repeat","repeat","round","space"},{"center","default"}};
    private String img = "null";
    private String imgColor = Color.WHITE.toString();
    private String imgRepeat = listInput[3][0];
    private String imgPos = listInput[4][0];
    private Stage s;

    private final Slider numberBarSlider;
    private final Slider rotationSlider;
    private final Slider sizeSlider;
    private final Slider radiusSlider;
    private final Slider gapSlider;
    private final Slider sensitivitySlider;
    private final Slider posXSlider;
    private final Slider posYSlider;
    private final TextField textMaxFrequency;
    private final TextField textMinFrequency;
    private final ColorPicker colorPicker;
    private final ColorPicker colorPicker2;
    private final ChoiceBox choiceBoxEqulizer;
    private final ChoiceBox choiceBoxDirection;
    private final ChoiceBox choiceBoxStereo;
    private final ColorPicker backgroundPicker;
    private final ChoiceBox choiceBoxBackgroundRepeat;
    private final ChoiceBox choiceBoxBackgroundPos;
    public BooleanProperty valueChangeProperty;

    public paneController(){

        valueChangeProperty = new SimpleBooleanProperty(false);
        paneofControler.setAlignment(Pos.BASELINE_RIGHT);   //向右對齊
        paneofControler.setHgap(10);    //邊界
        paneofControler.setVgap(10);
        Label non1 = new Label("                                  SPECTRUM");
        GridPane.setColumnSpan(non1,3);
        //non1.setTextFill(Color.RED);
        non1.setFont(new Font(15));
        paneofControler.add(non1,0,0);
        Label numberBar = new Label(sliderParameter[0]);
        numberBarSlider = new Slider();
        numberBarSlider.setValue(sliderNumber[0]);
        numberBarSlider.setMax(256);
        TextField textFieldNumberBar = new TextField("");
        textFieldNumberBar.setPrefSize(45,5);
        textFieldNumberBar.setText(Integer.toString((int)sliderNumber[0]));
        paneofControler.add(numberBar,0,1);
        paneofControler.add(numberBarSlider,1,1);
        paneofControler.add(textFieldNumberBar,2,1);

        numberBarSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = numberBarSlider.getValue();
                textFieldNumberBar.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        numberBarSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                numberBarSlider.setValueChanging(true);
                double value = (event.getX()/numberBarSlider.getWidth())*numberBarSlider.getMax();
                numberBarSlider.setValue(value);
                numberBarSlider.setValueChanging(false);
                textFieldNumberBar.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldNumberBar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldNumberBar.getText();
                if(Integer.valueOf(s).intValue()>256) s="256";
                numberBarSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label rotation = new Label(sliderParameter[1]);
        rotationSlider = new Slider();
        rotationSlider.setMax(360);
        rotationSlider.setValue(sliderNumber[1]);
        TextField textFieldRotation = new TextField("");
        textFieldRotation.setPrefSize(45,5);
        textFieldRotation.setText(Integer.toString((int)sliderNumber[1]));
        paneofControler.add(rotation,0,2);
        paneofControler.add(rotationSlider,1,2);
        paneofControler.add(textFieldRotation,2,2);

        rotationSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = rotationSlider.getValue();
                textFieldRotation.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        rotationSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rotationSlider.setValueChanging(true);
                double value = (event.getX()/rotationSlider.getWidth())*rotationSlider.getMax();
                rotationSlider.setValue(value);
                rotationSlider.setValueChanging(false);
                textFieldRotation.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldRotation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldRotation.getText();
                if(Integer.valueOf(s).intValue()>360) s="360";
                rotationSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label size = new Label(sliderParameter[2]);
        sizeSlider = new Slider();
        sizeSlider.setValue(sliderNumber[2]);
        sizeSlider.setMax(10);
        TextField textFieldSize = new TextField("");
        textFieldSize.setPrefSize(45,5);
        textFieldSize.setText(Integer.toString((int)sliderNumber[2]));
        paneofControler.add(size,0,3);
        paneofControler.add(sizeSlider,1,3);
        paneofControler.add(textFieldSize,2,3);

        sizeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = sizeSlider.getValue();
                textFieldSize.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        sizeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sizeSlider.setValueChanging(true);
                double value = (event.getX()/sizeSlider.getWidth())*sizeSlider.getMax();
                sizeSlider.setValue(value);
                sizeSlider.setValueChanging(false);
                textFieldSize.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldSize.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldSize.getText();
                if(Integer.valueOf(s).intValue()>10) s="10";
                sizeSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label radius = new Label(sliderParameter[3]);
        radiusSlider = new Slider();
        radiusSlider.setMax(1000);
        radiusSlider.setValue(sliderNumber[3]);
        TextField textFieldRadius = new TextField("");
        textFieldRadius.setPrefSize(45,5);
        textFieldRadius.setText(Integer.toString((int)sliderNumber[3]));
        paneofControler.add(radius,0,4);
        paneofControler.add(radiusSlider,1,4);
        paneofControler.add(textFieldRadius,2,4);

        radiusSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = radiusSlider.getValue();
                textFieldRadius.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        radiusSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                radiusSlider.setValueChanging(true);
                double value = (event.getX()/radiusSlider.getWidth())*radiusSlider.getMax();
                radiusSlider.setValue(value);
                radiusSlider.setValueChanging(false);
                textFieldRadius.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldRadius.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldRadius.getText();
                radiusSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label gap = new Label(sliderParameter[4]);
        gapSlider = new Slider();
        gapSlider.setValue(sliderNumber[4]);
        gapSlider.setMax(10);
        TextField textFieldGap = new TextField("");
        textFieldGap.setPrefSize(45,5);
        textFieldGap.setText(Integer.toString((int)sliderNumber[4]));
        paneofControler.add(gap,0,5);
        paneofControler.add(gapSlider,1,5);
        paneofControler.add(textFieldGap,2,5);

        gapSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = gapSlider.getValue();
                textFieldGap.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        gapSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                gapSlider.setValueChanging(true);
                double value = (event.getX()/gapSlider.getWidth())*gapSlider.getMax();
                gapSlider.setValue(value);
                gapSlider.setValueChanging(false);
                textFieldGap.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldGap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldGap.getText();
                if(Integer.valueOf(s).intValue()>10) s="10";
                gapSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label sensitivity = new Label(sliderParameter[5]);
        sensitivitySlider = new Slider();
        sensitivitySlider.setValue(sliderNumber[5]);
        sensitivitySlider.setMax(1);
        TextField textFieldSensitivity = new TextField("");
        textFieldSensitivity.setPrefSize(45,5);
        textFieldSensitivity.setText(Double.toString(sliderNumber[5]));
        paneofControler.add(sensitivity,0,6);
        paneofControler.add(sensitivitySlider,1,6);
        paneofControler.add(textFieldSensitivity,2,6);

        sensitivitySlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = sensitivitySlider.getValue();
                textFieldSensitivity.setText(Double.toString(num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        sensitivitySlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                sensitivitySlider.setValueChanging(true);
                double value = (event.getX()/sensitivitySlider.getWidth())*sensitivitySlider.getMax();
                sensitivitySlider.setValue(value);
                sensitivitySlider.setValueChanging(false);
                textFieldSensitivity.setText(Double.toString(value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldSensitivity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldSensitivity.getText();
                if(Double.valueOf(s).doubleValue()>1) s="1";
                sensitivitySlider.setValue(Double.valueOf(s).doubleValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label posX = new Label(sliderParameter[6]);
        posXSlider = new Slider();
        posXSlider.setValue(sliderNumber[6]);
        posXSlider.setMax(1920);
        TextField textFieldPosX = new TextField("");
        textFieldPosX.setPrefSize(45,5);
        textFieldPosX.setText(Integer.toString((int)sliderNumber[6]));
        paneofControler.add(posX,0,7);
        paneofControler.add(posXSlider,1,7);
        paneofControler.add(textFieldPosX,2,7);

        posXSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = posXSlider.getValue();
                textFieldPosX.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        posXSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                posXSlider.setValueChanging(true);
                double value = (event.getX()/posXSlider.getWidth())*posXSlider.getMax();
                posXSlider.setValue(value);
                posXSlider.setValueChanging(false);
                textFieldPosX.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldPosX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldPosX.getText();
                posXSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label posY = new Label(sliderParameter[7]);
        posYSlider = new Slider();
        posYSlider.setValue(sliderNumber[7]);
        posYSlider.setMax(1080);
        TextField textFieldPosY = new TextField("");
        textFieldPosY.setPrefSize(45,5);
        textFieldPosY.setText(Integer.toString((int)sliderNumber[7]));
        paneofControler.add(posY,0,8);
        paneofControler.add(posYSlider,1,8);
        paneofControler.add(textFieldPosY,2,8);

        posYSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = posYSlider.getValue();
                textFieldPosY.setText(Integer.toString((int)num));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        posYSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                posYSlider.setValueChanging(true);
                double value = (event.getX()/posYSlider.getWidth())*posYSlider.getMax();
                posYSlider.setValue(value);
                posYSlider.setValueChanging(false);
                textFieldPosY.setText(Integer.toString((int)value));
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        textFieldPosY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = textFieldPosY.getText();
                posYSlider.setValue(Integer.valueOf(s).intValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label frequency = new Label(buttonParameter[0]);
        CheckBox checkBox = new CheckBox();
        paneofControler.add(frequency,0,9);
        paneofControler.add(checkBox,1,9);

        Label maxFrequency = new Label("maxFrequency");
        textMaxFrequency = new TextField("22050");
        textMaxFrequency.setPrefSize(30,5);
        paneofControler.add(maxFrequency,0,10);
        paneofControler.add(textMaxFrequency,1,10);

        Label minFrequency = new Label("minFrequency");
        textMinFrequency = new TextField("20");
        textMinFrequency.setPrefSize(30,5);
        textMaxFrequency.setEditable(false);
        textMinFrequency.setEditable(false);
        paneofControler.add(minFrequency,0,11);
        paneofControler.add(textMinFrequency,1,11);

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(checkBox.isSelected()){
                    textMaxFrequency.setEditable(true);
                    textMinFrequency.setEditable(true);
                }
                else{
                    textMaxFrequency.setText("22050");
                    textMinFrequency.setText("20");
                    textMaxFrequency.setEditable(false);
                    textMinFrequency.setEditable(false);
                    valueChangeProperty.setValue(true); // 數值更改旗標
                }
            }
        });

        textMaxFrequency.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Integer.valueOf(textMaxFrequency.getText())>Integer.valueOf(textMinFrequency.getText())){
                    valueChangeProperty.setValue(true); // 數值更改旗標
                }
                else textMaxFrequency.setText("22050");
            }
        });

        textMinFrequency.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Integer.valueOf(textMaxFrequency.getText())>Integer.valueOf(textMinFrequency.getText())){
                    valueChangeProperty.setValue(true); // 數值更改旗標
                }
                else textMinFrequency.setText("20");
            }
        });

        Label color = new Label("Color");
        colorPicker = new ColorPicker();
        paneofControler.add(color,0,12);
        paneofControler.add(colorPicker,1,12);

        colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label colorBlur = new Label("Color Blur");
        colorPicker2 = new ColorPicker();
        paneofControler.add(colorBlur,0,13);
        paneofControler.add(colorPicker2,1,13);

        colorPicker2.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label equlizer = new Label(listParameter[0]);
        choiceBoxEqulizer = new ChoiceBox(FXCollections.observableArrayList(listInput[0]));
        choiceBoxEqulizer.setValue(listInput[0][0]);
        paneofControler.add(equlizer,0,14);
        paneofControler.add(choiceBoxEqulizer,1,14);

        choiceBoxEqulizer.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label direction = new Label(listParameter[1]);
        choiceBoxDirection = new ChoiceBox(FXCollections.observableArrayList(listInput[1]));
        choiceBoxDirection.setValue(listInput[1][0]);
        paneofControler.add(direction,0,15);
        paneofControler.add(choiceBoxDirection,1,15);

        choiceBoxDirection.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label stereo = new Label(listParameter[2]);
        choiceBoxStereo = new ChoiceBox(FXCollections.observableArrayList(listInput[2]));
        choiceBoxStereo.setValue(listInput[2][3]);
        paneofControler.add(stereo,0,16);
        paneofControler.add(choiceBoxStereo,1,16);

        choiceBoxStereo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Button reset = new Button("reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                numberBarSlider.setValue(sliderNumber[0]);
                textFieldNumberBar.setText(Integer.toString((int)sliderNumber[0]));

                rotationSlider.setValue(sliderNumber[1]);
                textFieldRadius.setText(Integer.toString((int)sliderNumber[1]));

                sizeSlider.setValue(sliderNumber[2]);
                textFieldSize.setText(Integer.toString((int)sliderNumber[2]));

                radiusSlider.setValue(sliderNumber[3]);
                textFieldRadius.setText(Integer.toString((int)sliderNumber[3]));

                gapSlider.setValue(sliderNumber[4]);
                textFieldGap.setText(Integer.toString((int)sliderNumber[4]));

                sensitivitySlider.setValue(sliderNumber[5]);
                textFieldSensitivity.setText(Double.toString(sliderNumber[5]));

                posXSlider.setValue(sliderNumber[6]);
                textFieldPosX.setText(Integer.toString((int)sliderNumber[6]));

                posYSlider.setValue(sliderNumber[7]);
                textFieldPosY.setText(Integer.toString((int)sliderNumber[7]));

                checkBox.setSelected(false);
                textMaxFrequency.setText("22050");
                textMinFrequency.setText("20");

                colorPicker.setValue(Color.WHITE);
                colorPicker2.setValue(Color.WHITE);

                choiceBoxEqulizer.setValue(listInput[0][0]);
                choiceBoxDirection.setValue(listInput[1][0]);
                choiceBoxStereo.setValue(listInput[2][3]);

                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });
        Label label = new Label("                            BACKGROUND");
        label.setFont(new Font(15.0));
        //label.setTextFill(Color.RED);
        GridPane.setColumnSpan(label,3);
        paneofControler.addRow(17,label);

        FileChooser imgChooser = new FileChooser();
        imgChooser.setInitialDirectory(new File("C:\\Users\\"));
        imgChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"));

        Label backgroundImg = new Label("BackgroundImage");
        Button imgButton = new Button("Import");
        Button clear = new Button("Clear");
        paneofControler.add(backgroundImg,0,18);
        paneofControler.add(imgButton,1,18);
        paneofControler.add(clear,2,18);

        imgButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    File imgFile = imgChooser.showOpenDialog(s);
                    img = imgFile.getAbsolutePath();
                    imgChooser.setInitialDirectory(new File(imgFile.getParent()));
                    valueChangeProperty.setValue(true); // 數值更改旗標
                }catch (NullPointerException ex){
                    System.out.println("洗哩靠喔幹");
                }
            }
        });

        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                img = "null";
                valueChangeProperty.setValue(true);
            }
        });

        Label backgroundColor = new Label("BackgroundColor");
        backgroundPicker = new ColorPicker();
        paneofControler.add(backgroundColor,0,19);
        paneofControler.add(backgroundPicker,1,19);

        backgroundPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color color, Color t1) {
                //System.out.println(backgroundPicker.getValue().toString());
                imgColor = backgroundPicker.getValue().toString();
                label.setTextFill(backgroundPicker.getValue());
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label backgroundRepeat = new Label("BackgroundRepeat");
        choiceBoxBackgroundRepeat = new ChoiceBox(FXCollections.observableArrayList(listInput[3]));
        choiceBoxBackgroundRepeat.setValue(listInput[3][0]);
        paneofControler.add(backgroundRepeat,0,20);
        paneofControler.add(choiceBoxBackgroundRepeat,1,20);

        choiceBoxBackgroundRepeat.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                imgRepeat=choiceBoxBackgroundRepeat.getValue().toString();
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Label backgroundPos = new Label("BackgroundPosition");
        choiceBoxBackgroundPos = new ChoiceBox(FXCollections.observableArrayList(listInput[4]));
        choiceBoxBackgroundPos.setValue(listInput[4][0]);
        paneofControler.add(backgroundPos,0,21);
        paneofControler.add(choiceBoxBackgroundPos,1,21);

        choiceBoxBackgroundPos.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                imgPos= choiceBoxBackgroundPos.getValue().toString();
                valueChangeProperty.setValue(true); // 數值更改旗標
            }
        });
        //paneofControler.add(reset,1,26);
        //paneofControler.setStyle("-fx-border-color:black;-fx-padding: 0 0 0 5;");

    }

    //下面為抓各項數值

    public GridPane getPane() {
        return paneofControler;
    }

    public double getNumberBar() {
        return numberBarSlider.getValue();
    }
    public double getRotation() {
        return rotationSlider.getValue();
    }
    public double getSize() {
        return sizeSlider.getValue();
    }
    public double getRadius() {
        return radiusSlider.getValue();
    }
    public double getGap() {
        return gapSlider.getValue();
    }
    public double getSensitivity() {
        return sensitivitySlider.getValue();
    }
    public double getPosX() {
        return posXSlider.getValue();
    }
    public double getPosY() {
        return posYSlider.getValue();
    }
    public int getMaxFrequency() {
        return Integer.valueOf(textMaxFrequency.getText());
    }
    public int getMinFrequency() {
        return Integer.valueOf(textMinFrequency.getText());
    }
    public Color getColor() {
        return colorPicker.getValue();
    }
    public Color getColorBlur() {
        return colorPicker2.getValue();
    }
    public VisualizeMode.Side getSide(){
        if("OUT".equals(choiceBoxDirection.getValue().toString())) return VisualizeMode.Side.OUT;
        else if("IN".equals(choiceBoxDirection.getValue().toString())) return VisualizeMode.Side.IN;
        else return  VisualizeMode.Side.BOTH;
    }
    public VisualizeMode.View getView(){
        if("LINE".equals(choiceBoxEqulizer.getValue().toString())) return VisualizeMode.View.LINE;
        else if("CIRCLE".equals(choiceBoxEqulizer.getValue().toString())) return VisualizeMode.View.CIRCLE;
        else return  VisualizeMode.View.ANALOGY;
    }
    public void setStereo(VisualizeMode.Stereo stereo) {
        choiceBoxStereo.setValue(stereo);
    }
    public VisualizeMode.Stereo getStereo(){
        if("SINGLE".equals(choiceBoxStereo.getValue().toString())) return VisualizeMode.Stereo.SINGLE;
        else if("LEFT".equals(choiceBoxStereo.getValue().toString())) return VisualizeMode.Stereo.LEFT;
        else if("RIGHT".equals(choiceBoxStereo.getValue().toString())) return VisualizeMode.Stereo.RIGHT;
        else return VisualizeMode.Stereo.BOTH;
    }
    public VisualizeFormat getVisualizeFormat() {
        return new VisualizeFormat(
                (int)getNumberBar(), (int)getSize(), (int)getGap(), (int)getRadius(),
                getPosX(), getPosY(), getRotation(),
                getColor(), getColorBlur(), getSensitivity());
    }
    public String getImg(){
        return img;
    }
    public String getImgColor(){
        return imgColor;
    }
    public String getImgRepeat(){
        return imgRepeat;
    }
    public String getImgPos(){
        return imgPos;
    }
}