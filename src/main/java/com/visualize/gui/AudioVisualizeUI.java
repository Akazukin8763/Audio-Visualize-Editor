package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.event.Event;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class AudioVisualizeUI extends Pane {

    private final ProjectSL project = new ProjectSL();
    private String projectName = null;
    private String projectPath = null;

    private static final String TITLE = "Ɐudio Ʌisualizer Ǝditor";
    public final StringProperty titleProperty = new SimpleStringProperty(null);

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

    private final FileChooser fileChooser;

    // Constructor
    public AudioVisualizeUI(double width, double height) throws Exception {
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
        titleProperty.setValue(TITLE);

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Project", "*.xml"));

        menuUI = new MenuUI(width, height * .01);
        fileUI = new FileUI(width * .12, height * .65);
        paramUI = new ParamUI(width * .20, height * .94, rangeWidth, rangeHeight);
        menuUI.setMenuEnable(false);
        paramUI.setEnable(false);

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
            ProjectFormat format = newProject.showAndReturn();

            if (format != null) {
                this.projectName = format.getProjectName();
                this.rangeWidth = format.getWidth();
                this.rangeHeight = format.getHeight();

                setProject(format);

                menuUI.setMenuEnable(true, false); // 不啟用 Save, 只啟用 Save as
                paramUI.setEnable(true);
                titleProperty.setValue(TITLE + " - *" + projectName);
            }
        });
        menuUI.fileOpenClickProperty.addListener(event -> {
            try {
                projectPath = fileChooser.showOpenDialog(null).getAbsolutePath();
                ProjectFormat format = project.load(projectPath);

                this.projectName = format.getProjectName();
                this.rangeWidth = format.getWidth();
                this.rangeHeight = format.getHeight();

                setProject(format);

                menuUI.setMenuEnable(true); // 全啟用
                paramUI.setEnable(true);
                titleProperty.setValue(TITLE + " - " + projectName);
            } catch (NullPointerException ignored) {
                // 不做任何事
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuUI.fileSaveClickProperty.addListener(event -> {
            try {
                project.save(new ProjectFormat(
                                projectName, rangeWidth, rangeHeight, paramUI.isAdvancedEnable(),
                                paramUI.getView(), paramUI.getSide(), paramUI.getDirect(), paramUI.getStereo(), audioFile.getAbsolutePath(),
                                paramUI.getBarNum(), paramUI.getSize(), paramUI.getGap(), paramUI.getRadius(), paramUI.getPosX(), paramUI.getPosY(), paramUI.getRotation(),
                                paramUI.getColor(), paramUI.getColorShadow(), paramUI.getColorShadowRadius(), paramUI.getColorShadowSpread(), paramUI.getColorShadowOffsetX(), paramUI.getColorShadowOffsetY(),
                                paramUI.getSensitivity(), paramUI.getMinFreq(), paramUI.getMaxFreq(),
                                paramUI.getBackgroundColor(), paramUI.getBackgroundImage(), paramUI.getBackgroundImagePosX(), paramUI.getBackgroundImagePosY()),
                        projectPath);
                titleProperty.setValue(TITLE + " - " + projectName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuUI.fileSaveAsClickProperty.addListener(event -> {
            try {
                fileChooser.setInitialFileName(projectName);
                projectPath = fileChooser.showSaveDialog(null).getPath();
                projectName = new File(projectPath).getName();
                projectName = projectName.substring(0, projectName.length() - 4); // 去除 .xml

                project.save(new ProjectFormat(
                                projectName, rangeWidth, rangeHeight, paramUI.isAdvancedEnable(),
                                paramUI.getView(), paramUI.getSide(), paramUI.getDirect(), paramUI.getStereo(), audioFile.getAbsolutePath(),
                                paramUI.getBarNum(), paramUI.getSize(), paramUI.getGap(), paramUI.getRadius(), paramUI.getPosX(), paramUI.getPosY(), paramUI.getRotation(),
                                paramUI.getColor(), paramUI.getColorShadow(), paramUI.getColorShadowRadius(), paramUI.getColorShadowSpread(), paramUI.getColorShadowOffsetX(), paramUI.getColorShadowOffsetY(),
                                paramUI.getSensitivity(), paramUI.getMinFreq(), paramUI.getMaxFreq(),
                                paramUI.getBackgroundColor(), paramUI.getBackgroundImage(), paramUI.getBackgroundImagePosX(), paramUI.getBackgroundImagePosY()),
                        projectPath);
                menuUI.setMenuEnable(true); // Save as 完啟用 Save
                titleProperty.setValue(TITLE + " - " + projectName);
            } catch (NullPointerException ignored) {
                // 不做任何事
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //  └ Edit
        menuUI.editUndoClickProperty.addListener(event -> {
            System.out.println("Undo");
        });
        menuUI.editRedoClickProperty.addListener(event -> {
            System.out.println("Redo");
        });
        //  └ Run
        menuUI.previewClickProperty.addListener(event -> preview());
        menuUI.animateClickProperty.addListener(event -> animate());
        // └ Param UI
        //  └ Equalizer
        //   └ Equalizer Type
        paramUI.equalizerTypeProperty.addListener((obs, oldValue, newValue) -> {
            for (VisualizeMode.View view: VisualizeMode.View.values())
                if (newValue.equals(view.toString()))
                    visualizePane.setView(view);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Side
        paramUI.equalizerSideProperty.addListener((obs, oldValue, newValue) -> {
            for (VisualizeMode.Side side: VisualizeMode.Side.values())
                if (newValue.equals(side.toString()))
                    visualizePane.setSide(side);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Direction
        paramUI.equalizerDirectionProperty.addListener((obs, oldValue, newValue) -> {
            for (VisualizeMode.Direct direct: VisualizeMode.Direct.values())
                if (newValue.equals(direct.toString()))
                    visualizePane.setDirect(direct);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Stereo
        paramUI.equalizerStereoProperty.addListener((obs, oldValue, newValue) -> {
            for (VisualizeMode.Stereo stereo: VisualizeMode.Stereo.values())
                if (newValue.equals(stereo.toString()))
                    visualizePane.setStereo(stereo);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Bar Number
        paramUI.barNumberProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarNum(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Size
        paramUI.sizeProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarSize(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Rotation
        paramUI.rotationProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setRotation(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Gap
        paramUI.gapProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarGap(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Radius
        paramUI.radiusProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setRadius(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Position X
        paramUI.positionXProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setPosX(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Position Y
        paramUI.positionYProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setPosY(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Sensitivity
        paramUI.sensitivityProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setSensitivity(newValue.doubleValue() / 100);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Min Frequency
        paramUI.minFrequencyProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setMinFreq(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Min Frequency
        paramUI.maxFrequencyProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setMaxFreq(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color
        paramUI.colorProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarColor(Color.web(newValue));
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow
        paramUI.colorShadowProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColor(Color.web(newValue));
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Radius
        paramUI.colorShadowRadiusProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorRadius(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Spread
        paramUI.colorShadowSpreadProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorSpread(newValue.doubleValue() / 100);
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Offset X
        paramUI.colorShadowOffsetXProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorOffsetX(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Offset Y
        paramUI.colorShadowOffsetYProperty.addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorOffsetY(newValue.intValue());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //  └ Background
        //   └ Color
        paramUI.backgroundColorProperty.addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundColor(Color.web(newValue));
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Image
        paramUI.backgroundImageProperty.addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImage(newValue);
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Image Position X
        paramUI.backgroundImagePositionXProperty.addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImagePosX(newValue.intValue());
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //   └ Image Position Y
        paramUI.backgroundImagePositionYProperty.addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImagePosY(newValue.intValue());
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
        //  └ Image
        paramUI.imageFormatProperty.addListener(event -> {
            visualizePane.setImageFormat(paramUI.getImageFormat());
            preview();
            titleProperty.setValue(TITLE + " - *" + projectName);
        });
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

    public void setProject (ProjectFormat format) {
        // 編輯視窗
        visualizeFormat = new VisualizeFormat(
                format.getBarNum(), format.getSize(), format.getGap(), format.getRadius(),
                format.getPosX(), format.getPosY(), format.getRotation(), format.getColor());
        visualizeFormat.setDropShadowColor(format.getColorShadow());
        visualizeFormat.setDropShadowColorRadius(format.getColorShadowRadius());
        visualizeFormat.setDropShadowColorSpread(format.getColorShadowSpread() / 100);
        visualizeFormat.setDropShadowColorOffsetX(format.getColorShadowOffsetX());
        visualizeFormat.setDropShadowColorOffsetY(format.getColorShadowOffsetY());
        visualizeFormat.setSensitivity(format.getSensitivity() / 100);
        visualizeFormat.setMinFreq(format.getMinFreq());
        visualizeFormat.setMaxFreq(format.getMaxFreq());

        backgroundFormat = new BackgroundFormat(format.getBackgroundColor(), format.getBackgroundImage(), format.getBackgroundImagePosX(), format.getBackgroundImagePosY());

        visualizePane = new VisualizePane(audioFile, visualizeFormat, backgroundFormat, format.getWidth(), format.getHeight());
        visualizePane.setView(format.getView());
        visualizePane.setSide(format.getSide());
        visualizePane.setDirect(format.getDirect());
        visualizePane.setStereo(format.getStereo());

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
        paramUI.setRangeWidth(format.getWidth());
        paramUI.setRangeHeight(format.getHeight());

        paramUI.setAdvancedEnable(format.isAdvanced());

        paramUI.setView(format.getView());
        paramUI.setSide(format.getSide());
        paramUI.setDirect(format.getDirect());
        paramUI.setStereo(format.getStereo());

        // └ Music
        changeAudio(format.getFilepath());

        // └ Equalizer
        paramUI.setBarNum(format.getBarNum());
        paramUI.setSize(format.getSize());
        paramUI.setGap(format.getGap());
        paramUI.setRadius(format.getRadius());
        paramUI.setPosX(format.getPosX());
        paramUI.setPosY(format.getPosY());
        paramUI.setRotation(format.getRotation());
        paramUI.setColor(format.getColor());
        paramUI.setColorShadow(format.getColorShadow());
        paramUI.setColorShadowRadius(format.getColorShadowRadius());
        paramUI.setColorShadowSpread(format.getColorShadowSpread());
        paramUI.setColorShadowOffsetX(format.getColorShadowOffsetX());
        paramUI.setColorShadowOffsetY(format.getColorShadowOffsetY());
        paramUI.setSensitivity(format.getSensitivity());
        paramUI.setMinFreq(format.getMinFreq());
        paramUI.setMaxFreq(format.getMaxFreq());

        // └ Background
        paramUI.setBackgroundColor(format.getBackgroundColor());
        paramUI.setBackgroundImage(format.getBackgroundImage());
        paramUI.setBackgroundImagePosX(format.getBackgroundImagePosX());
        paramUI.setBackgroundImagePosY(format.getBackgroundImagePosY());
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
