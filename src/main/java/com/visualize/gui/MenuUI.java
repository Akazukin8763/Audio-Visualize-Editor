package com.visualize.gui;

import javafx.scene.layout.BorderPane;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import javafx.application.Platform;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class MenuUI extends BorderPane {

    private final double width;
    private final double height;

    private final MenuItem fileNew;
    private final MenuItem fileOpen;
    private final MenuItem fileSave;
    private final MenuItem fileSaveAs;
    private final MenuItem fileImport;
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

    public BooleanProperty editUndoClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty editRedoClickProperty = new SimpleBooleanProperty(false);

    public BooleanProperty previewClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty animateClickProperty = new SimpleBooleanProperty(false);

    public MenuUI (double width, double height) {
        this.width = width;
        this.height = height;

        // File
        Menu menuFile = new Menu("File");
        fileNew = new MenuItem("New");
        fileOpen = new MenuItem("Open");
        fileSave = new MenuItem("Save");
        fileSaveAs = new MenuItem("Save As...");
        fileImport = new MenuItem("Import");
        fileExit = new MenuItem("Exit");

        menuFile.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs);
        menuFile.getItems().add(new SeparatorMenuItem()); // 分隔線
        menuFile.getItems().addAll(fileImport);
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
        menuBar.setPrefSize(width, height);

        setTop(menuBar);

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
        //fileExit.setDisable(disable);

        editUndo.setDisable(disable);
        editRedo.setDisable(disable);

        runPreview.setDisable(disable);
        runAnimate.setDisable(disable);
    }

}
