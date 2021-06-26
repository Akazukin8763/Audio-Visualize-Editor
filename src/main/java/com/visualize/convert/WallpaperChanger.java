package com.visualize.convert;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

public class WallpaperChanger {
    public static native int putImage(String path);
    static {
        //System.load(Jar.getJarPath() + "/wallpaperchanger.dll");
        System.loadLibrary("src/main/resources/wallpaperchanger");
    }
    public static void playWallpaper(String path, int sleepTime) throws InterruptedException {
        System.out.println("Starting to replay video after every "+(sleepTime/1000)+"s");
        File f=new File(path);
        File[] filePath=f.listFiles();
        while(true) {
            for(int i=5;i<=filePath.length;i++) {
                putImage(path+"\\"+i+".jpg");
            }
            Thread.sleep(sleepTime);
        }
    }
    public static void convertMovietoJPG(String mp4Path, String imagePath, String imgType, int frameJump) throws IOException {
        Java2DFrameConverter converter = new Java2DFrameConverter();
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(mp4Path);
        frameGrabber.start();
        Frame frame;
        double frameRate=frameGrabber.getFrameRate();
        int imgNum=0;
        System.out.println("Video has "+frameGrabber.getLengthInFrames()+" frames and has frame rate of "+frameRate);
        try {
            for(int ii=0;ii<frameGrabber.getLengthInFrames();ii++){
                imgNum++;
                frameGrabber.setFrameNumber(ii);
                frame = frameGrabber.grabImage();
                BufferedImage bi = converter.convert(frame);
                String path = imagePath+File.separator+imgNum+".jpg";
                ImageIO.write(bi,imgType, new File(path));
                ii+=frameJump;
            }
            frameGrabber.stop();
        } catch (IllegalArgumentException | IOException e) {
            throw new IOException();
        }
    }
}
