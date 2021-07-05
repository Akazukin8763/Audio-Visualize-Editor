package com.visualize.view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.scene.transform.Rotate;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;

import javafx.util.Duration;

import javafx.geometry.Rectangle2D;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import java.awt.image.BufferedImage;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

public class LineVisualize extends AudioVisualize{

    // Lambda Function Array
    private static final HeightMode heightOut = (m) -> sensitivity * m * offset;
    private static final HeightMode heightIn = (m) -> sensitivity * m * offset;
    private static final HeightMode heightBoth = (m) -> sensitivity * m * 2 * offset;
    private static final HeightMode[] heightModes = new HeightMode[] {heightOut, heightIn, heightBoth};

    private static final yMode yOut = (y, m) -> y - sensitivity * m * offset;
    private static final yMode yIn = (y, m) -> y;
    private static final yMode yBoth = (y, m) -> y - sensitivity * m * offset;
    private static final yMode[] yModes = new yMode[] {yOut, yIn, yBoth};

    private interface HeightMode {
        double height(double magnitude);
    }

    private interface yMode {
        double y(double y, double magnitude);
    }

    // Constructor
    public LineVisualize(int width, int height) {
        super(width, height);
    }

    // Methods
    @Override
    public Pane preview(VisualizeFormat visualizeFormat) {
        pane.getChildren().clear(); // 清空所有矩形物件

        int barNum = visualizeFormat.getBarNum();
        int barSize = visualizeFormat.getBarSize();
        int barGap = visualizeFormat.getBarGap();
        double posX = visualizeFormat.getPosX();
        double posY = visualizeFormat.getPosY();
        double rotation = visualizeFormat.getRotation();
        Color barColor = visualizeFormat.getBarColor();
        Color dropShadowColor = visualizeFormat.getDropShadowColor();

        // Rotation
        Rotate rotate = new Rotate(rotation, posX, posY);

        // DropShadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(dropShadowColor);
        dropShadow.setRadius(15);

        for (int i = 0; i < barNum; i++) {
            double initHeight = getInitHeight(directModes[direct.value()].direct(i, barNum));

            double y = yModes[side.value()].y(posY, initHeight);
            double height = heightModes[side.value()].height(initHeight);

            Rectangle rectangle = new Rectangle(posX + i * (barSize + barGap) , y, barSize, height);
            rectangle.getTransforms().add(rotate);
            rectangle.setFill(barColor);
            rectangle.setEffect(dropShadow);

            pane.getChildren().add(rectangle);
        }

        return pane;
    }

    @Override
    public VisualizeParameter.PaneTimeline animate(VisualizeFormat visualizeFormat, double[][][] magnitude, double spf) {
        pane = preview(visualizeFormat); // 重設所有矩形
        timeline = new Timeline(); // 重設所有動畫

        Rectangle rect; // 要動畫化的 Rectangle
        int bar = 0; // 第幾個 Bar (邊號)
        int barNum = magnitude[0].length;
        int lengths = magnitude[0][0].length; // 時間區塊
        int channels = (stereo == VisualizeMode.Stereo.SINGLE) ? 0 : 1; // 單雙聲道，用來確保參數個數一樣

        setSensitivity(visualizeFormat.getSensitivity());
        setOffset(visualizeFormat.getBarNum());

        for (Node node: pane.getChildren()) {
            if (node instanceof Rectangle) // 確保物件為 Rectangle
                rect = (Rectangle) node;
            else
                continue;

            KeyFrame[] keyFrames = new KeyFrame[lengths + 1]; // 關鍵影格

            int realBar = directModes[direct.value()].direct(bar, barNum);
            for (int length = 0; length < lengths; length++) {
                keyFrames[length] = new KeyFrame(
                    new Duration(spf * (length + 1) * 1000), // 第幾秒
                    new KeyValue(rect.heightProperty(), heightModes[side.value()].height(
                            magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][realBar][length], magnitude[channels][realBar][length]))),
                    new KeyValue(rect.yProperty(), yModes[side.value()].y(
                            visualizeFormat.getPosY(),
                            magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][realBar][length], magnitude[channels][realBar][length])))
                );
            }
            keyFrames[lengths] = new KeyFrame( // 將視覺化矩形高度重製
                    new Duration(spf * (lengths + 1) * 1000),
                    new KeyValue(rect.heightProperty(), 1),
                    new KeyValue(rect.yProperty(), yModes[side.value()].y(visualizeFormat.getPosY(), 1))
            );

            timeline.getKeyFrames().addAll(keyFrames);
            bar++;
        }

        return new VisualizeParameter.PaneTimeline(pane, timeline);
    }

    @Override
    public void saveVideo(VisualizeFormat visualizeFormat, double[][][] magnitude, double spf, double fps, String exportPath) throws FrameRecorder.Exception {
        pane = preview(visualizeFormat); // 重設所有矩形
        new Scene(pane);
        pane.setPrefSize(width, height);

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(exportPath, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setVideoQuality(0);
        recorder.setFrameRate(fps);
        recorder.setFormat("mp4");

        WritableImage writableImage = new WritableImage(width, height); // 設定圖片大小
        SnapshotParameters snapshotParameters = new SnapshotParameters(); // 設定截圖範圍
        snapshotParameters.setViewport(new Rectangle2D(0, 0, width, height));

        int barNum = magnitude[0].length;
        int lengths = magnitude[0][0].length; // 時間區塊
        int channels = (stereo == VisualizeMode.Stereo.SINGLE) ? 0 : 1; // 單雙聲道，用來確保參數個數一樣
        Rectangle[] rectangles = new Rectangle[barNum];

        setSensitivity(visualizeFormat.getSensitivity());
        setOffset(visualizeFormat.getBarNum());

        int bar = 0;
        for (Node node: pane.getChildren()) {
            if (node instanceof Rectangle) // 確保物件為 Rectangle
                rectangles[bar++] = (Rectangle) node;
        }

        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();

            for (int length = 0; length < lengths; length++) {
                for (bar = 0; bar < barNum; bar++) {
                    int realBar = directModes[direct.value()].direct(bar, barNum);

                    double m = magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][realBar][length], magnitude[channels][realBar][length]);
                    double y = visualizeFormat.getPosY();

                    rectangles[bar].setHeight(heightModes[side.value()].height(m));
                    rectangles[bar].setY(yModes[side.value()].y(y, m));
                }

                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(pane.snapshot(snapshotParameters, writableImage), new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
                recorder.record(converter.getFrame(bufferedImage), avutil.AV_PIX_FMT_ARGB);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recorder.stop();
            recorder.release();
        }

        /*for (bar = 0; bar < barNum; bar++) { // 將視覺化矩形高度重製
            rectangles[bar].setHeight(1);
            rectangles[bar].setY(yModes[side.value()].y(visualizeFormat.getPosY(), 1));
        }
        snapshot(SwingFXUtils.fromFXImage(pane.snapshot(snapshotParameters, writableImage), new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)), lengths + 1);*/
    }

}