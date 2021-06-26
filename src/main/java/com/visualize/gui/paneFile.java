package com.visualize.gui;

//import com.visualize.Jar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;

import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class paneFile {
    AnchorPane paneofFile = new AnchorPane();
    private ObservableList<String> data = FXCollections.observableArrayList();  //歌名
    private ObservableList<String> pathData = FXCollections.observableArrayList();  //歌曲絕對路徑
    final ListView<String> songList = new ListView<>(data);
    private Slider volumeSlider;
    private  TextField volumeTextField;

    private int selectSongIndex;
    public BooleanProperty songChangeProperty;
    public BooleanProperty volumeChangeProperty;

    public paneFile(){

        songChangeProperty = new SimpleBooleanProperty(false);
        volumeChangeProperty = new SimpleBooleanProperty(false);

        Stage s = new Stage();
        FileChooser chooser=new FileChooser();
        chooser.setInitialDirectory(new File("C:\\Users\\"));   //預設路徑
        //chooser.setTitle("Choose Song");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music", "*.wav", "*.mp3")); //只抓wav, mp3

        data.add("__default__.wav");
        //pathData.add(new File(Jar.getJarPath() + "/music/__default__.wav").getAbsolutePath());
        pathData.add(new File("src/main/resources/music/__default__.wav").getAbsolutePath());//這邊看你歌到時候放哪

        songList.setPrefSize(150,500);
        songList.setOrientation(Orientation.VERTICAL);
        songList.setCellFactory(ComboBoxListCell.forListView(data));
        songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectSongIndex = songList.getSelectionModel().getSelectedIndex();
                if (selectSongIndex == -1)
                    selectSongIndex = 0;
                songChangeProperty.setValue(true); // 數值更改旗標
            }
        });
        AnchorPane.setTopAnchor(songList, 25.0);  //設定相對位置
        AnchorPane.setLeftAnchor(songList,20.0);
        paneofFile.getChildren().add(songList);

        Label volume = new Label("Volume");
        volumeSlider = new Slider();
        volumeSlider.setValue(50);
        volumeSlider.setMax(100);
        volumeTextField = new TextField("");
        volumeTextField.setPrefSize(45,5);
        volumeTextField.setText("50.00");
        AnchorPane.setBottomAnchor(volume,150.0);
        AnchorPane.setLeftAnchor(volume,70.0);
        AnchorPane.setBottomAnchor(volumeSlider,130.0);
        AnchorPane.setLeftAnchor(volumeSlider,25.0);
        AnchorPane.setBottomAnchor(volumeTextField,90.0);
        AnchorPane.setLeftAnchor(volumeTextField,70.0);

        paneofFile.getChildren().add(volume);
        paneofFile.getChildren().add(volumeSlider);
        paneofFile.getChildren().add(volumeTextField);

        volumeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                double num = volumeSlider.getValue();
                volumeTextField.setText(Double.toString(num));
                volumeChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        volumeSlider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                volumeSlider.setValueChanging(true);
                double value = (event.getX()/volumeSlider.getWidth())*volumeSlider.getMax();
                volumeSlider.setValue(value);
                volumeSlider.setValueChanging(false);
                volumeTextField.setText(Double.toString(value));
                volumeChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        volumeTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = volumeTextField.getText();
                if(Double.valueOf(s).doubleValue()>1) s="1";
                volumeSlider.setValue(Double.valueOf(s).doubleValue());
                volumeChangeProperty.setValue(true); // 數值更改旗標
            }
        });

        Button importSong = new Button("Import");
        AnchorPane.setBottomAnchor(importSong, 30.0);
        AnchorPane.setLeftAnchor(importSong,65.0);
        importSong.setOnMouseClicked(e->{
            try{
                List<File> tempList = chooser.showOpenMultipleDialog(s);
                for(int j=0;j<tempList.size();j++){
                    boolean hasSame = false;
                    File f = new File(String.valueOf(tempList.get(j).getAbsoluteFile()));
                    for(int i=0;i<data.size();i++){
                        String str = data.get(i);
                        if(str.equals(f.getName())) hasSame=true;
                    }
                    if(hasSame) continue;
                    pathData.add(f.toString());
                    data.add(f.getName());
                    chooser.setInitialDirectory(new File(f.getParent()));
                }

            }catch (NullPointerException ex){
                System.out.println("洗哩靠喔幹");
            }
        });
        paneofFile.getChildren().add(importSong);
        paneofFile.setPrefSize(200,500);
        paneofFile.setStyle("-fx-border-color:black;");
    }

    public AnchorPane getPane() {
        return paneofFile;
    }
    public double getVolume(){
        return volumeSlider.getValue();
    }

    public String getFile(){ return pathData.get(selectSongIndex); }   //取得歌曲絕對路徑
}


