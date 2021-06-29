package com.visualize.gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.event.Event;

public class AudioVisualizeUI extends Pane {

    private final MenuUI menuUI;
    private final FileUI fileUI;

    // Constructor
    public AudioVisualizeUI(double width, double height) {
        menuUI = new MenuUI(width, height);
        fileUI = new FileUI(width * .12, height * .55);

        fileUI.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //fileUI.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //fileUI.addEventFilter(ScrollEvent.SCROLL, Event::consume);

        HBox hBox = new HBox(fileUI, new Pane());
        VBox vBox = new VBox(menuUI, hBox);
        getChildren().add(vBox);
        //setTop(menuUI);
        //setCenter(fileUI);
    }

}
