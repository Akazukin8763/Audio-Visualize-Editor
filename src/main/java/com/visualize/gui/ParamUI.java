package com.visualize.gui;

import javafx.scene.layout.GridPane;

import javafx.scene.control.ScrollPane;

public class ParamUI extends ScrollPane {

    private final double width;
    private final double height;

    private final GridPane paramPane;

    public ParamUI(double width, double height) {
        this.width = width;
        this.height = height;

        paramPane = new GridPane();
        paramPane.setStyle("-fx-background-color: lightgray");
        setContent(paramPane);
        //setPrefSize(width, height);
    }
}
