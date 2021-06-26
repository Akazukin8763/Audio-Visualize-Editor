package com.visualize.view;

import javafx.scene.Node;
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

public class CircleVisualize extends AudioVisualize{

    // Lambda Function Array
    private static final CircleVisualize.HeightMode heightOut = (m) -> sensitivity * m * offset;
    private static final CircleVisualize.HeightMode heightIn = (m) -> sensitivity * m * offset;
    private static final CircleVisualize.HeightMode heightBoth = (m) -> sensitivity * m * 2 * offset;
    private static final CircleVisualize.HeightMode[] heightModes = new CircleVisualize.HeightMode[] {heightOut, heightIn, heightBoth};

    private static final CircleVisualize.yMode yOut = (y, m) -> y;
    private static final CircleVisualize.yMode yIn = (y, m) -> y - sensitivity * m * offset;
    private static final CircleVisualize.yMode yBoth = (y, m) -> y - sensitivity * m * offset;
    private static final CircleVisualize.yMode[] yModes = new CircleVisualize.yMode[] {yOut, yIn, yBoth};

    private interface HeightMode {
        double height(double magnitude);
    }

    private interface yMode {
        double y(double y, double magnitude);
    }

    // Constructor
    public CircleVisualize(Pane pane) {
        super(pane);
    }

    // Methods
    @Override
    public void preview(VisualizeFormat visualizeFormat, VisualizeMode.Side side) {
        pane.getChildren().clear(); // 清空所有矩形物件

        int barNum = visualizeFormat.getBarNum();
        int barSize = visualizeFormat.getBarSize();
        int radius = visualizeFormat.getRadius();
        double posX = visualizeFormat.getPosX();
        double posY = visualizeFormat.getPosY();
        double rotation = visualizeFormat.getRotation();
        Color barColor = visualizeFormat.getBarColor();
        Color dropShadowColor = visualizeFormat.getDropShadowColor();

        double radian = 2 * Math.PI / barNum;
        double angle = 360.0 / barNum;

        // Rotation
        double rotateRadian = rotation / 180.0 * Math.PI;
        double rotateAngle = rotation;

        // DropShadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(dropShadowColor);
        dropShadow.setRadius(15);

        for (int i = 0; i < barNum; i++) {
            double rand = random(i);

            double x = posX + radius * Math.cos(radian * i + rotateRadian); // 每個 bar 實際上的 x 座標
            double y = posY + radius * Math.sin(radian * i + rotateRadian); // 每個 bar 實際上的 y 座標
            double height = heightModes[side.value()].height(rand);

            Rectangle rectangle = new Rectangle(x - barSize / 2.0, yModes[side.value()].y(y, rand), barSize, height); // 將 x 錨點校正至 bar 底邊中心
            rectangle.getTransforms().add(new Rotate(angle * i - 90 + rotateAngle, x, y)); // 依照 bar 正中心點旋轉
            rectangle.setFill(barColor);
            rectangle.setEffect(dropShadow);

            pane.getChildren().add(rectangle);
        }
    }

    @Override
    public Timeline animate(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf) {
        Timeline timeline = new Timeline();

        double posY = visualizeFormat.getPosY();
        int radius = visualizeFormat.getRadius();
        double radian = 2 * Math.PI / visualizeFormat.getBarNum();
        double rotateRadian = visualizeFormat.getRotation() / 180.0 * Math.PI;

        Rectangle rect; // 要動畫化的 Rectangle
        int bar = 0; // 第幾個 Bar (邊號)
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

            double y = posY + radius * Math.sin(radian * bar + rotateRadian); // 每個 bar 實際上的 y 座標
            for (int length = 0; length < lengths; length++) {
                keyFrames[length] = new KeyFrame(
                        new Duration(spf * (length + 1) * 1000), // 第幾秒
                        new KeyValue(rect.heightProperty(), heightModes[side.value()].height(
                                magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length]))),
                        new KeyValue(rect.yProperty(), yModes[side.value()].y(y,
                                magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])))
                );
            }
            keyFrames[lengths] = new KeyFrame( // 將視覺化矩形高度重製
                    new Duration(spf * (lengths + 1) * 1000),
                    new KeyValue(rect.heightProperty(), 1),
                    new KeyValue(rect.yProperty(), yModes[side.value()].y(y, 1))
            );

            timeline.getKeyFrames().addAll(keyFrames);
            bar++;
        }

        return timeline;
    }

    @Override
    public int saveImage(VisualizeFormat visualizeFormat, VisualizeMode.Side side, VisualizeMode.Stereo stereo, double[][][] magnitude, double spf) throws java.io.IOException {
        WritableImage writableImage = new WritableImage((int)pane.getWidth(), (int)pane.getHeight()); // 設定圖片大小
        SnapshotParameters snapshotParameters = new SnapshotParameters(); // 設定截圖範圍
        snapshotParameters.setViewport(new Rectangle2D(0, 0, pane.getWidth(), pane.getHeight()));

        double posY = visualizeFormat.getPosY();
        int radius = visualizeFormat.getRadius();
        double radian = 2 * Math.PI / visualizeFormat.getBarNum();
        double rotateRadian = visualizeFormat.getRotation() / 180.0 * Math.PI;

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

        double[] y = new double[barNum];  // 每個 bar 實際上的 y 座標 (先存起來)
        for (bar = 0; bar < barNum; bar++)
            y[bar] = posY + radius * Math.sin(radian * bar + rotateRadian);

        for (int length = 0; length < lengths; length++) {
            for (bar = 0; bar < barNum; bar++) {
                rectangles[bar].setHeight(heightModes[side.value()].height(
                        magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])));
                rectangles[bar].setY(yModes[side.value()].y(y[bar],
                        magnitudeModes[stereo.value()].magnitudeSelect(magnitude[0][bar][length], magnitude[channels][bar][length])));
            }

            snapshot(SwingFXUtils.fromFXImage(pane.snapshot(snapshotParameters, writableImage), new BufferedImage((int)pane.getWidth(), (int)pane.getHeight(), BufferedImage.TYPE_INT_RGB)), length + 1);
        }

        for (bar = 0; bar < barNum; bar++) { // 將視覺化矩形高度重製
            rectangles[bar].setHeight(1);
            rectangles[bar].setY(yModes[side.value()].y(y[bar], 1));
        }
        snapshot(SwingFXUtils.fromFXImage(pane.snapshot(snapshotParameters, writableImage), new BufferedImage((int)pane.getWidth(), (int)pane.getHeight(), BufferedImage.TYPE_INT_RGB)), lengths + 1);

        return lengths + 1;
    }

}