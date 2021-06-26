package com.visualize.view;

import javafx.animation.Timeline;

public interface Drawable {

    void preview(VisualizeFormat visualizeFormat, VisualizeMode.Side side);
    Timeline animate(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf);
    int saveImage(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf) throws java.io.IOException;

}
