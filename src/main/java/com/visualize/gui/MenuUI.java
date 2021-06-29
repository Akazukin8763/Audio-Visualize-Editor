package com.visualize.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuUI extends BorderPane {

    public MenuUI (double width, double height) {
        // File
        Menu menuFile = new Menu("File");
        MenuItem menuItemNew = new MenuItem("New");
        MenuItem menuItemOpen = new MenuItem("Open");
        MenuItem menuItemSave = new MenuItem("Save");
        MenuItem menuItemExit = new MenuItem("Exit");

        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemExit);

        // Edit
        Menu menuEdit = new Menu("Edit");

        // Run
        Menu menuRun = new Menu("Run");

        // Menu Bar
        MenuBar menuBar = new MenuBar(menuFile, menuEdit, menuRun);
        menuBar.setPrefWidth(width);

        setTop(menuBar);
    }

}
