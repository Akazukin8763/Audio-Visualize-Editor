package com.visualize.view;

import com.visualize.Jar;
import com.visualize.file.*;
import com.visualize.math.*;
import com.visualize.convert.*;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

import javafx.animation.KeyFrame;

import javafx.util.Duration;
import javafx.animation.Animation;

import java.util.concurrent.CountDownLatch;
import javafx.concurrent.Task;
import javafx.concurrent.Service;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class VisualizePane extends Pane {

    public static final int BUFFER = 2048;

    private Timeline timeline;
    public BooleanProperty timeChangeProperty;
    public double currentTime;

    private AudioFile audioFile;
    private VisualizeFormat visualizeFormat;

    private VisualizeMode.Side side;
    private VisualizeMode.View view;
    private VisualizeMode.Stereo stereo;

    private String backgroundImage;
    private String backgroundColor;
    private String backgroundRepeat;
    private String backgroundPosition;

    //int availableLengths: 實際可用長度 (捨棄超過 BUFFER 倍的資料)
    private double fps; // 每秒偵數 (frame per second)
    private double spf; // 每偵秒數 (second per frame)
    private double[][] frequency; // 頻率振福 [channels][availableLengths / 2]
    private double[][][] magnitude; // 區域總振幅 [channels][barNum][availableLengths / BUFFER]

    private double minFreq;
    private double maxFreq;

    protected boolean autoPlay; // 繪製完音波是否立即撥放
    private boolean audioIsChanged; // 外部是否改變 audioFile，有的話則要重新計算 frequency
    private boolean barNumIsChanged; // 外部是否改變 barNum 數量，有的話則要重新計算 magnitude
    private boolean freqIsChanged; // 外部是否改變 freq 範圍，有的話則要重新計算 magnitude

    // Constructor
    public VisualizePane(AudioFile audioFile, VisualizeFormat visualizeFormat) {
        this(audioFile, visualizeFormat, VisualizeMode.Side.OUT, VisualizeMode.View.LINE, VisualizeMode.Stereo.BOTH);
    }

    public VisualizePane(AudioFile audioFile, VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.View view, VisualizeMode.Stereo stereo) {
        timeChangeProperty = new SimpleBooleanProperty(false);

        this.audioFile = audioFile;
        this.visualizeFormat = visualizeFormat;

        this.side = side;
        this.view = view;
        setStereo(stereo); // 防止輸入可用聲道數

        this.minFreq = this.audioFile.getFrameRate() / BUFFER;
        this.maxFreq = this.audioFile.getFrameRate() / 2;

        this.autoPlay = true; // 預設為立即撥放
        this.audioIsChanged = true; // 保證第一次計算 frequency
        this.barNumIsChanged = true; // 保證第一次計算 magnitude
        this.freqIsChanged = true; // 保證第一次計算 magnitude

        // Discord Color: #36393F
        this.setBackgroundStyle("null", "#FFFFFF", "no-repeat", "center");
        this.setPrefSize(1920, 1080);
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
            int[] frequencyIndex = frequencyIndex(this.audioFile.getFrameRate() / 2, this.visualizeFormat.getBarNum(), minFreq, maxFreq);
            magnitude = magnitudeCollect(frequency, frequencyIndex);

            audioIsChanged = false;
            barNumIsChanged = false;
        }
    }

    public void preview() {
        switch (view) {
            case LINE:
                new LineVisualize(this).preview(visualizeFormat, side);
                break;
            case CIRCLE:
                new CircleVisualize(this).preview(visualizeFormat, side);
                break;
            case ANALOGY:
                new AnalogyVisualize(this).preview(visualizeFormat, side);
                break;
            default:
                throw new IllegalArgumentException("Doesn't support this view mode.");
        }
    }

    public void animate() {
        updateData();

        // Animation
        switch (view) {
            case LINE:
                timeline = new LineVisualize(this).animate(visualizeFormat, side, stereo, magnitude, spf);
                break;
            case CIRCLE:
                timeline = new CircleVisualize(this).animate(visualizeFormat, side, stereo, magnitude, spf);
                break;
            case ANALOGY:
                timeline = new AnalogyVisualize(this).animate(visualizeFormat, side, stereo, magnitude, spf);
                break;
            default:
                throw new IllegalArgumentException("Doesn't support this view mode.");
        }

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

        /*VisualizePane visualizePane = new VisualizePane(audioFile, visualizeFormat, side, view, stereo);
        visualizePane.setBackgroundStyle(backgroundImage, backgroundColor, backgroundRepeat, backgroundPosition);
        visualizePane.updateData();
        visualizePane.preview();
        javafx.scene.Scene scene = new javafx.scene.Scene(visualizePane);

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try{
                                java.io.File imgPath = new java.io.File("image");
                                String jpgDirPath = imgPath.getAbsolutePath();
                                imgPath.delete();
                                imgPath.mkdirs();

                                // Images nums
                                int nums = 0;

                                // Images
                                gui.EventLog.eventLog.encode("Video Fetching...");
                                switch (view) {
                                    case LINE:
                                        nums = new LineVisualize(visualizePane).saveImage(visualizeFormat, side, stereo, magnitude, spf);
                                        break;
                                    case CIRCLE:
                                        nums = new CircleVisualize(visualizePane).saveImage(visualizeFormat, side, stereo, magnitude, spf);;
                                        break;
                                    case ANALOGY:
                                        nums = new AnalogyVisualize(visualizePane).saveImage(visualizeFormat, side, stereo, magnitude, spf);;
                                        break;
                                    default:
                                        throw new IllegalArgumentException("Doesn't support this view mode.");
                                }

                                // Convert Image Array to Video
                                gui.EventLog.eventLog.encode("Video Encoding...");
                                ExportMp4.exportMp4(jpgDirPath, audioFile.getAbsolutePath(), (float)(fps), (int)visualizePane.getPrefWidth(), (int)visualizePane.getPrefHeight(), filepath);

                                // Delete Image
                                for (int i = 1; i <= nums; i++) {
                                    java.io.File imgFile = new java.io.File(String.format("image/%06d.jpg", i));
                                    imgFile.delete();
                                }

                                gui.EventLog.eventLog.finish("The file has been exported correctly.");
                                MediaPlayer mediaPlayer = new MediaPlayer(new Media(new java.io.File("music/__finish__.mp3").toURI().toString()));
                                mediaPlayer.setVolume(.5);
                                mediaPlayer.play();
                            } catch (Exception e) {
                                gui.EventLog.eventLog.warning("The file can not be exported correctly.");
                            } finally{
                                latch.countDown();
                            }
                        });
                        latch.await();

                        return null;
                    }
                };
            }
        };
        service.start();*/

        java.io.File imgPath = new java.io.File(Jar.getJarPath() + "/image");
        //java.io.File imgPath = new java.io.File("src/main/resources/image");
        imgPath.delete();
        imgPath.mkdirs();

        // Images nums
        int nums = 0;

        // Images
        try {
            switch (view) {
                case LINE:
                    nums = new LineVisualize(this).saveImage(visualizeFormat, side, stereo, magnitude, spf);
                    break;
                case CIRCLE:
                    nums = new CircleVisualize(this).saveImage(visualizeFormat, side, stereo, magnitude, spf);
                    break;
                case ANALOGY:
                    nums = new AnalogyVisualize(this).saveImage(visualizeFormat, side, stereo, magnitude, spf);
                    break;
                default:
                    throw new IllegalArgumentException("Doesn't support this view mode.");
            }
        } catch (java.io.IOException e) {
            throw new java.io.IOException();
        }

        // Convert Image Array to Video
        String jpgDirPath = new java.io.File(Jar.getJarPath() + "/image").getAbsolutePath();
        //String jpgDirPath = new java.io.File("src/main/resources/image").getAbsolutePath();
        String audioPath = audioFile.getAbsolutePath();

        BackgroundExportMp4 backgroundExportMp4 = new BackgroundExportMp4(jpgDirPath, audioPath, (float)(fps), (int)this.getPrefWidth(), (int)this.getPrefHeight(), filepath, nums);
        backgroundExportMp4.execute();

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

    public VisualizeMode.Side getSide() {
        return side;
    }

    public void setSide(VisualizeMode.Side side) {
        this.side = side;
    }

    public VisualizeMode.View getView() {
        return view;
    }

    public void setView(VisualizeMode.View view) {
        this.view = view;
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
        this.audioFile = audioFile;
        this.audioIsChanged = true;
    }

    public VisualizeFormat getVisualizeFormat() {
        return visualizeFormat;
    }

    public void setVisualizeFormat(VisualizeFormat visualizeFormat) {
        if (this.visualizeFormat.getBarNum() != visualizeFormat.getBarNum())
            this.barNumIsChanged = true;

        this.visualizeFormat = visualizeFormat;
    }

    public double getMinFreq() {
        return minFreq;
    }

    public double getMaxFreq() {
        return maxFreq;
    }

    public void setMinMaxFreq(double minFreq, double maxFreq) {
        if (this.minFreq != minFreq || this.maxFreq != maxFreq)
            this.freqIsChanged = true;

        this.minFreq = minFreq;
        this.maxFreq = maxFreq;
    }

    public String getBackgroundImage() {
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

    public void setBackgroundStyle(String backgroundImage, String backgroundColor, String backgroundRepeat, String backgroundPosition) {
        this.backgroundImage = backgroundImage;
        this.backgroundColor = backgroundColor;
        this.backgroundRepeat = backgroundRepeat;
        this.backgroundPosition = backgroundPosition;

        try {
            String image = (backgroundImage.equals("null") ? "" : "-fx-background-image: url(\"" + new java.io.File(backgroundImage).toURI().toString() + "\");");
            String color = "-fx-background-color: " + backgroundColor + ";";
            String repeat = "-fx-background-repeat: " + backgroundRepeat + ";";
            String position = "-fx-background-position: " + backgroundPosition + ";";

            this.setStyle(image + color + repeat + position);
        } catch (NullPointerException ignored) {
            // 不做任何事
        }
    }
}
