package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.object.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import javafx.scene.input.MouseEvent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Pos;

import java.io.File;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class TimelineUI extends Pane {

    private final double width;
    private final double height;

    private PlayStatus.Play play;
    private PlayStatus.Sound sound;
    private int currentVolume; // 用於紀錄現在音量，不受禁音影響
    private int oldVolume; // 滑動前的音量

    private double totalDuration; // sec
    private double currentDuration;

    private final HBox groupTimeline;

    // Property
    public final DoubleProperty volumeProperty = new SimpleDoubleProperty();

    // Object
    private final Label labelVideoName;

    private final Button buttonReplay;
    private final Button buttonPlayPause;
    private final Button buttonSound;
    private final Slider sliderVolume;

    private final Slider sliderTimeline;
    private final Label labelTimeline;

    // Constructor
    public TimelineUI (double width, double height) {
        this.width = width;
        this.height = height;
        this.setPrefSize(width, height);
        this.setStyle("-fx-background-color: #333333; -fx-border-color: #444444; -fx-border-width: 2 2 0 0;");

        groupTimeline = new HBox();
        groupTimeline.setAlignment(Pos.CENTER_LEFT);
        groupTimeline.setPrefSize(width, height);
        this.getChildren().addAll(groupTimeline);

        // Part
        labelVideoName = new Label("Selected: ");
        labelVideoName.setPrefWidth(width * .15);
        groupTimeline.getChildren().add(labelVideoName);

        buttonReplay = new Button();
        ImageView imageViewReplay = new ImageView(new Image(new File(DefaultPath.REPLAY_ICON_PATH).toURI().toString()));
        buttonReplay.setGraphic(imageViewReplay);
        buttonReplay.setStyle("-fx-background-color: transparent;");
        buttonReplay.setPrefWidth(width * .03);
        groupTimeline.getChildren().add(buttonReplay);

        buttonPlayPause = new Button();
        play = PlayStatus.Play.STOP;
        ImageView imageViewPlay = new ImageView(new Image(new File(DefaultPath.PLAY_ICON_PATH).toURI().toString()));
        ImageView imageViewPause = new ImageView(new Image(new File(DefaultPath.PAUSE_ICON_PATH).toURI().toString()));
        buttonPlayPause.setGraphic(imageViewPlay);
        buttonPlayPause.setStyle("-fx-background-color: transparent;");
        buttonPlayPause.setPrefWidth(width * .03);
        groupTimeline.getChildren().add(buttonPlayPause);

        buttonSound = new Button();
        ImageView imageViewSoundOn = new ImageView(new Image(new File(DefaultPath.SOUND_ON_ICON_PATH).toURI().toString()));
        ImageView imageViewSoundOff = new ImageView(new Image(new File(DefaultPath.SOUND_OFF_ICON_PATH).toURI().toString()));
        buttonSound.setGraphic(imageViewSoundOn);
        buttonSound.setStyle("-fx-background-color: transparent;");
        buttonSound.setPrefWidth(width * .03);
        groupTimeline.getChildren().add(buttonSound);

        sliderVolume = new CustomSlider(0, 100, 50);
        sound = PlayStatus.Sound.ON;
        currentVolume = oldVolume = (int) sliderVolume.getValue();
        sliderVolume.setPrefWidth(width * .11);
        groupTimeline.getChildren().add(sliderVolume);
        volumeProperty.bind(sliderVolume.valueProperty());

        Label labelNull = new Label();
        labelNull.setPrefWidth(width * .25);
        groupTimeline.getChildren().add(labelNull);

        sliderTimeline = new CustomSlider(0, 100, 0);
        labelTimeline = new Label(timeFormat(0, 0));
        sliderTimeline.setPrefWidth(width * .30);
        //labelTimeline.setPrefWidth(width * .05);
        groupTimeline.getChildren().addAll(sliderTimeline, labelTimeline);

        // Event
        // └ Button
        //  └ Play / Pause
        buttonPlayPause.setOnAction(event -> {
            if (play == PlayStatus.Play.PLAY) {
                buttonPlayPause.setGraphic(imageViewPlay); // 轉暫停
                play = PlayStatus.Play.PAUSE;
            }
            else {
                buttonPlayPause.setGraphic(imageViewPause); // 轉撥放
                play = PlayStatus.Play.PLAY;
            }
        });
        //  └ Volume
        buttonSound.setOnAction(event -> {
            if (sound == PlayStatus.Sound.ON) {
                buttonSound.setGraphic(imageViewSoundOff);
                sound = PlayStatus.Sound.OFF;

                oldVolume = currentVolume; // 記錄禁音前的值
                sliderVolume.setValue(0);
            }
            else {
                buttonSound.setGraphic(imageViewSoundOn);
                sound = PlayStatus.Sound.ON;

                sliderVolume.setValue(oldVolume); // 回復至禁音前的值
            }
        });
        buttonSound.hoverProperty().addListener(event -> {

        });
        sliderVolume.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (sound == PlayStatus.Sound.ON)
                currentVolume = newValue.intValue();

            if (newValue.intValue() == 0) {
                buttonSound.setGraphic(imageViewSoundOff);
                sound = PlayStatus.Sound.OFF;
            }
            else {
                buttonSound.setGraphic(imageViewSoundOn);
                sound = PlayStatus.Sound.ON;
            }
        });
        sliderVolume.valueChangingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) // 記錄拖動前的值
                oldVolume = (int) sliderVolume.getValue();
            else
                currentVolume = (int) sliderVolume.getValue();
        });
        sliderVolume.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> sliderVolume.setValueChanging(true));
        sliderVolume.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> sliderVolume.setValueChanging(false));
    }

    // Methods
    public void setFilename(String filename) {
        labelVideoName.setText("Selected: " + filename);
    }

    public void setTotalDuration(double duration) {
        this.totalDuration = duration;
        sliderTimeline.setMax(duration);
        labelTimeline.setText(timeFormat(0, totalDuration));
    }

    public void setCurrentVolume(double duration) {
        this.currentDuration = duration;
        sliderTimeline.setValue(duration);
        labelTimeline.setText(timeFormat(currentDuration, totalDuration));
    }

    private String timeFormat(double startTime, double finishTime) {
        int startSec = (int) startTime % 60;
        int startMin = (int) startTime / 60 % 60;
        int startHour = (int) startTime / 60 / 60;
        String start = String.format("%02d:%02d:%02d", startHour, startMin, startSec);

        int finishSec = (int) finishTime % 60;
        int finishMin = (int) finishTime / 60 % 60;
        int finishHour = (int) finishTime / 60 / 60;
        String finish = String.format("%02d:%02d:%02d", finishHour, finishMin, finishSec);

        return "   " + start + " / " + finish;
    }

}
