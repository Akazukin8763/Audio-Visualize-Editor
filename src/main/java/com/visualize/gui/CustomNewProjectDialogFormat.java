package com.visualize.gui;

import com.visualize.view.*;

import javafx.scene.paint.Color;

public final class CustomNewProjectDialogFormat {

    private String projectName;

    private int width;
    private int height;

    private VisualizeMode.View view;
    private VisualizeMode.Side side;
    private VisualizeMode.Direct direct;

    private Color backgroundColor;

    public CustomNewProjectDialogFormat(String projectName, int width, int height, VisualizeMode.View view, VisualizeMode.Side side, VisualizeMode.Direct direct, Color backgroundColor) {
        this.projectName = projectName;

        this.width = width;
        this.height = height;

        this.view = view;
        this.side = side;
        this.direct = direct;

        this.backgroundColor = backgroundColor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public VisualizeMode.View getView() {
        return view;
    }

    public void setView(VisualizeMode.View view) {
        this.view = view;
    }

    public VisualizeMode.Side getSide() {
        return side;
    }

    public void setSide(VisualizeMode.Side side) {
        this.side = side;
    }

    public VisualizeMode.Direct getDirect() {
        return direct;
    }

    public void setDirect(VisualizeMode.Direct direct) {
        this.direct = direct;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
