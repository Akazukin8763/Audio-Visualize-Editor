package com.visualize.view;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import org.bytedeco.javacv.FrameRecorder;

public interface Drawable {

    Pane preview(VisualizeFormat visualizeFormat, VisualizeMode.Side side);
    VisualizeParameter.PaneTimeline animate(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf);
    void saveVideo(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf, double fps, String exportPath) throws FrameRecorder.Exception;

}
