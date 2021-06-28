package com.visualize.view;

//import com.visualize.Jar;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

public abstract class AudioVisualize implements Drawable{

    protected Pane pane;
    protected Timeline timeline;

    protected final int width;
    protected final int height;

    protected static double sensitivity;
    protected static double offset;

    // Lambda Function Array
    protected static final AudioVisualize.MagnitudeMode magnitudeSingle = (s1, s2) -> s1; // s1 = s2
    protected static final AudioVisualize.MagnitudeMode magnitudeLeft = (l, r) -> l;
    protected static final AudioVisualize.MagnitudeMode magnitudeRight = (l, r) -> r;
    protected static final AudioVisualize.MagnitudeMode magnitudeBoth = (l, r) -> .5 * (l + r);
    protected static final AudioVisualize.MagnitudeMode[] magnitudeModes = new AudioVisualize.MagnitudeMode[] {magnitudeSingle, magnitudeLeft, magnitudeRight, magnitudeBoth};

    protected interface MagnitudeMode {
        double magnitudeSelect(double left, double right);
    }

    // Constructor
    public AudioVisualize(int width, int height) {
        this.pane = new Pane();
        this.timeline = new Timeline();

        this.width = width;
        this.height = height;

        setSensitivity(1);
        setOffset(64);
    }

    // Methods
    protected double random(int i) {
        return Math.abs(Math.sin((2 * Math.PI / 24) * (i % 24))) * 150 + 1;
    }

    protected void setSensitivity(double sensitivity) { // 調整靈敏度
        this.sensitivity = sensitivity;
    }

    protected void setOffset(int barNum) {
        this.offset = barNum / 64.0;
    }

    public void setBackgroundStyle(String backgroundStyle) {
        pane.setStyle(backgroundStyle);
    }

}
