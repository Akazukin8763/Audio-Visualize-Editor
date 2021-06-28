package com.visualize.main;

//import com.visualize.Jar;
import com.visualize.gui.*;
import com.visualize.file.*;
import com.visualize.view.*;
import com.visualize.convert.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.event.Event;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;

public class Main extends Application {

    private Stage stage;

    //paneMenu p0 = new paneMenu(); //裡面沒東西要用 而且很醜
    private paneFile paneFile;
    private VisualizePane visualizePane;
    private paneController paneController;

    private GridPane paneButton;
    private FileChooser fileExportChooser;
    private FileChooser fileWallpaperChooser;

    private AudioFile audioFile;
    private VisualizeFormat visualizeFormat;

    private final double SCALE_DEFAULT = 1;
    private final double SCALE_OUT = 1000 / 1920.0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = new Stage();

        //audioFile = new WavFile(Jar.getJarPath() + "/music/__default__.wav");
        audioFile = new WavFile("src/main/resources/music/__default__.wav");
        visualizeFormat = new VisualizeFormat(64, 4, 2, 100, 0, 0, 0, Color.WHITE, Color.WHITE, .5);

        paneFile = new paneFile();
        visualizePane = new VisualizePane(audioFile, visualizeFormat);
        paneController = new paneController();

        ScrollPane fitPane = new ScrollPane();
        fitPane.setContent(visualizePane);
        fitPane.setPrefSize(1000, 562.5);
        fitPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        zoom(SCALE_OUT);

        paneButton = new GridPane();
        paneButton.setHgap(10);
        Button buttonPreDraw = new Button("PreDraw");
        buttonPreDraw.setOnAction((event) -> preview());
        Button buttonDraw = new Button("Draw");
        buttonDraw.setOnAction((event) -> animate());
        Button buttonPlay = new Button("Play");
        buttonPlay.setOnAction((event) -> play());
        Button buttonStop = new Button("Stop");
        buttonStop.setOnAction((event) -> stop());
        Button buttonExport = new Button("Export");
        buttonExport.setOnAction((event) -> saveVideo());
        Button buttonChangeWallpaper = new Button("Change Wallpaper");
        buttonChangeWallpaper.setOnAction((event) -> changeWallpaper());
        Label labelTime = new Label();
        setTime(labelTime, 0, (int)audioFile.getDuration());
        Slider sliderTime = new Slider(0, (int)audioFile.getDuration(), 0);
        sliderTime.setPrefWidth(300);
        paneButton.add(buttonPreDraw, 0, 0);
        paneButton.add(buttonDraw, 1, 0);
        paneButton.add(buttonExport, 2, 0);
        paneButton.add(buttonChangeWallpaper, 3, 0);
        paneButton.add(buttonPlay, 19, 0);
        paneButton.add(buttonStop, 20, 0);
        paneButton.add(labelTime, 21, 0);
        paneButton.add(sliderTime, 22, 0);
        EventLog.eventLog = new EventLog("Event Log:\n");
        EventLog.eventLog.setPrefHeight(215);
        EventLog.eventLog.setEditable(false);
        EventLog.eventLog.setStyle("-fx-font-size: 14px;");

        fileExportChooser = new FileChooser();
        fileExportChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));
        fileExportChooser.setInitialFileName("AudioVisualize");
        fileWallpaperChooser = new FileChooser();
        fileWallpaperChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));

        BorderPane middlePane = new BorderPane(paneButton, fitPane, null, EventLog.eventLog, null);
        HBox hBox = new HBox(paneFile.getPane(), middlePane, paneController.getPane());
        Scene scene = new Scene(hBox);

        //scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
        //buttonChangeWallpaper.getStyleClass().add("button");

        stage.setScene(scene);
        stage.setTitle("Ɐudio Ʌisualizer Ǝditor");
        //stage.getIcons().add(new javafx.scene.image.Image(new java.io.File(Jar.getJarPath() + "/icon/icon.png").toURI().toString()));
        stage.getIcons().add(new javafx.scene.image.Image(new java.io.File("src/main/resources/icon/icon.png").toURI().toString()));
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.show();

        preview();

        // Event
        paneController.valueChangeProperty.addListener((obs, oldValue, newValue) -> {
            if (!visualizePane.isRunning()) {
                clear();
                preview();
            }
            paneController.valueChangeProperty.setValue(false);
        });

        paneFile.songChangeProperty.addListener((obs, oldValue, newValue) -> {
            String songOld = audioFile.getAbsolutePath();
            String songNew = paneFile.getFile();
            if (!songOld.equals(songNew)) {
                try {
                    if (visualizePane.isRunning())
                        stop();
                    clear();

                    if (songNew.endsWith(".wav"))
                        audioFile = new WavFile(songNew);
                    else if (songNew.endsWith("mp3"))
                        audioFile = new Mp3File(songNew);
                    else
                        throw new javax.sound.sampled.UnsupportedAudioFileException();

                    audioFile.setVolume(paneFile.getVolume() / 100.0);
                    visualizePane.setAudioFile(audioFile);
                    setTime(labelTime, 0, (int)audioFile.getDuration());
                    sliderTime.setMax(audioFile.getDuration());
                    EventLog.eventLog.songChange(songOld, songNew);
                } catch (javax.sound.sampled.UnsupportedAudioFileException | java.io.IOException e) {
                    EventLog.eventLog.warning("The file is Unsupported or Non-existent.");
                }
            }
            paneFile.songChangeProperty.setValue(false);
        });

        paneFile.volumeChangeProperty.addListener((obs, oldValue, newValue) -> {
            EventLog.eventLog.volumeChange(audioFile.getVolume() * 100, paneFile.getVolume());
            audioFile.setVolume(paneFile.getVolume() / 100.0);
            paneFile.volumeChangeProperty.setValue(false);
        });

        visualizePane.timeChangeProperty.addListener((obs, oldValue, newValue) -> {
            setTime(labelTime, (int)visualizePane.currentTime, (int)audioFile.getDuration());
            sliderTime.setValue(visualizePane.currentTime);
            visualizePane.timeChangeProperty.setValue(false);
        });

        sliderTime.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            setTime(labelTime, (int)sliderTime.getValue(), (int)audioFile.getDuration());
        });

        sliderTime.setOnMouseClicked((event) -> {
            double value = event.getX() / sliderTime.getWidth() * sliderTime.getMax();
            sliderTime.setValue(value);
            playFrom(value);
            setTime(labelTime, (int)value, (int)audioFile.getDuration());
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    private void updateData() {
        VisualizeFormat visualizeFormatOld = visualizePane.getVisualizeFormat();
        VisualizeMode.Side sideOld = visualizePane.getSide();
        VisualizeMode.View viewOld = visualizePane.getView();
        VisualizeMode.Stereo stereoOld = visualizePane.getStereo();
        double minFreqOld = visualizePane.getMinFreq();
        double maxFreqOld = visualizePane.getMaxFreq();
        String backgroundImageOld = visualizePane.getBackgroundImage();
        String backgroundColorOld = visualizePane.getBackgroundColor(); // #123456
        String backgroundRepeatOld = visualizePane.getBackgroundRepeat();
        String backgroundPositionOld = visualizePane.getBackgroundPosition();

        VisualizeFormat visualizeFormatNew = paneController.getVisualizeFormat();
        VisualizeMode.Side sideNew = paneController.getSide();
        VisualizeMode.View viewNew = paneController.getView();
        VisualizeMode.Stereo stereoNew = paneController.getStereo();
        double minFreqNew = paneController.getMinFrequency();
        double maxFreqNew = paneController.getMaxFrequency();
        String backgroundImageNew = paneController.getImg();
        String backgroundColorNew = "#" + paneController.getImgColor().toUpperCase().subSequence(2, 8); // 0x123465ff
        String backgroundRepeatNew = paneController.getImgRepeat();
        String backgroundPositionNew = paneController.getImgPos();

        EventLog.eventLog.barNumChange(visualizeFormatOld.getBarNum(), visualizeFormatNew.getBarNum());
        EventLog.eventLog.barSizeChange(visualizeFormatOld.getBarSize(), visualizeFormatNew.getBarSize());
        EventLog.eventLog.barGapChange(visualizeFormatOld.getBarGap(), visualizeFormatNew.getBarGap());
        EventLog.eventLog.radiusChange(visualizeFormatOld.getRadius(), visualizeFormatNew.getRadius());
        EventLog.eventLog.posXChange((int)visualizeFormatOld.getPosX(), (int)visualizeFormatNew.getPosX());
        EventLog.eventLog.posYChange((int)visualizeFormatOld.getPosY(), (int)visualizeFormatNew.getPosY());
        EventLog.eventLog.rotationChange((int)visualizeFormatOld.getRotation(), (int)visualizeFormatNew.getRotation());
        EventLog.eventLog.colorChange("#" + visualizeFormatOld.getBarColor().toString().toUpperCase().subSequence(2, 8), "#" + visualizeFormatNew.getBarColor().toString().toUpperCase().subSequence(2, 8));
        EventLog.eventLog.colorBlurChange("#" + visualizeFormatOld.getDropShadowColor().toString().toUpperCase().subSequence(2, 8), "#" + visualizeFormatNew.getDropShadowColor().toString().toUpperCase().subSequence(2, 8));
        EventLog.eventLog.sensitivityChange(visualizeFormatOld.getSensitivity(), visualizeFormatNew.getSensitivity());
        EventLog.eventLog.sideChange(sideOld, sideNew);
        EventLog.eventLog.viewChange(viewOld, viewNew);
        EventLog.eventLog.stereoChange(stereoOld, stereoNew);
        EventLog.eventLog.minFreqChange((int)minFreqOld, (int)minFreqNew);
        EventLog.eventLog.maxFreqChange((int)maxFreqOld, (int)maxFreqNew);
        EventLog.eventLog.backgroundImageChange(backgroundImageOld, backgroundImageNew);
        EventLog.eventLog.backgroundColorChange(backgroundColorOld, backgroundColorNew);
        EventLog.eventLog.backgroundRepeatChange(backgroundRepeatOld, backgroundRepeatNew);
        EventLog.eventLog.backgroundPositionChange(backgroundPositionOld, backgroundPositionNew);

        visualizePane.setVisualizeFormat(visualizeFormatNew);
        visualizePane.setSide(sideNew);
        visualizePane.setView(viewNew);
        try {
            visualizePane.setStereo(stereoNew);
        } catch (IllegalArgumentException e){
            paneController.setStereo(stereoOld);
            EventLog.eventLog.warning(e.getMessage());
        }
        visualizePane.setMinMaxFreq(minFreqNew, maxFreqNew);
        visualizePane.setBackgroundStyle(backgroundImageNew, backgroundColorNew, backgroundRepeatNew, backgroundPositionNew);
    }

    public void preview() {
        if (visualizePane.isRunning())
            stop();
        clear();
        updateData();

        visualizePane.preview();
    }

    public void animate() {
        if (visualizePane.isRunning())
            stop();
        clear();
        updateData();

        visualizePane.animate();
    }

    public void saveVideo() {
        if (visualizePane.isRunning())
            stop();
        updateData();
        //visualizePane.preview();

        //zoom(SCALE_DEFAULT); // 縮放為正常尺寸以方便剪輯
        try {
            String path = fileExportChooser.showSaveDialog(stage).getPath();

            EventLog.eventLog.encode("Video Fetching...");
            visualizePane.saveVideo(path);
            EventLog.eventLog.encode("Video Encoding...");
        } catch (NullPointerException ignored) {
            // 不做任何事
        } catch (java.io.IOException e) {
            EventLog.eventLog.warning("The file can not be exported correctly.");
        }
        //zoom(SCALE_OUT); // 縮放回預設大小
    }

    public void changeWallpaper() {
        java.io.File imgPath = new java.io.File("src/main/resources/convert");
        imgPath.delete();
        imgPath.mkdirs();
        String jpgDirPath = imgPath.getAbsolutePath();

        try {
            String path = fileWallpaperChooser.showOpenDialog(stage).getPath();
            BackgroundWallpaperChanger backGroundPlayer = new BackgroundWallpaperChanger(path, jpgDirPath);
            backGroundPlayer.execute();
        } catch (NullPointerException ignored) {
            // 不做任何事
        }
    }

    public void play() {
        if (visualizePane.isRunning())
            stop();

        try {
            visualizePane.play();
        } catch (NullPointerException e) {
            EventLog.eventLog.warning(e.getMessage());
        }
    }

    public void playFrom(double sec) {
        if (visualizePane.isRunning()) {
            try {
                visualizePane.playFrom(sec);
            } catch (NullPointerException e) {
                EventLog.eventLog.warning(e.getMessage());
            }
        }
    }

    public void stop() {
        try {
            visualizePane.stop();
        } catch (NullPointerException e) {
            EventLog.eventLog.warning(e.getMessage());
        }
    }

    public void clear() {
        visualizePane.clearAnimation();
    }

    public void zoom(double scale) {
        visualizePane.setScaleX(scale);
        visualizePane.setScaleY(scale);
        visualizePane.setTranslateX(0 - (visualizePane.getPrefWidth() * (1 - scale)) / 2);
        visualizePane.setTranslateY(0 - (visualizePane.getPrefHeight() * (1 - scale)) / 2);
    }

    public void setTime(Label time, int current, int end) {
        time.setText(showTime(current) + " / " + showTime(end));
    }

    public String showTime(int time) {
        int sec = time % 60;
        int min = time / 60 % 60;
        int hour = time / 60 / 60;

        return String.format("%02d:%02d:%02d", hour, min, sec);
    }

}
