package com.visualize.convert;

//import org.bytedeco.ffmpeg.global.avcodec;
//import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
import java.io.File;

public class ExportMp4 {

    /*private static void createMp4(String jpgDirPath, String targetPath, float fps, int mWidth, int mHeight) throws FrameRecorder.Exception {
        final File[] jpgs = new File(jpgDirPath).listFiles();
        if (jpgs == null || jpgs.length == 0) {
            return;
        }

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(targetPath, mWidth, mHeight);
        //set codec
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //set frame rate
        recorder.setFrameRate(fps);
        recorder.setVideoQuality(0);
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");

        try {
            recorder.start();
            Java2DFrameConverter converter = new Java2DFrameConverter();
            for (File jpg : jpgs) {
                BufferedImage read = ImageIO.read(jpg);
                recorder.record(converter.getFrame(read));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            recorder.stop();
            recorder.release();
        }

    }*/

    public static void mergeAudio(String videoPath, String audioPath, String exportPath) throws Exception {
        FrameRecorder recorder = null;
        FrameGrabber grabber1 = null;
        FrameGrabber grabber2 = null;

        try {
            // 抓取影音檔幀數
            grabber1 = new FFmpegFrameGrabber(videoPath);
            grabber2 = new FFmpegFrameGrabber(audioPath);

            grabber1.start();
            grabber2.start();
            //創建錄製
            recorder = new FFmpegFrameRecorder(exportPath,
                    grabber1.getImageWidth(), grabber1.getImageHeight(),
                    grabber2.getAudioChannels());

            recorder.setFormat("mp4");
            recorder.setVideoBitrate(grabber1.getVideoBitrate());
            recorder.setFrameRate(grabber1.getFrameRate());
            recorder.setSampleRate(grabber2.getSampleRate());

            recorder.start();

            Frame frame1;
            Frame frame2 ;

            // 錄入影檔
            while ((frame1 = grabber1.grabFrame()) != null )
                recorder.record(frame1);
            // 錄入音檔
            while ((frame2 = grabber2.grabFrame()) != null)
                recorder.record(frame2);

            grabber1.stop();
            grabber2.stop();
            recorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (recorder != null) {
                    recorder.release();
                }
                if (grabber1 != null) {
                    grabber1.release();
                }
                if (grabber2 != null) {
                    grabber2.release();
                }
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exportMp4(String jpgDirPath, String audioPath, float fps, int width, int height, String exportPath) throws Exception {
        //將資料夾中的全部圖片(jpg)轉為mp4檔後再嵌入mp3融合出新的mp4
        //createMp4(jpgDirPath, "temp.mp4", fps, width, height);
        mergeAudio("temp.mp4", audioPath, exportPath);
        //刪掉過程檔案
        (new File("temp.mp4")).delete();
    }
}