package com.visualize.gui;

import com.visualize.file.*;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ListView;

import javafx.collections.FXCollections;

import java.util.HashMap;
import java.util.HashSet;

import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FileUI extends ScrollPane {

    private final double width;
    private final double height;

    private final VBox accordion;
    private final ImageView image;

    private final HashMap<String, HashSet<String>> tags = new HashMap<>();

    // Constructor
    public FileUI(double width, double height) {
        this.width = width;
        this.height = height;
        this.image = new ImageView(new Image(new File(DefaultPath.FOLDER_ICON_PATH).toURI().toString(), 10, 10, false, false));
        //this.image.setFitHeight(.2);
        //this.image.setSmooth(true);

        accordion = new VBox();
        accordion.setPrefWidth(width);
        //accordion.setStyle("-fx-background-color: red");
        setContent(accordion);
        setPrefSize(width, height);

        createList("test1");
        createList("test2");
        createList("test2");
        createList("test3");
        createList("test4");

        insertList("test1", "path1");
        insertList("test1", "path2");
        insertList("test1", "path3");
        insertList("test2", "path4");
        insertList("test2", "path5");
        insertList("test1", "path13");
        insertList("test1", "path12");
        insertList("test3", "path5");
        insertList("test4", "path6");
        insertList("test3", "path8");
        insertList("test4", "path7");
    }

    // Methods
    public boolean createList(String tag) {
        // 標籤是否已建立，無則新增標籤
        if (tags.containsKey(tag))
            return false;

        // 新增標籤
        TitledPane list = new TitledPane(tag, new ListView<String>());
        list.setGraphic(image);
        list.setAnimated(false);
        list.setExpanded(false);
        //list.setPrefHeight(height * .5);

        tags.put(tag, new HashSet<>());
        accordion.getChildren().add(list);

        return true;
    }

    public boolean insertList(String tag, String filePath) {
        // 標籤是否已建立，有則新增路徑
        if (!tags.containsKey(tag))
            return false;

        // 新增檔案
        HashSet<String> fileList = tags.get(tag);
        fileList.add(filePath);

        tags.replace(tag, fileList);
        for (Node node: accordion.getChildren()) {
            TitledPane list = (TitledPane) node;
            if (list.getText().equals(tag)) {
                ListView<String> items = new ListView<>(FXCollections.observableArrayList(fileList));

                list.setContent(items);
                break;
            }
        }

        return true;
    }

}
