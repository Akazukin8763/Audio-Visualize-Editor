package com.visualize.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

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

public class AnalogyVisualize extends AudioVisualize{

    // Lambda Function Array
    private static final yMode yOut = (y, m) -> y - sensitivity * m * offset;
    private static final yMode yIn = (y, m) -> y + sensitivity * m * offset;
    private static final yMode yBoth = (y, m) -> y - sensitivity * m * offset; // 用 OUT 配合鏡射
    private static final yMode[] yModes = new yMode[] {yOut, yIn, yBoth};

    private interface yMode {
        double y(double y, double magnitude);
    }

    // Constructor
    public AnalogyVisualize(int width, int height) {
        super(width, height);
    }

    // Methods
    @Override
    public Pane preview(VisualizeFormat visualizeFormat, VisualizeMode.Side side) {
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

        // Reflection
        if (side == VisualizeMode.Side.BOTH) {
            Reflection reflection = new Reflection();
            reflection.setTopOffset(-barSize * 2);
            reflection.setTopOpacity(1);
            reflection.setBottomOpacity(1);
            reflection.setFraction(1);
            dropShadow.setInput(reflection);
        }

        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(posX - (barSize + barGap), posY); // 起端
        for (int i = 0; i < barNum; i++) {
            double rand = random(i);

            double y = yModes[side.value()].y(posY, rand);

            polyline.getPoints().addAll(posX + i * (barSize + barGap), y);
        }
        polyline.getPoints().addAll(posX + barNum * (barSize + barGap), posY); // 末端

        /*if (side == VisualizeMode.Side.BOTH) { // BOTH
            for (int i = 0; i < barNum; i++) { // 從末端連回去
                double rand = random();

                polyline.getPoints().addAll(posX + (barNum - i - 1) * (barSize + barGap), posY + sensitivity * rand);
            }
            polyline.getPoints().addAll(posX - (barSize + barGap), posY); // 連回起端
        }*/

        polyline.setStrokeWidth(barSize);
        polyline.setStrokeLineJoin(StrokeLineJoin.ROUND);
        polyline.getTransforms().add(rotate);
        polyline.setStroke(barColor);
        polyline.setEffect(dropShadow);

        pane.getChildren().add(polyline);

        return pane;
    }

    @Override
    public VisualizeParameter.PaneTimeline animate(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf) {
        pane = preview(visualizeFormat, side); // 重設所有矩形
        timeline = new Timeline(); // 重設所有動畫

        int lengths = magnitude[0][0].length; // 時間區塊
        int channels = (stereo == VisualizeMode.Stereo.SINGLE) ? 0 : 1; // 單雙聲道，用來確保參數個數一樣

        setSensitivity(visualizeFormat.getSensitivity());
        setOffset(visualizeFormat.getBarNum());

        for (Node node: pane.getChildren()) { // 基本上只有一個 Polyline
            Polyline polyline; // 要動畫化的 Polyline

            if (node instanceof Polyline) // 確保物件為 Polyline
                polyline = (Polyline) node;
            else
                continue;

            for (int i = 2; i < polyline.getPoints().size() - 2; i += 2) { // 排除起端與末端
                int bar = i / 2 - 1; // 第幾個 Bar (邊號)
                final int index = i;

                DoubleProperty yProperty = new SimpleDoubleProperty(polyline.getPoints().get(index + 1)); // polyline 每個點的 y 軸 property
                yProperty.addListener((obs, oldValue, newValue) -> polyline.getPoints().set(index + 1, (double) newValue));

                KeyFrame[] keyFrames = new KeyFrame[lengths + 1]; // 關鍵影格

                for (int length = 0; length < lengths; length++) {
                    keyFrames[length] = new KeyFrame(
                            new Duration(spf * (length + 1) * 1000), // 第幾秒
                            new KeyValue(yProperty, yModes[side.value()].y(
                                    visualizeFormat.getPosY(),
                                    magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])))
                    );
                }
                keyFrames[lengths] = new KeyFrame( // 將視覺化矩形高度重製
                        new Duration(spf * (lengths + 1) * 1000),
                        new KeyValue(yProperty, yModes[side.value()].y(visualizeFormat.getPosY(), 1))
                );

                timeline.getKeyFrames().addAll(keyFrames);
            }
            /* BOTH
            int barNum = magnitude[0].length;

            for (int bar = 0; bar < barNum; bar++) {
                // OUT
                final int indexOUT = 2 + bar * 2;

                DoubleProperty yPropertyOUT = new SimpleDoubleProperty(polyline.getPoints().get(indexOUT + 1)); // polyline 每個點的 y 軸 property
                yPropertyOUT.addListener((obs, oldValue, newValue) -> polyline.getPoints().set(indexOUT + 1, (double) newValue));

                KeyFrame[] keyFramesOUT = new KeyFrame[lengths + 1]; // 關鍵影格

                for (int length = 0; length < lengths; length++) {
                    keyFramesOUT[length] = new KeyFrame(
                            new Duration(spf * (length + 1) * 1000), // 第幾秒
                            new KeyValue(yPropertyOUT, yModes[0].y( // OUT
                                    visualizeFormat.getPosY(),
                                    magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])))
                    );
                }
                keyFramesOUT[lengths] = new KeyFrame( // 將視覺化矩形高度重製
                        new Duration(spf * (lengths + 1) * 1000),
                        new KeyValue(yPropertyOUT, yModes[0].y(visualizeFormat.getPosY(), 1)) //OUT
                );

                timeline.getKeyFrames().addAll(keyFramesOUT);

                // IN
                final int indexIN = polyline.getPoints().size() - 2 - bar * 2 - 2;

                DoubleProperty yPropertyIN = new SimpleDoubleProperty(polyline.getPoints().get(indexIN + 1)); // polyline 每個點的 y 軸 property
                yPropertyIN.addListener((obs, oldValue, newValue) -> polyline.getPoints().set(indexIN + 1, (double) newValue));

                KeyFrame[] keyFramesIN = new KeyFrame[lengths + 1]; // 關鍵影格

                for (int length = 0; length < lengths; length++) {
                    keyFramesIN[length] = new KeyFrame(
                            new Duration(spf * (length + 1) * 1000), // 第幾秒
                            new KeyValue(yPropertyIN, yModes[1].y( // IN
                                    visualizeFormat.getPosY(),
                                    magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])))
                    );
                }
                keyFramesIN[lengths] = new KeyFrame( // 將視覺化矩形高度重製
                        new Duration(spf * (lengths + 1) * 1000),
                        new KeyValue(yPropertyIN, yModes[1].y(visualizeFormat.getPosY(), 1)) //IN
                );

                timeline.getKeyFrames().addAll(keyFramesIN);
            }
            */
        }

        return new VisualizeParameter.PaneTimeline(pane, timeline);
    }

    @Override
    public void saveVideo(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf, double fps, String exportPath) throws FrameRecorder.Exception{
        pane = preview(visualizeFormat, side); // 重設所有矩形

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(exportPath, width, height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setVideoQuality(0);
        recorder.setFrameRate(fps);
        recorder.setFormat("mp4");

        WritableImage writableImage = new WritableImage(width, height); // 設定圖片大小
        SnapshotParameters snapshotParameters = new SnapshotParameters(); // 設定截圖範圍
        snapshotParameters.setViewport(new Rectangle2D(0, 0, width, height));

        int lengths = magnitude[0][0].length; // 時間區塊
        int channels = (stereo == VisualizeMode.Stereo.SINGLE) ? 0 : 1; // 單雙聲道，用來確保參數個數一樣
        Polyline polyline = new Polyline();

        setSensitivity(visualizeFormat.getSensitivity());
        setOffset(visualizeFormat.getBarNum());

        for (Node node: pane.getChildren()) { // 基本上只有一個 Polyline
            if (node instanceof Polyline) // 確保物件為 Polyline
                polyline = (Polyline) node;
        }

        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();

            for (int length = 0; length < lengths; length++) {
                for (int i = 2; i < polyline.getPoints().size() - 2; i += 2) { // 排除起端與末端
                    int bar = i / 2 - 1; // 第幾個 Bar (邊號)
                    double m = magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length]);
                    double y = visualizeFormat.getPosY();

                    polyline.getPoints().set(i + 1, yModes[side.value()].y(y, m));
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

        /*for (int i = 2; i < polyline.getPoints().size() - 2; i += 2) { // 排除起端與末端
            polyline.getPoints().set(i + 1, yModes[side.value()].y(visualizeFormat.getPosY(), 1));
        }
        snapshot(SwingFXUtils.fromFXImage(pane.snapshot(snapshotParameters, writableImage), new BufferedImage((int)pane.getWidth(), (int)pane.getHeight(), BufferedImage.TYPE_INT_RGB)), lengths + 1);*/
    }

}
