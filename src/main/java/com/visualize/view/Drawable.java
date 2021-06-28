package com.visualize.view;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;
import org.bytedeco.javacv.FrameRecorder;

public interface Drawable {

    Pane preview(VisualizeFormat visualizeFormat);
    VisualizeParameter.PaneTimeline animate(VisualizeFormat visualizeFormat, double[][][] magnitude, double spf);
    void saveVideo(VisualizeFormat visualizeFormat, double[][][] magnitude, double spf, double fps, String exportPath) throws FrameRecorder.Exception;

}
