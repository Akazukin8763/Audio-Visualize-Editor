package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.event.Event;

import javafx.scene.paint.Color;

public class AudioVisualizeUI extends Pane {

    private final double width;
    private final double height;
    private int rangeWidth = 1920;
    private int rangeHeight = 1080;

    private final MenuUI menuUI;
    private final FileUI fileUI;
    private final ParamUI paramUI;

    private AudioFile audioFile;
    private VisualizeFormat visualizeFormat;
    private BackgroundFormat backgroundFormat;
    private VisualizePane visualizePane;

    private ScrollPane fitPane;

    // Constructor
    public AudioVisualizeUI(double width, double height) throws Exception {
        this.width = width;
        this.height = height;
        setPrefSize(width, height);

        menuUI = new MenuUI(width, height * .01);
        fileUI = new FileUI(width * .12, height * .65);
        paramUI = new ParamUI(width * .20, height * .94, rangeWidth, rangeHeight);

        audioFile = new WavFile(DefaultPath.DEFAULT_MUSIC_PATH);
        visualizeFormat = new VisualizeFormat();
        backgroundFormat = new BackgroundFormat();
        visualizePane = new VisualizePane(audioFile, visualizeFormat, backgroundFormat, rangeWidth, rangeHeight);

        fitPane = new ScrollPane();
        fitPane.setPrefSize(width * .68, height * .65);
        fitPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        fitPane.setStyle("-fx-background-color: #2B2B2B; -fx-border-color: #444444; -fx-border-width: 0 2 2 2;");

        // |----------------------------------------------------------------|
        // |                              Menu                              |
        // |----------|-----------------------------------------|-----------|
        // |          |                                         |           |
        // |          |                                         |           |
        // |   File   |           fitPane (Visualize)           |           |
        // |          |                                         |   Param   |
        // |          |                                         |           |
        // |----------|-----------------------------------------|           |
        // |                                                    |           |
        // |----------------------------------------------------|-----------|

        HBox hBox1 = new HBox(fileUI, fitPane);
        VBox vBox1 = new VBox(hBox1, new Pane());
        HBox hBox2 = new HBox(vBox1, paramUI);
        VBox vBox2 = new VBox(menuUI, hBox2);
        getChildren().add(vBox2);

        // Event
        // └ File UI
        fileUI.selectFileProperty.addListener(event -> changeAudio(fileUI.selectFileProperty.getValue()));
        // └ Menu UI
        //  └ File
        menuUI.fileNewClickProperty.addListener(event -> {
            CustomNewProjectDialog newProject = new CustomNewProjectDialog();
            newProject.setTitle("New Project");
            CustomNewProjectDialogFormat format = newProject.showAndReturn();

            if (format != null) {
                // 編輯視窗
                visualizeFormat = new VisualizeFormat();

                backgroundFormat = new BackgroundFormat();
                backgroundFormat.setBackgroundColor(format.getBackgroundColor());

                visualizePane = new VisualizePane(audioFile, visualizeFormat, backgroundFormat, format.getWidth(), format.getHeight());
                visualizePane.setView(format.getView());
                visualizePane.setSide(format.getSide());
                visualizePane.setDirect(format.getDirect());

                double scaleWidth = width * .68 / visualizePane.getPrefWidth();
                double scaleHeight = height * .65 / visualizePane.getPrefHeight();
                double scale = Math.min(scaleWidth, scaleHeight);
                double offsetX = 0 - (visualizePane.getPrefWidth() * (1 - scale)) / 2;
                double offsetY = 0 - (visualizePane.getPrefHeight() * (1 - scale)) / 2;
                if (scale == scaleWidth)
                    offsetY += (height * .65 - visualizePane.getPrefHeight() * scale) / 2;
                else if (scale == scaleHeight)
                    offsetX += (width * .68 - visualizePane.getPrefWidth() * scale) / 2;

                visualizePane.setScaleX(scale);
                visualizePane.setScaleY(scale);
                visualizePane.setTranslateX(offsetX);
                visualizePane.setTranslateY(offsetY);

                fitPane.setContent(visualizePane);

                // 參數視窗
                paramUI.setEqualizerType(format.getView());
                paramUI.setEqualizerSide(format.getSide());
                paramUI.setEqualizerDirection(format.getDirect());
                paramUI.setBackgroundColor(format.getBackgroundColor());
                paramUI.setRangeWidth(format.getWidth());
                paramUI.setRangeHeight(format.getHeight());

                System.out.println(format.getProjectName());
            }
        });
        //  └ Run
        menuUI.previewClickProperty.addListener(event -> preview());
        menuUI.animateClickProperty.addListener(event -> animate());
        // └ Param UI
        //  └ Equalizer
        //   └ Equalizer Type
        paramUI.equalizerTypeProperty.addListener(event -> {
            String equalizerType = paramUI.equalizerTypeProperty.getValue();

            for (VisualizeMode.View view: VisualizeMode.View.values())
                if (equalizerType.equals(view.toString()))
                    visualizePane.setView(view);

            preview();
        });
        //   └ Equalizer Side
        paramUI.equalizerSideProperty.addListener(event -> {
            String equalizerSide = paramUI.equalizerSideProperty.getValue();

            for (VisualizeMode.Side side: VisualizeMode.Side.values())
                if (equalizerSide.equals(side.toString()))
                    visualizePane.setSide(side);

            preview();
        });
        //   └ Equalizer Direction
        paramUI.equalizerDirectionProperty.addListener(event -> {
            String equalizerDirection = paramUI.equalizerDirectionProperty.getValue();

            for (VisualizeMode.Direct direct: VisualizeMode.Direct.values())
                if (equalizerDirection.equals(direct.toString()))
                    visualizePane.setDirect(direct);

            preview();
        });
        //   └ Equalizer Stereo
        paramUI.equalizerStereoProperty.addListener(event -> {
            String equalizerStereo = paramUI.equalizerStereoProperty.getValue();

            for (VisualizeMode.Stereo stereo: VisualizeMode.Stereo.values())
                if (equalizerStereo.equals(stereo.toString()))
                    visualizePane.setStereo(stereo);

            preview();
        });
        //   └ Bar Number
        paramUI.barNumberProperty.addListener(event -> {
            int barNum = paramUI.barNumberProperty.getValue();
            visualizeFormat.setBarNum(barNum);

            preview();
        });
        //   └ Size
        paramUI.sizeProperty.addListener(event -> {
            int barSize = paramUI.sizeProperty.getValue();
            visualizeFormat.setBarSize(barSize);

            preview();
        });
        //   └ Rotation
        paramUI.rotationProperty.addListener(event -> {
            int rotation = paramUI.rotationProperty.getValue();
            visualizeFormat.setRotation(rotation);

            preview();
        });
        //   └ Gap
        paramUI.gapProperty.addListener(event -> {
            int barGap = paramUI.gapProperty.getValue();
            visualizeFormat.setBarGap(barGap);

            preview();
        });
        //   └ Radius
        paramUI.radiusProperty.addListener(event -> {
            int radius = paramUI.radiusProperty.getValue();
            visualizeFormat.setRadius(radius);

            preview();
        });
        //   └ Position X
        paramUI.positionXProperty.addListener(event -> {
            int posX = paramUI.positionXProperty.getValue();
            visualizeFormat.setPosX(posX);

            preview();
        });
        //   └ Position Y
        paramUI.positionYProperty.addListener(event -> {
            int posY = paramUI.positionYProperty.getValue();
            visualizeFormat.setPosY(posY);

            preview();
        });
        //   └ Sensitivity
        paramUI.sensitivityProperty.addListener(event -> {
            double sensitivity = paramUI.sensitivityProperty.getValue() / 100.0;
            visualizeFormat.setSensitivity(sensitivity);

            preview();
        });
        //   └ Min Frequency
        paramUI.minFrequencyProperty.addListener(event -> {
            int minFreq = paramUI.minFrequencyProperty.getValue();
            visualizeFormat.setMinFreq(minFreq);

            preview();
        });
        //   └ Min Frequency
        paramUI.maxFrequencyProperty.addListener(event -> {
            int maxFreq = paramUI.maxFrequencyProperty.getValue();
            visualizeFormat.setMaxFreq(maxFreq);

            preview();
        });
        //   └ Color
        paramUI.colorProperty.addListener(event -> {
            Color barColor = Color.web(paramUI.colorProperty.getValue());
            visualizeFormat.setBarColor(barColor);

            preview();
        });
        //   └ Color Shadow
        paramUI.colorShadowProperty.addListener((obs, oldValue, newValue) -> {
            Color dropShadowColor = Color.web(newValue);
            visualizeFormat.setDropShadowColor(dropShadowColor);

            preview();
        });
        //   └ Color Shadow Radius
        paramUI.colorShadowRadiusProperty.addListener((obs, oldValue, newValue) -> {
            int dropShadowColorRadius = newValue.intValue();
            visualizeFormat.setDropShadowColorRadius(dropShadowColorRadius);

            preview();
        });
        //   └ Color Shadow Spread
        paramUI.colorShadowSpreadProperty.addListener((obs, oldValue, newValue) -> {
            double dropShadowColorSpread = newValue.doubleValue() / 100;
            visualizeFormat.setDropShadowColorSpread(dropShadowColorSpread);

            preview();
        });
        //   └ Color Shadow Offset X
        paramUI.colorShadowOffsetXProperty.addListener((obs, oldValue, newValue) -> {
            int dropShadowColorOffsetX = newValue.intValue();
            visualizeFormat.setDropShadowColorOffsetX(dropShadowColorOffsetX);

            preview();
        });
        //   └ Color Shadow Offset Y
        paramUI.colorShadowOffsetYProperty.addListener((obs, oldValue, newValue) -> {
            int dropShadowColorOffsetY = newValue.intValue();
            visualizeFormat.setDropShadowColorOffsetY(dropShadowColorOffsetY);

            preview();
        });
        //  └ Background
        //   └ Color
        paramUI.backgroundColorProperty.addListener((obs, oldValue, newValue) -> backgroundFormat.setBackgroundColor(Color.web(newValue)));
        //   └ Image
        paramUI.backgroundImageProperty.addListener((obs, oldValue, newValue) -> backgroundFormat.setBackgroundImage(newValue));
        //   └ Image Position X
        paramUI.backgroundImagePositionXProperty.addListener((obs, oldValue, newValue) -> backgroundFormat.setBackgroundImagePosX(newValue.intValue()));
        //   └ Image Position Y
        paramUI.backgroundImagePositionYProperty.addListener((obs, oldValue, newValue) -> backgroundFormat.setBackgroundImagePosY(newValue.intValue()));
    }

    public void changeAudio (String filepath) {
        if (visualizePane.isRunning())
            stop();
        visualizePane.clearAnimation();

        try {
            if (filepath.endsWith(".wav"))
                audioFile = new WavFile(filepath);
            else if (filepath.endsWith("mp3"))
                audioFile = new Mp3File(filepath);
            else
                throw new javax.sound.sampled.UnsupportedAudioFileException();

            visualizePane.setAudioFile(audioFile);
            paramUI.setChannels(audioFile.getChannels());
            //paramUI.setFrameRate(audioFile.getFrameRate());

            //audioFile.setVolume(paneFile.getVolume() / 100.0);
            //setTime(labelTime, 0, (int)audioFile.getDuration());
            //sliderTime.setMax(audioFile.getDuration());
            //EventLog.eventLog.songChange(songOld, songNew);
        } catch (javax.sound.sampled.UnsupportedAudioFileException | java.io.IOException e) {
            //EventLog.eventLog.warning("The file is Unsupported or Non-existent.");
        }
    }

    public void preview() {
        if (visualizePane.isRunning())
            stop();
        visualizePane.clearAnimation();
        visualizePane.preview();
    }

    public void animate() {
        if (visualizePane.isRunning())
            stop();
        visualizePane.clearAnimation();
        visualizePane.animate();
    }

    public void stop() {
        try {
            visualizePane.stop();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            //EventLog.eventLog.warning(e.getMessage());
        }
    }
}
