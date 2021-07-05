package com.visualize.view;

import javafx.scene.paint.Color;

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

    private double sensitivity;

    private double minFreq;
    private double maxFreq;

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

        this.sensitivity = 1;

        this.minFreq = 0;
        this.maxFreq = 0;
    }

    // Methods
    public int getBarNum() {
        return barNum;
    }

    public void setBarNum(int barNum) {
        this.barNum = barNum;
    }

    public int getBarSize() {
        return barSize;
    }

    public void setBarSize(int barSize) {
        this.barSize = barSize;
    }

    public int getBarGap() {
        return barGap;
    }

    public void setBarGap(int barGap) {
        this.barGap = barGap;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Color getBarColor() {
        return barColor;
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
    }

    public Color getDropShadowColor() {
        return dropShadowColor;
    }

    public void setDropShadowColor(Color dropShadowColor) {
        this.dropShadowColor = dropShadowColor;
    }

    public double getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public double getMinFreq() {
        return minFreq;
    }

    public void setMinFreq(double minFreq) {
        this.minFreq = minFreq;
    }

    public double getMaxFreq() {
        return maxFreq;
    }

    public void setMaxFreq(double maxFreq) {
        this.maxFreq = maxFreq;
    }

}
