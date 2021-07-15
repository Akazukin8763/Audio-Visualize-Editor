package com.visualize.view;

public class ImageFormat {

    private String filepath;

    private double posX;
    private double posY;
    private double rotation;
    private double scaleX;
    private double scaleY;

    // Constructor
    public ImageFormat() {
        this(null, 0, 0, 0, 0, 0);
    }

    public ImageFormat(String filepath, double posX, double posY, double rotation, double scaleX, double scaleY) {
        this.filepath = filepath;

        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    // Methods
    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

}
