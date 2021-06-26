package com.visualize.gui;

import com.visualize.view.*;

import javafx.scene.control.TextArea;

public class EventLog extends TextArea {

    public static EventLog eventLog;

    private final String EMOJI_FINISH = "\uD83C\uDFC1";
    private final String EMOJI_ENCODE = "\uD83D\uDCBB";
    private final String EMOJI_WARNING = "⚠";
    private final String EMOJI_ARROW = " ➤ ";

    // Constructor
    public EventLog(String log) {
        super(log);
    }

    // Method
    public void finish(String log) {
        this.appendText(EMOJI_FINISH + "\t" + log + "\n");
    }

    public void encode(String log) {
        this.appendText(EMOJI_ENCODE + "\t" + log + "\n");
    }

    public void warning(String log) {
        this.appendText(EMOJI_WARNING + "\t" + log + "\n");
    }

    public void songChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tSong: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void volumeChange(double oldValue, double newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tVolume: \t%.2f %s %.2f\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void barNumChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tNumber Bar: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void barSizeChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tSize: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void barGapChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tGap: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void radiusChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tRadius: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void posXChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tPosition X: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void posYChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tPosition Y: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void rotationChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tRotation: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void colorChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tColor: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void colorBlurChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tColor Blur: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void sensitivityChange(double oldValue, double newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tSensitivity: \t%.2f %s %.2f\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void viewChange(VisualizeMode.View oldValue, VisualizeMode.View newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tEqulizer Type: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void sideChange(VisualizeMode.Side oldValue, VisualizeMode.Side newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tDirection: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void stereoChange(VisualizeMode.Stereo oldValue, VisualizeMode.Stereo newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tStereo: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void minFreqChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tMin Frequency: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void maxFreqChange(int oldValue, int newValue) {
        if (oldValue != newValue)
            this.appendText(String.format("\tMax Frequency: \t%d %s %d\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void backgroundImageChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tBackground Image: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void backgroundColorChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tBackground Color: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void backgroundRepeatChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tBackground Repeat: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

    public void backgroundPositionChange(String oldValue, String newValue) {
        if (!oldValue.equals(newValue))
            this.appendText(String.format("\tBackground Position: \t%s %s %s\n", oldValue, EMOJI_ARROW, newValue));
    }

}
