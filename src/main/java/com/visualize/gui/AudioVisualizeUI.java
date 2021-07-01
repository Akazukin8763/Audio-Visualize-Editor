package com.visualize.gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;

public class AudioVisualizeUI extends Pane {

    private final MenuUI menuUI;
    private final FileUI fileUI;
    private final ParamUI paramUI;

    // Constructor
    public AudioVisualizeUI(double width, double height) {
        menuUI = new MenuUI(width, height);
        fileUI = new FileUI(width * .12, height * .65);
        paramUI = new ParamUI(width * .12, height * .65);

        Pane pane = new Pane();
        pane.setPrefSize(width * .68, height * .65);
        pane.setStyle("-fx-background-color: lightblue");

        HBox hBox = new HBox(fileUI, pane, paramUI);
        VBox vBox = new VBox(menuUI, hBox);
        getChildren().add(vBox);
        //setPrefSize(width, height);

        System.out.println(fileUI.getPrefWidth());
        System.out.println(pane.getPrefWidth());
        System.out.println(paramUI.getPrefWidth());
        //hBox.setStyle("-fx-background-color: lightgray");

        fileUI.selectFileProperty.addListener(event -> System.out.println(fileUI.selectFileProperty.getValue()));
    }

}
