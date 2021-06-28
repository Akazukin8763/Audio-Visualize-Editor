package com.visualize.execute;

import com.visualize.file.*;
import com.visualize.view.*;
import com.visualize.convert.*;

import javafx.application.Platform;

import java.io.File;

public class BackgroundSaveVideo implements Runnable {

    private final AudioVisualize audioVisualize;

    private final VisualizeFormat visualizeFormat;
    private final VisualizeMode.Side side;
    private final VisualizeMode.Stereo stereo;

    private final double[][][] magnitude;
    private final double spf;
    private final double fps;

    private final String audioPath;
    private final String exportPath;

    public BackgroundSaveVideo(AudioVisualize audioVisualize,
                               VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo,
                               double[][][] magnitude, double spf, double fps,
                               String audioPath, String exportPath) {
        this.audioVisualize = audioVisualize;

        this.visualizeFormat = visualizeFormat;
        this.side = side;
        this.stereo = stereo;

        this.magnitude = magnitude;
        this.spf = spf;
        this.fps = fps;

        this.audioPath = audioPath;
        this.exportPath = exportPath;
    }

    @Override
    public void run() {  // useless, but not crash
        Platform.runLater(() -> {
            try {
                audioVisualize.saveVideo(visualizeFormat, side, stereo, magnitude, spf, fps, DefaultPath.TEMP_VIDEO_PATH);
                ExportMp4.mergeAudio(DefaultPath.TEMP_VIDEO_PATH, audioPath, exportPath);
            } catch (Exception e) {
                System.out.println("error");
                e.printStackTrace();
            }finally {
                boolean result = new File(DefaultPath.TEMP_VIDEO_PATH).delete();
                System.out.println(result);
            }
        });
    }

}
