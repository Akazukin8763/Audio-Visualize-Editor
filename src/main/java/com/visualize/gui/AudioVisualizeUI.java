package com.visualize.gui;

import com.visualize.file.*;
import com.visualize.view.*;
import com.visualize.object.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.event.Event;

import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class AudioVisualizeUI extends Pane {

    private final ProjectSL project = new ProjectSL();
    private String projectName = null;
    private String projectPath = null;

    private static final String TITLE = "Ɐudio Ʌisualizer Ǝditor";

    private final double width;
    private final double height;
    private int rangeWidth = 1920;
    private int rangeHeight = 1080;

    private final MenuUI menuUI;
    private final FileUI fileUI;
    private final ParamUI paramUI;
    private final TimelineUI timelineUI;

    private AudioFile audioFile;
    private VisualizeFormat visualizeFormat;
    private BackgroundFormat backgroundFormat;
    private VisualizePane visualizePane;

    private ScrollPane fitPane;

    private final FileChooser projectChooser;
    private final FileChooser videoChooser;

    // Constructor
    public AudioVisualizeUI(double width, double height) throws Exception {
        this.width = width;
        this.height = height;
        this.setPrefSize(width, height);

        projectChooser = new FileChooser();
        projectChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Project", "*.xml"));
        videoChooser = new FileChooser();
        videoChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video", "*.mp4"));

        menuUI = new MenuUI(width, height * .03);
        fileUI = new FileUI(width * .12, height * .65);
        paramUI = new ParamUI(width * .20, height * .97, rangeWidth, rangeHeight);
        timelineUI = new TimelineUI(width * .80, height * .03);
        menuUI.setMenuEnable(false);
        paramUI.setEnable(false);
        timelineUI.setEnable(false);

        audioFile = new WavFile(DefaultPath.DEFAULT_MUSIC_PATH);
        visualizeFormat = new VisualizeFormat();
        backgroundFormat = new BackgroundFormat();
        visualizePane = new VisualizePane(audioFile, visualizeFormat, backgroundFormat, rangeWidth, rangeHeight);

        fitPane = new ScrollPane();
        fitPane.setPrefSize(width * .68, height * .65);
        fitPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        fitPane.addEventFilter(ScrollEvent.SCROLL, Event::consume);
        fitPane.setStyle("-fx-background-color: #2B2B2B; -fx-border-color: #444444; -fx-border-width: 0 2 0 2;");

        // |----------------------------------------------------------------|
        // |                              Menu                              |
        // |----------|-----------------------------------------|-----------|
        // |          |                                         |           |
        // |          |                                         |           |
        // |   File   |           fitPane (Visualize)           |           |
        // |          |                                         |   Param   |
        // |          |                                         |           |
        // |----------|-----------------------------------------|           |
        // |                      TimeLine                      |           |
        // |----------------------------------------------------|           |
        // |                                                    |           |
        // |----------------------------------------------------|-----------|

        BorderPane idk = new BorderPane();
        idk.setCenter(new Label("不知道放莎曉"));
        idk.setStyle("-fx-background-color: #2B2B2B; -fx-border-color: #444444; -fx-border-width: 0 2 0 0;");
        idk.setPrefSize(width * .80, height * .29);

        HBox hBox1 = new HBox(fileUI, fitPane);
        VBox vBox1 = new VBox(hBox1, timelineUI, idk);
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
                //timelineUI.setEnable(true);
                menuUI.setTitle(TITLE + " - " + projectName);
            }
        });
        menuUI.fileOpenClickProperty.addListener(event -> {
            try {
                projectPath = projectChooser.showOpenDialog(null).getAbsolutePath();
                ProjectFormat format = project.load(projectPath);

                if (format.getProjectName().equals(projectName))
                    return;

                this.projectName = format.getProjectName();
                this.rangeWidth = format.getWidth();
                this.rangeHeight = format.getHeight();

                setProject(format);

                menuUI.setMenuEnable(true); // 全啟用
                paramUI.setEnable(true);
                //timelineUI.setEnable(true);
                menuUI.setTitle(TITLE + " - " + projectName);
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
                                paramUI.getBackgroundColor(), paramUI.getBackgroundImage(), paramUI.getBackgroundImagePosX(), paramUI.getBackgroundImagePosY(),
                                paramUI.getImageFormat()),
                        projectPath);
                menuUI.setTitle(TITLE + " - " + projectName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuUI.fileSaveAsClickProperty.addListener(event -> {
            try {
                projectChooser.setInitialFileName(projectName);
                projectPath = projectChooser.showSaveDialog(null).getPath();
                projectName = new File(projectPath).getName();
                projectName = projectName.substring(0, projectName.length() - 4); // 去除 .xml

                project.save(new ProjectFormat(
                                projectName, rangeWidth, rangeHeight, paramUI.isAdvancedEnable(),
                                paramUI.getView(), paramUI.getSide(), paramUI.getDirect(), paramUI.getStereo(), audioFile.getAbsolutePath(),
                                paramUI.getBarNum(), paramUI.getSize(), paramUI.getGap(), paramUI.getRadius(), paramUI.getPosX(), paramUI.getPosY(), paramUI.getRotation(),
                                paramUI.getColor(), paramUI.getColorShadow(), paramUI.getColorShadowRadius(), paramUI.getColorShadowSpread(), paramUI.getColorShadowOffsetX(), paramUI.getColorShadowOffsetY(),
                                paramUI.getSensitivity(), paramUI.getMinFreq(), paramUI.getMaxFreq(),
                                paramUI.getBackgroundColor(), paramUI.getBackgroundImage(), paramUI.getBackgroundImagePosX(), paramUI.getBackgroundImagePosY(),
                                paramUI.getImageFormat()),
                        projectPath);
                menuUI.setMenuEnable(true); // Save as 完啟用 Save
                menuUI.setTitle(TITLE + " - " + projectName);
            } catch (NullPointerException ignored) {
                // 不做任何事
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        menuUI.fileImportClickProperty.addListener(event -> System.out.println("Import"));
        menuUI.fileExportClickProperty.addListener(event -> export());
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
        paramUI.equalizerTypeProperty().addListener((obs, oldValue, newValue) -> {
            visualizePane.setView(newValue);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Side
        paramUI.equalizerSideProperty().addListener((obs, oldValue, newValue) -> {
            visualizePane.setSide(newValue);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Direction
        paramUI.equalizerDirectionProperty().addListener((obs, oldValue, newValue) -> {
            visualizePane.setDirect(newValue);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Equalizer Stereo
        paramUI.equalizerStereoProperty().addListener((obs, oldValue, newValue) -> {
            visualizePane.setStereo(newValue);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Bar Number
        paramUI.barNumberProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarNum(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Size
        paramUI.sizeProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarSize(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Rotation
        paramUI.rotationProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setRotation(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Gap
        paramUI.gapProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarGap(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Radius
        paramUI.radiusProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setRadius(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Position X
        paramUI.positionXProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setPosX(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Position Y
        paramUI.positionYProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setPosY(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Sensitivity
        paramUI.sensitivityProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setSensitivity(newValue.doubleValue() / 100);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Min Frequency
        paramUI.minFrequencyProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setMinFreq(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Min Frequency
        paramUI.maxFrequencyProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setMaxFreq(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color
        paramUI.colorProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setBarColor(Color.web(newValue));
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow
        paramUI.colorShadowProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColor(Color.web(newValue));
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Radius
        paramUI.colorShadowRadiusProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorRadius(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Spread
        paramUI.colorShadowSpreadProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorSpread(newValue.doubleValue() / 100);
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Offset X
        paramUI.colorShadowOffsetXProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorOffsetX(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Color Shadow Offset Y
        paramUI.colorShadowOffsetYProperty().addListener((obs, oldValue, newValue) -> {
            visualizeFormat.setDropShadowColorOffsetY(newValue.intValue());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //  └ Background
        //   └ Color
        paramUI.backgroundColorProperty().addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundColor(Color.web(newValue));
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Image
        paramUI.backgroundImageProperty().addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImage(newValue);
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Image Position X
        paramUI.backgroundImagePositionXProperty().addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImagePosX(newValue.intValue());
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //   └ Image Position Y
        paramUI.backgroundImagePositionYProperty().addListener((obs, oldValue, newValue) -> {
            backgroundFormat.setBackgroundImagePosY(newValue.intValue());
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        //  └ Image
        paramUI.imageFormatProperty().addListener(event -> {
            visualizePane.setImageFormat(paramUI.getImageFormat());
            preview();
            menuUI.setTitle(TITLE + " - *" + projectName);
        });
        // └ Timeline UI
        //  └ Volume
        timelineUI.volumeProperty().addListener((obs, oldValue, newValue) -> audioFile.setVolume(newValue.doubleValue() / 100));
        //  └ Play Status
        timelineUI.playStatusProperty().addListener((obs, oldValue, newValue) -> {
            switch (newValue) {
                case PLAY:
                    visualizePane.play();
                    break;
                case RESUME:
                    visualizePane.playFrom(timelineUI.getCurrentDuration());
                    break;
                case PAUSE:
                    visualizePane.pause();
                    break;
                case STOP:
                    visualizePane.stop();
                    break;
                default:
                    throw new IllegalArgumentException("Doesn't support this play mode.");
            }
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

            String filename = new File(filepath).getName();

            visualizePane.setAudioFile(audioFile);
            paramUI.setChannels(audioFile.getChannels());

            audioFile.setVolume(timelineUI.volumeProperty().doubleValue() / 100);

            timelineUI.setFilename(filename.substring(0, filename.length() - 4)); // 去除 .wav / .mp3
            timelineUI.setTotalDuration(audioFile.getDuration());
            timelineUI.setCurrentDuration(0);

            //paramUI.setFrameRate(audioFile.getFrameRate());

            //EventLog.eventLog.songChange(songOld, songNew);
        } catch (javax.sound.sampled.UnsupportedAudioFileException | java.io.IOException e) {
            //EventLog.eventLog.warning("The file is Unsupported or Non-existent.");
        }
    }

    public void setProject (ProjectFormat format) {
        if (visualizePane.isRunning())
            visualizePane.stop();

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

        // └ Image
        paramUI.setImageFormat(format.getImageFormat());

        // Event
        // └ VisualizePane
        visualizePane.timeProperty.addListener((obs, oldValue, newValue) -> {
            double duration = newValue.doubleValue();

            timelineUI.setCurrentDuration(duration);
        });
    }

    public void preview() {
        if (visualizePane.isRunning())
            stop();
        visualizePane.clearAnimation();
        visualizePane.preview();

        timelineUI.setEnable(false);
    }

    public void animate() {
        if (visualizePane.isRunning())
            stop();
        visualizePane.clearAnimation();
        visualizePane.animate();

        timelineUI.initPlayer();
        timelineUI.setEnable(true);
    }

    public void export() {
        if (visualizePane.isRunning())
            stop();

        try {
            videoChooser.setInitialFileName(projectName);
            String path = videoChooser.showSaveDialog(null).getPath();

            //EventLog.eventLog.encode("Video Fetching...");
            visualizePane.saveVideo(path);
            //EventLog.eventLog.encode("Video Encoding...");
        } catch (NullPointerException ignored) {
            // 不做任何事
        } catch (java.io.IOException e) {
            //EventLog.eventLog.warning("The file can not be exported correctly.");
        }
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
