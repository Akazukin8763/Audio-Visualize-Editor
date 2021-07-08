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

    // Property
    public BooleanProperty fileNewClickProperty = new SimpleBooleanProperty(false);

    public BooleanProperty previewClickProperty = new SimpleBooleanProperty(false);
    public BooleanProperty animateClickProperty = new SimpleBooleanProperty(false);

    public MenuUI (double width, double height) {
        this.width = width;
        this.height = height;

        // File
        Menu menuFile = new Menu("File");
        MenuItem fileNew = new MenuItem("New");
        MenuItem fileOpen = new MenuItem("Open");
        MenuItem fileSave = new MenuItem("Save");
        MenuItem fileSaveAs = new MenuItem("Save As...");
        MenuItem fileImport = new MenuItem("Import");
        MenuItem fileExit = new MenuItem("Exit");

        menuFile.getItems().addAll(fileNew, fileOpen, fileSave, fileSaveAs);
        menuFile.getItems().add(new SeparatorMenuItem()); // 分隔線
        menuFile.getItems().addAll(fileImport);
        menuFile.getItems().add(new SeparatorMenuItem()); // 分隔線
        menuFile.getItems().addAll(fileExit);

        // Edit
        Menu menuEdit = new Menu("Edit");

        // Run
        Menu menuRun = new Menu("Run");
        MenuItem runPreview = new MenuItem("Preview");
        MenuItem runAnimate = new MenuItem("Visualize");

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
        fileOpen.setOnAction(event -> System.out.println("open"));
        fileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN)); // ctrl + s
        fileSave.setOnAction(event -> System.out.println("save"));
        fileSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)); // ctrl + shift + s
        fileSaveAs.setOnAction(event -> System.out.println("save as"));
        fileExit.setOnAction(event -> Platform.exit());
        // └ Run
        runPreview.setAccelerator(new KeyCodeCombination(KeyCode.F10)); // F10
        runPreview.setOnAction(event -> previewClickProperty.setValue(!previewClickProperty.getValue()));
        runAnimate.setAccelerator(new KeyCodeCombination(KeyCode.F11)); // F11
        runAnimate.setOnAction(event -> animateClickProperty.setValue(!animateClickProperty.getValue()));

    }

}
