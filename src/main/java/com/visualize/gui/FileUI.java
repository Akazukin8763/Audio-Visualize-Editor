package com.visualize.gui;

import com.visualize.file.*;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

import javafx.collections.FXCollections;

import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;

public class FileUI extends ScrollPane {

    private final double width;
    private final double height;

    private final VBox accordion;
    private final ImageView image;

    private final FileChooser fileChooser;

    private final ContextMenu menuFileGroup;
    private final MenuItem fileGroupNew;
    private final MenuItem fileGroupClear;

    private final ContextMenu menuFile;
    private final MenuItem fileAdd;
    private final MenuItem fileRename;

    private String selectTag = "";

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
        setContent(accordion);
        setPrefSize(width, height);

        this.fileChooser = new FileChooser();
        this.fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music", "*.wav", "*.mp3")); // 只抓 wav, mp3

        this.menuFileGroup = new ContextMenu();
        this.fileGroupNew = new MenuItem("New List");
        this.fileGroupClear = new MenuItem("Clear List");
        this.menuFileGroup.getItems().addAll(fileGroupNew, fileGroupClear);

        this.menuFile = new ContextMenu();
        this.fileAdd = new MenuItem("Add File");
        this.fileRename = new MenuItem("Rename List");
        this.menuFile.getItems().addAll(fileAdd, fileRename);

        createList("TEST");

        // Event
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            menuFileGroup.hide();
            menuFile.hide();
        });

        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                menuFileGroup.show(this, event.getScreenX(), event.getScreenY());
            }
            else {
                menuFileGroup.hide();
            }
        });

        fileGroupNew.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog("");
            textInputDialog.setTitle("New List");
            textInputDialog.setHeaderText(null);

            String tag = null;
            try{
                tag = textInputDialog.showAndWait().get().trim();
            } catch(NoSuchElementException ignored){
                // 不做任何事
            }
            if (tag != null && !tag.equals(""))
                System.out.println("Create List: " + createList(tag));
        });
        fileGroupClear.setOnAction(event -> clearList());

        fileAdd.setOnAction(event -> {
            try {
                List<File> files = fileChooser.showOpenMultipleDialog(null);
                fileChooser.setInitialDirectory(new File(files.get(0).getParent()));
                System.out.println("Insert List: " + insertList(selectTag, files));
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
        fileRename.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog(selectTag);
            textInputDialog.setTitle("Rename List");
            textInputDialog.setHeaderText(null);

            String newtag = null;
            try{
                newtag = textInputDialog.showAndWait().get().trim();
            } catch(NoSuchElementException ignored){
                // 不做任何事
            }
            if (newtag != null && !newtag.equals(""))
                System.out.println("Rename List: " + renameList(selectTag, newtag));
        });
    }

    // Methods
    public boolean createList(String tag) {
        // 標籤是否已建立，無則新增標籤
        if (tags.containsKey(tag))
            return false;

        // 新增標籤
        TitledPane list = new TitledPane(tag, new ListView<String>());
        //list.setGraphic(image);
        list.setAnimated(false);
        list.setExpanded(false);
        list.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                selectTag = list.getText();
                menuFile.show(this, event.getScreenX(), event.getScreenY());
            }
            else {
                menuFileGroup.hide();
                menuFile.hide();
            }
        });

        tags.put(tag, new HashSet<>());
        accordion.getChildren().add(list);

        return true;
    }

    public boolean insertList(String tag, List<File> files) {
        // 標籤是否已建立，有則新增路徑
        if (!tags.containsKey(tag))
            return false;

        // 新增檔案
        HashSet<String> fileList = tags.get(tag);
        for (File file: files)
            fileList.add("\t" + file.getAbsolutePath());

        tags.replace(tag, fileList);
        for (Node node: accordion.getChildren()) {
            TitledPane list = (TitledPane) node;
            if (list.getText().equals(tag)) {
                ListView<String> items = new ListView<>(FXCollections.observableArrayList(fileList));
                items.setPrefHeight(items.getItems().size() * 22 + 20);

                list.setContent(items);
                list.setExpanded(true);
                break;
            }
        }

        return true;
    }

    public boolean renameList(String tag, String newTag) {
        // 標籤是否已建立，無重複則更改名稱
        if (!tags.containsKey(tag) || tags.containsKey(newTag))
            return false;

        // 複製檔案並刪除
        tags.put(newTag, tags.get(tag));
        tags.remove(tag);

        for (Node node: accordion.getChildren()) {
            TitledPane list = (TitledPane) node;
            if (list.getText().equals(tag)) {
                list.setText(newTag);
                break;
            }
        }

        return true;
    }

    public boolean clearList() {
        if (tags.isEmpty())
            return false;

        tags.clear();
        accordion.getChildren().clear();

        return true;
    }

}
