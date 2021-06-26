package com.visualize.gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class paneMenu {
    BorderPane paneofMenu = new BorderPane();

    public paneMenu() {
        Menu menuFile = new Menu("File");
        Menu menuEdit = new Menu("Edit");
        Menu menuHelp = new Menu("Help");

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuFile,menuEdit,menuHelp);

        MenuItem fileA = new MenuItem("import");
        MenuItem fileB = new MenuItem("export");
        menuFile.getItems().addAll(fileA,fileB);
        //menuFile.getItems().add(new SeparatorMenuItem()); //分隔線
        paneofMenu.setTop(menuBar);

    }

    public Pane getPane() {
        return paneofMenu;
    }
}