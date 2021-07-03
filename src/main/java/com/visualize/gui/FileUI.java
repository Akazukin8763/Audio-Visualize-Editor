package com.visualize.gui;

import com.visualize.file.*;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Control;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;

import javafx.collections.FXCollections;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.input.KeyCode;

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

    private final HashMap<String, LinkedHashSet<String>> tags = new HashMap<>();

    public final StringProperty selectFileProperty = new SimpleStringProperty(null);

    // Constructor
    public FileUI(double width, double height) {
        this.width = width;
        this.height = height;
        this.image = new ImageView(new Image(new File(DefaultPath.FOLDER_ICON_PATH).toURI().toString(), 10, 10, false, false));
        //this.image.setFitHeight(.2);
        //this.image.setSmooth(true);

        accordion = new VBox();
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
        // └ this
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setOnMousePressed(event -> {
            menuFileGroup.hide();
            menuFile.hide();
        });
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) { // 右鍵功能列表
                menuFileGroup.show(this, event.getScreenX(), event.getScreenY());
            }
            else {
                menuFileGroup.hide();
            }
        });
        this.setOnMouseEntered(event -> this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED));
        this.setOnMouseExited(event -> this.setVbarPolicy(ScrollBarPolicy.NEVER));
        // └ 總體列表
        //  └ 新增列表
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
        //  └ 清除所有列表
        fileGroupClear.setOnAction(event -> clearList());
        // └ 個別列表
        //  └ 新增音樂至列表
        fileAdd.setOnAction(event -> {
            try {
                List<File> files = fileChooser.showOpenMultipleDialog(null);
                fileChooser.setInitialDirectory(new File(files.get(0).getParent()));
                System.out.println("Insert List: " + insertListFile(selectTag, files));
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
        //  └ 列表改名
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
        ListView<String> items = new ListView<>();
        items.setPrefHeight(20);

        TitledPane list = new TitledPane(tag, items);
        //list.setGraphic(image);
        list.setPrefWidth(width);
        list.setAnimated(false);
        list.setExpanded(false);
        list.setOnMouseClicked(event -> {
            selectTag = list.getText();
            if (event.getButton() == MouseButton.SECONDARY) // 右鍵功能列表
                menuFile.show(this, event.getScreenX(), event.getScreenY());
            else {
                menuFileGroup.hide();
                menuFile.hide();
            }
        });

        tags.put(tag, new LinkedHashSet<>());
        accordion.getChildren().add(list);

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
            if (list.getText().equals(tag)) { // 尋找該標籤
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

    public boolean insertListFile(String tag, List<File> files) {
        // 標籤是否已建立，有則新增路徑
        if (!tags.containsKey(tag))
            return false;

        // 新增檔案
        LinkedHashSet<String> fileList = tags.get(tag);
        for (File file: files)
            fileList.add(file.getAbsolutePath());

        refreshListFile(tag, fileList);

        return true;
    }

    public boolean deleteListFile(String tag, String filepath) {
        // 標籤是否已建立，有則新增路徑
        if (!tags.containsKey(tag))
            return false;

        // 刪除檔案
        LinkedHashSet<String> fileList = tags.get(tag);
        fileList.remove(filepath);

        refreshListFile(tag, fileList);

        return true;
    }

    private void refreshListFile(String tag, LinkedHashSet<String> fileList) {
        tags.replace(tag, fileList);
        for (Node node: accordion.getChildren()) {
            TitledPane list = (TitledPane) node;
            if (list.getText().equals(tag)) { // 尋找該標籤
                List<String> filenames = fileList.stream().map(file -> "\t" + new File(file).getName()).collect(Collectors.toList()); // 添加檔名至所屬標籤
                ListView<String> items = new ListView<>(FXCollections.observableArrayList(filenames));

                items.getSelectionModel().clearSelection();
                items.setPrefHeight(items.getItems().size() * 22 + 20);
                items.setCellFactory(param -> new ListCell<>() {
                    {
                        prefWidthProperty().bind(items.widthProperty().subtract(2));
                        setMaxWidth(Control.USE_PREF_SIZE);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null && !empty)
                            setText(item);
                        else
                            setText(null);
                    }
                });
                items.setOnMouseClicked(event -> {
                    selectTag = list.getText();
                    if (event.getButton() == MouseButton.SECONDARY) // 右鍵功能列表
                        menuFile.show(this, event.getScreenX(), event.getScreenY());
                    else {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            int index = items.getSelectionModel().getSelectedIndex();

                            if (index != -1) {
                                if (event.getClickCount() >= 2) // 左鍵雙擊或以上，設定所選檔案
                                    selectFileProperty.setValue(new ArrayList<>(fileList).get(index));
                            }
                        }
                        menuFile.hide();
                    }
                });
                items.setOnKeyPressed(event -> {
                    int index = items.getSelectionModel().getSelectedIndex();

                    if (index != -1) {
                        if (event.getCode() == KeyCode.BACK_SPACE) { // 左鍵單擊，可刪除所選檔案
                            items.getItems().remove(index);
                            items.getSelectionModel().clearSelection();
                            deleteListFile(selectTag, new ArrayList<>(fileList).get(index));
                        }
                    }
                });

                list.setContent(items);
                list.setExpanded(true);
                break;
            }
        }
    }

}
