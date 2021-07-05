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

    private final int DE_WIDTH = 1920;

    private final double width;
    private final double height;

    private final MenuUI menuUI;
    private final FileUI fileUI;
    private final ParamUI paramUI;

    private final AudioFile audioFile;
    private final VisualizeFormat visualizeFormat;
    private final VisualizePane visualizePane;

    // Constructor
    public AudioVisualizeUI(double width, double height) throws Exception {
        this.width = width;
        this.height = height;
        setPrefSize(width, height);

        menuUI = new MenuUI(width, height);
        fileUI = new FileUI(width * .12, height * .65);
        paramUI = new ParamUI(width * .20, height * .65);

        audioFile = new WavFile("src/main/resources/music/__default__.wav");
        visualizeFormat = new VisualizeFormat();
        //visualizeFormat.setMinFreq(0);
        //visualizeFormat.setMaxFreq(0);
        visualizePane = new VisualizePane(audioFile, visualizeFormat);

        ScrollPane fitPane = new ScrollPane();
        fitPane.setContent(visualizePane);
        fitPane.setPrefSize(width * .68, height * .65);
        fitPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        double scale = width * .68 / DE_WIDTH;
        visualizePane.setScaleX(scale);
        visualizePane.setScaleY(scale);
        visualizePane.setTranslateX(0 - (visualizePane.getPrefWidth() * (1 - scale)) / 2);
        visualizePane.setTranslateY(0 - (visualizePane.getPrefHeight() * (1 - scale)) / 2);

        HBox hBox = new HBox(fileUI, fitPane, paramUI);
        VBox vBox = new VBox(menuUI, hBox);
        getChildren().add(vBox);

        //setStyle("-fx-background-color: lightgray");
        //vBox.setStyle("-fx-background-color: lightgray");

        // Event
        // └ File UI
        fileUI.selectFileProperty.addListener(event -> System.out.println(fileUI.selectFileProperty.getValue()));
        // └ Menu UI
        menuUI.previewClickProperty.addListener(event -> preview());
        menuUI.animateClickProperty.addListener(event -> animate());
        // └ Param UI
        //  └ Equalizer Type
        paramUI.equalizerTypeProperty.addListener(event -> {
            String equalizerType = paramUI.equalizerTypeProperty.getValue();

            for (VisualizeMode.View view: VisualizeMode.View.values())
                if (equalizerType.equals(view.toString()))
                    visualizePane.setView(view);

            preview();
        });
        //  └ Equalizer Side
        paramUI.equalizerSideProperty.addListener(event -> {
            String equalizerSide = paramUI.equalizerSideProperty.getValue();

            for (VisualizeMode.Side side: VisualizeMode.Side.values())
                if (equalizerSide.equals(side.toString()))
                    visualizePane.setSide(side);

            preview();
        });
        //  └ Equalizer Direction
        paramUI.equalizerDirectionProperty.addListener(event -> {
            String equalizerDirection = paramUI.equalizerDirectionProperty.getValue();

            for (VisualizeMode.Direct direct: VisualizeMode.Direct.values())
                if (equalizerDirection.equals(direct.toString()))
                    visualizePane.setDirect(direct);

            preview();
        });
        //  └ Equalizer Stereo
        paramUI.equalizerStereoProperty.addListener(event -> {
            String equalizerStereo = paramUI.equalizerStereoProperty.getValue();

            for (VisualizeMode.Stereo stereo: VisualizeMode.Stereo.values())
                if (equalizerStereo.equals(stereo.toString()))
                    visualizePane.setStereo(stereo);

            preview();
        });
        //  └ Bar Number
        paramUI.barNumberProperty.addListener(event -> {
            int barNum = paramUI.barNumberProperty.getValue();
            visualizeFormat.setBarNum(barNum);

            preview();
        });
        //  └ Size
        paramUI.sizeProperty.addListener(event -> {
            int barSize = paramUI.sizeProperty.getValue();
            visualizeFormat.setBarSize(barSize);

            preview();
        });
        //  └ Rotation
        paramUI.rotationProperty.addListener(event -> {
            int rotation = paramUI.rotationProperty.getValue();
            visualizeFormat.setRotation(rotation);

            preview();
        });
        //  └ Gap
        paramUI.gapProperty.addListener(event -> {
            int barGap = paramUI.gapProperty.getValue();
            visualizeFormat.setBarGap(barGap);

            preview();
        });
        //  └ Radius
        paramUI.radiusProperty.addListener(event -> {
            int radius = paramUI.radiusProperty.getValue();
            visualizeFormat.setRadius(radius);

            preview();
        });
        //  └ Position X
        paramUI.positionXProperty.addListener(event -> {
            int posX = paramUI.positionXProperty.getValue();
            visualizeFormat.setPosX(posX);

            preview();
        });
        //  └ Position Y
        paramUI.positionYProperty.addListener(event -> {
            int posY = paramUI.positionYProperty.getValue();
            visualizeFormat.setPosY(posY);

            preview();
        });
        //  └ Sensitivity
        paramUI.sensitivityProperty.addListener(event -> {
            double sensitivity = paramUI.sensitivityProperty.getValue() / 100.0;
            visualizeFormat.setSensitivity(sensitivity);

            preview();
        });
        //  └ Min Frequency
        paramUI.minFrequencyProperty.addListener(event -> {
            int minFreq = paramUI.minFrequencyProperty.getValue();
            visualizeFormat.setMinFreq(minFreq);

            preview();
        });
        //  └ Min Frequency
        paramUI.maxFrequencyProperty.addListener(event -> {
            int maxFreq = paramUI.maxFrequencyProperty.getValue();
            visualizeFormat.setMaxFreq(maxFreq);

            preview();
        });
        //  └ Color
        paramUI.colorProperty.addListener(event -> {
            Color barColor = Color.web(paramUI.colorProperty.getValue());
            visualizeFormat.setBarColor(barColor);

            preview();
        });
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
