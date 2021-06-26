package com.visualize.view;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

public interface Drawable {

    Pane preview(VisualizeFormat visualizeFormat, VisualizeMode.Side side);
    VisualizeParameter.PaneTimeline animate(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf);
    int saveImage(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf) throws java.io.IOException;

}
