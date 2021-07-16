package com.visualize.file;

import com.visualize.view.*;

import javafx.scene.paint.Color;

import java.util.List;

public class ProjectFormat {

    private String projectName;

    private int width;
    private int height;

    private boolean advanced;

    private VisualizeMode.View view;
    private VisualizeMode.Side side;
    private VisualizeMode.Direct direct;
    private VisualizeMode.Stereo stereo;

    // Music
    private String filepath;

    // Equalizer
    private int barNum; // 長條數量
    private int size; // 長條寬度

    private int gap; // 長條間格
    private int radius; // 圓形半徑

    private double posX; // X 軸 (左下角錨點)
    private double posY; // Y 軸 (左下角錨點)
    private double rotation;

    private Color color;
    private Color colorShadow;
    private int colorShadowRadius;
    private double colorShadowSpread;
    private double colorShadowOffsetX;
    private double colorShadowOffsetY;

    private double sensitivity;

    private double minFreq;
    private double maxFreq;

    // Background
    private Color backgroundColor;
    private String backgroundImage;

    private int backgroundImagePosX;
    private int backgroundImagePosY;

    // Image
    private List<ImageFormat> imageFormat;

    // Constructor
    public ProjectFormat(String projectName, int width, int height, boolean advanced,
                         VisualizeMode.View view, VisualizeMode.Side side, VisualizeMode.Direct direct, VisualizeMode.Stereo stereo, String filepath,
                         int barNum, int size, int gap, int radius, double posX, double posY, double rotation,
                         Color color, Color colorShadow, int colorShadowRadius, double colorShadowSpread, double colorShadowOffsetX, double colorShadowOffsetY,
                         double sensitivity, double minFreq, double maxFreq,
                         Color backgroundColor, String backgroundImage, int backgroundImagePosX, int backgroundImagePosY,
                         List<ImageFormat> imageFormat) {
        this.projectName = projectName;

        this.width = width;
        this.height = height;

        this.advanced = advanced;

        this.view = view;
        this.side = side;
        this.direct = direct;
        this.stereo = stereo;

        this.filepath = filepath;

        this.barNum = barNum;
        this.size = size;
        this.gap = gap;
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        this.rotation = rotation;
        this.color = color;
        this.colorShadow = colorShadow;
        this.colorShadowRadius = colorShadowRadius;
        this.colorShadowSpread = colorShadowSpread;
        this.colorShadowOffsetX = colorShadowOffsetX;
        this.colorShadowOffsetY = colorShadowOffsetY;
        this.sensitivity = sensitivity;
        this.minFreq = minFreq;
        this.maxFreq = maxFreq;

        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.backgroundImagePosX = backgroundImagePosX;
        this.backgroundImagePosY = backgroundImagePosY;

        this.imageFormat = imageFormat;
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

    public boolean isAdvanced() {
        return advanced;
    }

    public void setAdvanced(boolean advanced) {
        this.advanced = advanced;
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

    public VisualizeMode.Stereo getStereo() {
        return stereo;
    }

    public void setStereo(VisualizeMode.Stereo stereo) {
        this.stereo = stereo;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getBarNum() {
        return barNum;
    }

    public void setBarNum(int barNum) {
        this.barNum = barNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorShadow() {
        return colorShadow;
    }

    public void setColorShadow(Color colorShadow) {
        this.colorShadow = colorShadow;
    }

    public int getColorShadowRadius() {
        return colorShadowRadius;
    }

    public void setColorShadowRadius(int colorShadowRadius) {
        this.colorShadowRadius = colorShadowRadius;
    }

    public double getColorShadowSpread() {
        return colorShadowSpread;
    }

    public void setColorShadowSpread(double colorShadowSpread) {
        this.colorShadowSpread = colorShadowSpread;
    }

    public double getColorShadowOffsetX() {
        return colorShadowOffsetX;
    }

    public void setColorShadowOffsetX(double colorShadowOffsetX) {
        this.colorShadowOffsetX = colorShadowOffsetX;
    }

    public double getColorShadowOffsetY() {
        return colorShadowOffsetY;
    }

    public void setColorShadowOffsetY(double colorShadowOffsetY) {
        this.colorShadowOffsetY = colorShadowOffsetY;
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

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public int getBackgroundImagePosX() {
        return backgroundImagePosX;
    }

    public void setBackgroundImagePosX(int backgroundImagePosX) {
        this.backgroundImagePosX = backgroundImagePosX;
    }

    public int getBackgroundImagePosY() {
        return backgroundImagePosY;
    }

    public void setBackgroundImagePosY(int backgroundImagePosY) {
        this.backgroundImagePosY = backgroundImagePosY;
    }

    public List<ImageFormat> getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(List<ImageFormat> imageFormat) {
        this.imageFormat = imageFormat;
    }
}
