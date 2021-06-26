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

    // Constructor
    public VisualizeFormat (int barNum, int barSize, int barGap, int radius, double posX, double posY, double rotation, Color barColor, Color dropShadowColor) {
        this(barNum, barSize, barGap, radius, posX, posY, rotation, barColor, dropShadowColor, 1);
    }

    public VisualizeFormat (int barNum, int barSize, int barGap, int radius, double posX, double posY, double rotation, Color barColor, Color dropShadowColor, double sensitivity) {
        this.barNum = barNum;
        this.barSize = barSize;

        this.barGap = barGap;
        this.radius = radius;

        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;

        this.barColor = barColor;
        this.dropShadowColor = dropShadowColor;

        this.sensitivity = sensitivity;
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
}
