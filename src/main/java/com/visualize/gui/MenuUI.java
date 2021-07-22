package com.visualize.gui;

import com.visualize.file.*;

import javafx.application.Platform;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Pos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.File;

public class MenuUI extends Pane {

    private final double width;
    private final double height;

    private final Label labelTitle;

    private final MenuItem fileNew;
    private final MenuItem fileOpen;
    private final MenuItem fileSave;
    private final MenuItem fileSaveAs;
    private final MenuItem fileImport;
    private final MenuItem fileExport;
    private final MenuItem fileExit;

    private final MenuItem editUndo;
    private final MenuItem editRedo;

    private final MenuItem runPreview;
    private final MenuItem runAnimate;

    // Property
    public BooleanProperty fileNewClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty fileOpenClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty fileSaveClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty fileSaveAsClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty fileImportClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty fileExportClickProperty = new SimpleBooleanProperty(false);

    public BooleanProperty editUndoClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty editRedoClickProperty = new SimpleBooleanProperty(false);

    public BooleanProperty previewClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty animateClickProperty = new SimpleBooleanProperty(false);

    public MenuUI (double width, double height) {
        this.width = width;
        this.height = height;
        this.setStyle("-fx-background-color: #2F3136; -fx-border-color: #444444; -fx-border-width: 0 0 2 0;");

        // File
        Menu menuFile = new Menu("File");
        fileNew = new MenuItem("New");
        fileOpen = new MenuItem("Open");
        fileSave = new MenuItem("Save");
        fileSaveAs = new MenuItem("Save As...");
        fileImport = new MenuItem("Import");
        fileExport = new MenuItem("Export");
        fileExit = new MenuItem("Exit");

        menuFile.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs);
        menuFile.getItems().add(new SeparatorMenuItem()); // 分隔線
        menuFile.getItems().addAll(fileImport, fileExport);
        menuFile.getItems().add(new SeparatorMenuItem()); // 分隔線
        menuFile.getItems().addAll(fileExit);

        // Edit
        Menu menuEdit = new Menu("Edit");
        editUndo = new MenuItem("Undo");
        editRedo = new MenuItem("Redo");

        menuEdit.getItems().addAll(editUndo, editRedo);

        // Run
        Menu menuRun = new Menu("Run");
        runPreview = new MenuItem("Preview");
        runAnimate = new MenuItem("Visualize");

        menuRun.getItems().addAll(runPreview, runAnimate);

        // Menu Bar
        MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuRun);
        menuBar.setPrefSize(width * .2 - height, height);

        // Icon
        ImageView icon = new ImageView(new Image(new File(DefaultPath.ICON_PATH).toURI().toString()));
        icon.setFitWidth(height);
        icon.setFitHeight(height);

        labelTitle = new Label("Ɐudio Ʌisualizer Ǝditor");
        labelTitle.setAlignment(Pos.CENTER);
        labelTitle.setPrefWidth(width * .6);
        labelTitle.setStyle("-fx-text-fill: #777777");

        // Window Button
        Button buttonExit = new Button();
        buttonExit.setGraphic(new ImageView(new Image(new File(DefaultPath.CANCEL_ICON_PATH).toURI().toString())));
        buttonExit.setStyle("-fx-background-color: transparent;");
        HBox groupButton = new HBox(buttonExit);
        groupButton.setAlignment(Pos.CENTER_RIGHT);
        groupButton.setPrefWidth(width * .2);

        HBox titleBar = new HBox(icon, menuBar, labelTitle, groupButton);
        titleBar.setAlignment(Pos.CENTER);
        getChildren().add(titleBar);

        // Event
        // └ File
        fileNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN)); // ctrl + n
        fileNew.setOnAction(event -> fileNewClickProperty.setValue(!fileNewClickProperty.getValue()));
        fileOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN)); // ctrl + o
        fileOpen.setOnAction(event -> fileOpenClickProperty.setValue(!fileOpenClickProperty.getValue()));
        fileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN)); // ctrl + s
        fileSave.setOnAction(event -> fileSaveClickProperty.setValue(!fileSaveClickProperty.getValue()));
        fileSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)); // ctrl + shift + s
        fileSaveAs.setOnAction(event -> fileSaveAsClickProperty.setValue(!fileSaveAsClickProperty.getValue()));
        fileImport.setOnAction(event -> fileImportClickProperty.setValue(!fileImportClickProperty.getValue()));
        fileExport.setOnAction(event -> fileExportClickProperty.setValue(!fileExportClickProperty.getValue()));
        fileExit.setOnAction(event -> Platform.exit());
        // └ Edit
        editUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN)); // ctrl + z
        editUndo.setOnAction(event -> editUndoClickProperty.setValue(!editUndoClickProperty.getValue()));
        editRedo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)); // ctrl + shift + z
        editRedo.setOnAction(event -> editRedoClickProperty.setValue(!editRedoClickProperty.getValue()));
        // └ Run
        runPreview.setAccelerator(new KeyCodeCombination(KeyCode.F10)); // F10
        runPreview.setOnAction(event -> previewClickProperty.setValue(!previewClickProperty.getValue()));
        runAnimate.setAccelerator(new KeyCodeCombination(KeyCode.F11)); // F11
        runAnimate.setOnAction(event -> animateClickProperty.setValue(!animateClickProperty.getValue()));
        // └ Window
        //  └ Close
        buttonExit.setOnAction(event -> Platform.exit());
        buttonExit.hoverProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue)
                buttonExit.setStyle("-fx-background-color: #E81123;");
            else
                buttonExit.setStyle("-fx-background-color: transparent;");
        });

    }

    // Methods
    public void setMenuEnable(boolean enable) {
        setMenuEnable(enable, enable);
    }
    public void setMenuEnable(boolean enable, boolean save) {
        boolean disable = !enable;

        //fileNew.setDisable(disable);
        //fileOpen.setDisable(disable);
        fileSave.setDisable(disable | !save);
        fileSaveAs.setDisable(disable);
        fileImport.setDisable(disable);
        fileExport.setDisable(disable);
        //fileExit.setDisable(disable);

        editUndo.setDisable(true); // 暫時
        editRedo.setDisable(true);

        runPreview.setDisable(disable);
        runAnimate.setDisable(disable);
    }

    public void setTitle(String title) {
        labelTitle.setText(title);
    }

}
