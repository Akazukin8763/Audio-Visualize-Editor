package com.visualize.view;

import javafx.scene.paint.Color;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class BackgroundFormat {

    private Color backgroundColor;

    // Property
    public final StringProperty backgroundColorProperty = new SimpleStringProperty(null);

    // Constructor
    public BackgroundFormat() {
        this(Color.WHITE);
    }

    public BackgroundFormat(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.backgroundColorProperty.setValue(backgroundColor.toString());
    }
}
