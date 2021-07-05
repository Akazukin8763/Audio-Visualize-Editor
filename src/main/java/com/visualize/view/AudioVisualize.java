package com.visualize.view;

//import com.visualize.Jar;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

public abstract class AudioVisualize implements Drawable{

    protected Pane pane;
    protected Timeline timeline;

    protected final int width;
    protected final int height;

    protected VisualizeMode.Side side;
    protected VisualizeMode.Direct direct;
    protected VisualizeMode.Stereo stereo;

    protected static double sensitivity;
    protected static double offset;

    // Lambda Function Array
    protected static final DirectMode directNormal = (c, m) -> c;
    protected static final DirectMode directInverse = (c, m) -> m - c - 1;
    protected static final DirectMode[] directModes = new DirectMode[] {directNormal, directInverse};

    protected static final MagnitudeMode magnitudeSingle = (s1, s2) -> s1; // s1 = s2
    protected static final MagnitudeMode magnitudeLeft = (l, r) -> l;
    protected static final MagnitudeMode magnitudeRight = (l, r) -> r;
    protected static final MagnitudeMode magnitudeBoth = (l, r) -> .5 * (l + r);
    protected static final MagnitudeMode[] magnitudeModes = new MagnitudeMode[] {magnitudeSingle, magnitudeLeft, magnitudeRight, magnitudeBoth};

    protected interface DirectMode {
        int direct(int cur, int max);
    }

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
    protected double getInitHeight(int i) {
        return 1;
        //return Math.abs(Math.sin((2 * Math.PI / 24) * (i % 24))) * 150 + 1;
    }

    protected void setSensitivity(double sensitivity) { // 調整靈敏度
        this.sensitivity = sensitivity;
    }

    protected void setOffset(int barNum) {
        this.offset = barNum / 64.0;
    }

    public void setSide(VisualizeMode.Side side) {
        this.side = side;
    }

    public void setDirect(VisualizeMode.Direct direct) {
        this.direct = direct;
    }

    public void setStereo(VisualizeMode.Stereo stereo) {
        this.stereo = stereo;
    }

    public void setBackgroundStyle(String backgroundStyle) {
        pane.setStyle(backgroundStyle);
    }

}
