package com.visualize.view;

import javafx.scene.paint.Color;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BackgroundFormat {

    private Color backgroundColor;
    private String backgroundImage;

    private int backgroundImagePosX;
    private int backgroundImagePosY;

    // Property
    public final StringProperty backgroundColorProperty = new SimpleStringProperty(null);
    public final StringProperty backgroundImageProperty = new SimpleStringProperty(null);

    public final IntegerProperty backgroundImagePosXProperty = new SimpleIntegerProperty();
    public final IntegerProperty backgroundImagePosYProperty = new SimpleIntegerProperty();

    // Constructor
    public BackgroundFormat() {
        this(Color.WHITE, null, 0, 0);
    }

    public BackgroundFormat(Color backgroundColor, String backgroundImage, int backgroundImagePosX, int backgroundImagePosY) {
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;

        this.backgroundImagePosX = backgroundImagePosX;
        this.backgroundImagePosY = backgroundImagePosY;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.backgroundColorProperty.setValue(backgroundColor.toString());
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.backgroundImageProperty.setValue(backgroundImage);
    }

    public int getBackgroundImagePosX() {
        return backgroundImagePosX;
    }

    public void setBackgroundImagePosX(int backgroundImagePosX) {
        this.backgroundImagePosX = backgroundImagePosX;
        this.backgroundImagePosXProperty.setValue(backgroundImagePosX);
    }

    public int getBackgroundImagePosY() {
        return backgroundImagePosY;
    }

    public void setBackgroundImagePosY(int backgroundImagePosY) {
        this.backgroundImagePosY = backgroundImagePosY;
        this.backgroundImagePosYProperty.setValue(backgroundImagePosY);
    }
}
