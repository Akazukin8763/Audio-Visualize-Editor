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

public class MenuUI extends BorderPane {

    public MenuUI (double width, double height) {
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

        // Menu Bar
        MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuRun);
        menuBar.setPrefWidth(width);

        setTop(menuBar);

        // Event
        fileNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN)); // ctrl + n
        fileNew.setOnAction(event -> System.out.println("new"));
        fileOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN)); // ctrl + o
        fileOpen.setOnAction(event -> System.out.println("open"));
        fileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN)); // ctrl + s
        fileSave.setOnAction(event -> System.out.println("save"));
        fileSaveAs.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)); // ctrl + shift + s
        fileSaveAs.setOnAction(event -> System.out.println("save as"));
        fileExit.setOnAction(event -> Platform.exit());
    }

}
