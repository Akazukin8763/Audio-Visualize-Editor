package com.visualize.view;

import javafx.scene.paint.Color;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class VisualizeFormat {

    private int barNum; // 長條數量
    private int barSize; // 長條寬度

    private int barGap; // 長條間格
    private int radius; // 圓形半徑

    private double posX; // X 軸 (左下角錨點)
    private double posY; // Y 軸 (左下角錨點)
    private double rotation;

    private Color barColor;
    private Color dropShadowColor;
    private int dropShadowColorRadius;
    private double dropShadowColorSpread;
    private double dropShadowColorOffsetX;
    private double dropShadowColorOffsetY;

    private double sensitivity;

    private double minFreq;
    private double maxFreq;

    // Property
    public final IntegerProperty barNumProperty = new SimpleIntegerProperty();
    public final IntegerProperty barSizeProperty = new SimpleIntegerProperty();
    public final IntegerProperty rotationProperty = new SimpleIntegerProperty();
    public final IntegerProperty barGapProperty = new SimpleIntegerProperty();
    public final IntegerProperty radiusProperty = new SimpleIntegerProperty();
    public final IntegerProperty posXProperty = new SimpleIntegerProperty();
    public final IntegerProperty posYProperty = new SimpleIntegerProperty();
    public final IntegerProperty sensitivityProperty = new SimpleIntegerProperty();
    public final IntegerProperty minFreqProperty = new SimpleIntegerProperty();
    public final IntegerProperty maxFreqProperty = new SimpleIntegerProperty();

    public final StringProperty barColorProperty = new SimpleStringProperty(null);
    public final StringProperty dropShadowColorProperty = new SimpleStringProperty(null);
    public final IntegerProperty dropShadowColorRadiusProperty = new SimpleIntegerProperty();
    public final IntegerProperty dropShadowColorSpreadProperty = new SimpleIntegerProperty();

    // Constructor
    public VisualizeFormat () {
        this(0, 0, 0, 0, 0, 0, 0, Color.WHITE);
    }

    public VisualizeFormat (int barNum, int barSize, int barGap, int radius, double posX, double posY, double rotation, Color barColor) {
        // Normal
        this.barNum = barNum;
        this.barSize = barSize;

        this.barGap = barGap;
        this.radius = radius;

        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;

        this.barColor = barColor;

        // Advance
        this.dropShadowColor = Color.rgb(0, 0, 0, 0);
        this.dropShadowColorRadius = 0;
        this.dropShadowColorSpread = 0;
        this.dropShadowColorOffsetX = 0;
        this.dropShadowColorOffsetY = 0;

        this.sensitivity = .5;

        this.minFreq = 0;
        this.maxFreq = 0;
    }

    // Methods
    public int getBarNum() {
        return barNum;
    }

    public void setBarNum(int barNum) {
        this.barNum = barNum;
        this.barNumProperty.setValue(barNum); // Property
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
        this.barSizeProperty.setValue(barSize); // Property
    }

    public int getBarGap() {
        return barGap;
    }

    public void setBarGap(int barGap) {
        this.barGap = barGap;
        this.barGapProperty.setValue(barGap); // Property
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.radiusProperty.setValue(radius); // Property
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        this.posXProperty.setValue(posX); // Property
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        this.posYProperty.setValue(posY); // Property
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
        this.rotationProperty.setValue(rotation); // Property
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
        this.barColorProperty.setValue(barColor.toString()); // Property
    }

    public Color getDropShadowColor() {
        return dropShadowColor;
    }

    public void setDropShadowColor(Color dropShadowColor) {
        this.dropShadowColor = dropShadowColor;
        this.dropShadowColorProperty.setValue(dropShadowColor.toString());
    }

    public int getDropShadowColorRadius() {
        return dropShadowColorRadius;
    }

    public void setDropShadowColorRadius(int dropShadowColorRadius) {
        this.dropShadowColorRadius = dropShadowColorRadius;
        this.dropShadowColorRadiusProperty.setValue(dropShadowColorRadius);
    }

    public double getDropShadowColorSpread() {
        return dropShadowColorSpread;
    }

    public void setDropShadowColorSpread(double dropShadowColorSpread) {
        this.dropShadowColorSpread = dropShadowColorSpread;
        this.dropShadowColorSpreadProperty.setValue(dropShadowColorSpread);
    }

    public double getDropShadowColorOffsetX() {
        return dropShadowColorOffsetX;
    }

    public void setDropShadowColorOffsetX(double dropShadowColorOffsetX) {
        this.dropShadowColorOffsetX = dropShadowColorOffsetX;
    }

    public double getDropShadowColorOffsetY() {
        return dropShadowColorOffsetY;
    }

    public void setDropShadowColorOffsetY(double dropShadowColorOffsetY) {
        this.dropShadowColorOffsetY = dropShadowColorOffsetY;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
        this.sensitivityProperty.setValue(sensitivity); // Property
    }

    public double getMinFreq() {
        return minFreq;
    }

    public void setMinFreq(double minFreq) {
        this.minFreq = minFreq;
        this.minFreqProperty.setValue(minFreq); // Property
    }

    public double getMaxFreq() {
        return maxFreq;
    }

    public void setMaxFreq(double maxFreq) {
        this.maxFreq = maxFreq;
        this.maxFreqProperty.setValue(maxFreq); // Property
    }

}
