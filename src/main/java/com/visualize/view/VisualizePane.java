package com.visualize.view;

import com.visualize.gui.*;
import com.visualize.file.*;
import com.visualize.math.*;
import com.visualize.execute.*;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

import javafx.animation.KeyFrame;

import javafx.util.Duration;
import javafx.animation.Animation;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class VisualizePane extends Pane {

    public static final int BUFFER = 2048;

    private final int width = 1920;
    private final int height = 1080;
    private AudioVisualize audioVisualize;

    private Timeline timeline;
    public BooleanProperty timeChangeProperty;
    public double currentTime;

    private AudioFile audioFile;
    private VisualizeFormat visualizeFormat;
    private BackgroundFormat backgroundFormat;

    private VisualizeMode.View view;
    private VisualizeMode.Side side;
    private VisualizeMode.Direct direct;
    private VisualizeMode.Stereo stereo;

    //int availableLengths: 實際可用長度 (捨棄超過 BUFFER 倍的資料)
    private double fps; // 每秒偵數 (frame per second)
    private double spf; // 每偵秒數 (second per frame)
    private double[][] frequency; // 頻率振福 [channels][availableLengths / 2]
    private double[][][] magnitude; // 區域總振幅 [channels][barNum][availableLengths / BUFFER]

    protected boolean autoPlay; // 繪製完音波是否立即撥放
    private boolean audioIsChanged; // 外部是否改變 audioFile，有的話則要重新計算 frequency
    private boolean barNumIsChanged; // 外部是否改變 barNum 數量，有的話則要重新計算 magnitude
    private boolean freqIsChanged; // 外部是否改變 freq 範圍，有的話則要重新計算 magnitude

    // Constructor
    public VisualizePane(AudioFile audioFile, VisualizeFormat visualizeFormat, BackgroundFormat backgroundFormat) {
        timeChangeProperty = new SimpleBooleanProperty(false);

        this.audioFile = audioFile;
        this.visualizeFormat = visualizeFormat;
        this.backgroundFormat = backgroundFormat;
        this.visualizeFormat.setMinFreq(this.audioFile.getFrameRate() / BUFFER);
        this.visualizeFormat.setMaxFreq(this.audioFile.getFrameRate() / 2);

        this.audioVisualize = new LineVisualize(width, height);
        this.view = VisualizeMode.View.LINE;
        this.side = VisualizeMode.Side.OUT;
        this.direct = VisualizeMode.Direct.NORMAL;
        this.stereo = VisualizeMode.Stereo.BOTH; // 防止輸入可用聲道數

        this.autoPlay = true; // 預設為立即撥放
        this.audioIsChanged = true; // 保證第一次計算 frequency
        this.barNumIsChanged = true; // 保證第一次計算 magnitude
        this.freqIsChanged = true; // 保證第一次計算 magnitude

        // Discord Color: #36393F
        this.setStyle("-fx-background-color: #" + backgroundFormat.getBackgroundColor().toString().subSequence(2, 8) + ";");
        this.setPrefSize(width, height);

        // Event
        this.visualizeFormat.barNumProperty.addListener((obs, oldValue, newValue) -> this.barNumIsChanged = true);
        this.visualizeFormat.minFreqProperty.addListener((obs, oldValue, newValue) -> this.freqIsChanged = true);
        this.visualizeFormat.maxFreqProperty.addListener((obs, oldValue, newValue) -> this.freqIsChanged = true);
        this.backgroundFormat.backgroundColorProperty.addListener((obs, oldValue, newValue) -> this.setStyle("-fx-background-color: #" + newValue.subSequence(2, 8) + ";"));
    }

    // Methods
    private static double[][] fftAudioData(AudioFile audioFile) {
        int availableLengths = audioFile.getFrameLength() / BUFFER * BUFFER;

        int channels = audioFile.getChannels();
        double[][] audioData = audioFile.getAudioData();

        double[][] frequency = new double[channels][availableLengths / 2]; // 實數 fft 後，後半段的資料是對稱的，所以捨棄
        Complex[] data = new Complex[BUFFER];

        for (int channel = 0; channel < channels; channel++) {
            for (int length = 0; length < availableLengths; length += BUFFER) {
                for (int i = 0; i < BUFFER; i++) { // 將實數轉為複數型態
                    data[i] = new Complex(audioData[channel][length + i]);
                }
                data = FFT.fft(data); // 傅立葉轉換

                for (int i = 0; i < BUFFER / 2; i++) { // 取樣前半段 (長度為 BUFFER / 2)
                    frequency[channel][length / 2 + i] = data[i].abs();
                }
            }
        }

        return frequency;
    }

    private static double[][][] magnitudeCollect(double[][] freq, int[] idx) {
        int channels = freq.length;
        int barNum = idx.length - 1;
        int blocks = freq[0].length / (BUFFER / 2); // 採樣長度個數
        double[][][] magnitude = new double[channels][barNum][blocks];

        for (int channel = 0; channel < channels; channel++) {
            for (int block = 0; block < blocks; block++) {
                for (int i = 0; i < barNum; i++) {
                    magnitude[channel][i][block] = 0;

                    for (int j = idx[i]; j < idx[i + 1]; j++) { // 收集各頻率總振幅
                        magnitude[channel][i][block] += freq[channel][block * (BUFFER / 2) + j];
                    }
                }
            }
        }

        /*
        double[] equalize = new double[] {.1, .8, .1};
        double[][][] equal = new double[channels][barNum][blocks];
        for (int channel = 0; channel < channels; channel++) {
            for (int block = 0; block < blocks; block++) {
                equal[channel][0][block] = magnitude[channel][0][block] * equalize[1] +
                                            magnitude[channel][1][block] * (equalize[0] + equalize[2]);
                equal[channel][barNum - 1][block] = magnitude[channel][barNum - 1][block] * equalize[1] +
                                            magnitude[channel][barNum - 2][block] * (equalize[0] + equalize[2]);

                for (int i = 1; i < barNum - 1; i++) {
                    equal[channel][i][block] = magnitude[channel][i - 1][block] * equalize[0] +
                                                magnitude[channel][i][block] * equalize[1] +
                                                magnitude[channel][i + 1][block] * equalize[2];
                }
            }
        }
        return equal;
        */

        return magnitude;
    }

    private static int[] frequencyIndex(double freq, int barNum, double minFreq, double maxFreq) {
        int[] bar = new int[barNum + 1]; // 取樣位置
        double[] transFreq = new double[barNum + 1]; // 各 bar 接收的頻率範圍
        double freqWidth = freq / (BUFFER / 2.0); // 頻率採樣寬度
        double blocks = 0; // 分割頻率寬度

        maxFreq = Transforms.melTrans(maxFreq);
        minFreq = Transforms.melTrans(minFreq);
        blocks = (maxFreq - minFreq) / barNum;

        for (int i = 0; i < barNum + 1; i++) { // 將頻率依照梅爾刻度分割
            transFreq[i] = Transforms.iMelTrans(minFreq + i * blocks);
        }

        /*blocks = (maxFreq - minFreq) / barNum;
        for (int i = 0; i < barNum + 1; i++) { // 將頻率平均分割
            transFreq[i] = minFreq + i * blocks;
        }*/

        int index = 0;
        for (int i = 1; i < BUFFER / 2 + 1; i++) {
            if (i * freqWidth >= transFreq[index])
                bar[index++] = i - 1;

            if (index == barNum + 1)
                break;
        }

        //System.out.println(java.util.Arrays.toString(bar));
        return bar;
    }

    private void updateData() {
        if (audioIsChanged) { // 若改變過 audioFile 則重新計算傅立葉轉換
            this.frequency = fftAudioData(this.audioFile);
            this.fps = (audioFile.getFrameRate() / BUFFER);
            this.spf = 1 / (audioFile.getFrameRate() / BUFFER);
        }

        if (audioIsChanged | barNumIsChanged | freqIsChanged) { // 若改變過 barNum, freq 則振幅重新計算
            int[] frequencyIndex = frequencyIndex(this.audioFile.getFrameRate() / 2, this.visualizeFormat.getBarNum(), this.visualizeFormat.getMinFreq(), this.visualizeFormat.getMaxFreq());
            magnitude = magnitudeCollect(frequency, frequencyIndex);

            audioIsChanged = false;
            barNumIsChanged = false;
            freqIsChanged = false;
        }
    }

    public void preview() {
        switch (view) {
            case LINE:
                audioVisualize = new LineVisualize(width, height);
                break;
            case CIRCLE:
                audioVisualize = new CircleVisualize(width, height);
                break;
            case ANALOGY:
                audioVisualize = new AnalogyVisualize(width, height);
                break;
            default:
                throw new IllegalArgumentException("Doesn't support this view mode.");
        }

        // preview()
        audioVisualize.setSide(side);
        audioVisualize.setDirect(direct);
        audioVisualize.setStereo(stereo);
        Pane pane = audioVisualize.preview(visualizeFormat);

        // Set
        this.getChildren().clear();
        this.getChildren().addAll(pane.getChildren());
    }

    public void animate() {
        updateData();

        // animate()
        VisualizeParameter.PaneTimeline paneTimeline = audioVisualize.animate(visualizeFormat, magnitude, spf);

        // Set Pane, Animation
        this.getChildren().clear();
        this.getChildren().addAll(paneTimeline.getPane().getChildren());
        this.timeline = new Timeline();
        this.timeline.getKeyFrames().addAll(paneTimeline.getTimeline().getKeyFrames());

        // Audio
        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, audioFile.getStartEvent()));
        timeline.getKeyFrames().add(new KeyFrame(new Duration(audioFile.getDuration() * 1000), audioFile.getStopEvent()));

        // Set Property
        timeline.currentTimeProperty().addListener((obs, oldValue, newValue) -> {
            currentTime = newValue.toSeconds();
            timeChangeProperty.setValue(true);
        });

        // Play Window
        if (autoPlay) {
            timeline.play();
        }
    }

    public void saveVideo(String filepath) throws java.io.IOException {
        updateData();

        //audioVisualize.setBackgroundStyle(getBackgroundStyle());
        BackgroundSaveVideo task = new BackgroundSaveVideo(audioVisualize, visualizeFormat, magnitude, spf, fps, audioFile.getAbsolutePath(), filepath);
        //Progress.progress.progressProperty().bind(task.progressProperty());
        new Thread(task).start();

    }

    public void play() {
        if (timeline != null) {
            timeline.play();
        }
        else {
            throw new NullPointerException("There is no animation in this panel.");
        }
    }

    public void playFrom(double sec) {
        if (timeline != null) {
            timeline.playFrom(new Duration(sec * 1000));
            audioFile.playFrom(sec);
        }
        else {
            throw new NullPointerException("There is no animation in this panel.");
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
            audioFile.stop();
        }
        else {
            throw new NullPointerException("There is no animation in this panel.");
        }
    }

    public void clear() {
        this.getChildren().clear();
        timeline = null;
    }

    public void clearAnimation() {
        timeline = null;
    }

    public boolean isRunning() {
        if (timeline != null)
            return timeline.getStatus() == Animation.Status.RUNNING;
        else
            return false;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
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
        if (audioFile.getChannels() == 1) {
            if (stereo != VisualizeMode.Stereo.SINGLE)
                throw new IllegalArgumentException("This Audio only supports Mono.");
        }
        else if (audioFile.getChannels() == 2) {
            if (stereo == VisualizeMode.Stereo.SINGLE)
                throw new IllegalArgumentException("This Audio only support Stereo.");
        }
        else {
            throw new IllegalArgumentException("Audio only supports Mono or Stereo.");
        }

        this.stereo = stereo;
    }

    public AudioFile getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(AudioFile audioFile) {
        this.audioIsChanged = true;
        this.audioFile = audioFile;
    }

    public VisualizeFormat getVisualizeFormat() {
        return visualizeFormat;
    }

    public void setVisualizeFormat(VisualizeFormat visualizeFormat) {
        if (this.visualizeFormat.getBarNum() != visualizeFormat.getBarNum())
            this.barNumIsChanged = true;

        if (this.visualizeFormat.getMinFreq() != visualizeFormat.getMinFreq() |
            this.visualizeFormat.getMaxFreq() != visualizeFormat.getMaxFreq())
            this.freqIsChanged = true;

        this.visualizeFormat = visualizeFormat;
    }

    public BackgroundFormat getBackgroundFormat() {
        return backgroundFormat;
    }

    public void setBackgroundFormat(BackgroundFormat backgroundFormat) {
        this.backgroundFormat = backgroundFormat;
    }

    /*public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getBackgroundRepeat() {
        return backgroundRepeat;
    }

    public String getBackgroundPosition() {
        return backgroundPosition;
    }

    public String getBackgroundStyle() {
        String backgroundStyle = "";

        try {
            String image = (this.backgroundImage.equals("null") ? "" : "-fx-background-image: url(\"" + new File(this.backgroundImage).toURI().toString() + "\");");
            String color = "-fx-background-color: " + this.backgroundColor + ";";
            String repeat = "-fx-background-repeat: " + this.backgroundRepeat + ";";
            String position = "-fx-background-position: " + this.backgroundPosition + ";";

            backgroundStyle = image + color + repeat + position;
        } catch (NullPointerException ignored) {
            // 不做任何事
        }

        return backgroundStyle;
    }

    public void setBackgroundStyle(String backgroundImage, String backgroundColor, String backgroundRepeat, String backgroundPosition) {
        this.backgroundImage = backgroundImage;
        this.backgroundColor = backgroundColor;
        this.backgroundRepeat = backgroundRepeat;
        this.backgroundPosition = backgroundPosition;

        try {
            String image = (backgroundImage.equals("null") ? "" : "-fx-background-image: url(\"" + new File(backgroundImage).toURI().toString() + "\");");
            String color = "-fx-background-color: " + backgroundColor + ";";
            String repeat = "-fx-background-repeat: " + backgroundRepeat + ";";
            String position = "-fx-background-position: " + backgroundPosition + ";";

            this.setStyle(image + color + repeat + position);
        } catch (NullPointerException ignored) {
            // 不做任何事
        }
    }*/
}
