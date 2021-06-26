package com.visualize.convert;

//import com.visualize.Jar;
import com.visualize.gui.*;

import javax.swing.SwingWorker;
import java.util.concurrent.ExecutionException;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundExportMp4 extends SwingWorker< Boolean, Object > {
    private final MediaPlayer mediaPlayer;

    private String jpgDirPath;
    private String audioPath;
    private float fps;
    private int width;
    private int height;
    private String exportPath;
    private int imgNum;

    public BackgroundExportMp4(String jpgDirPath, String audioPath, float fps, int width, int height, String exportPath, int imgNum) {
        //this.mediaPlayer = new MediaPlayer(new Media(new File(Jar.getJarPath() + "/music/__finish__.mp3").toURI().toString()));
        this.mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/music/__finish__.mp3").toURI().toString()));
        this.mediaPlayer.setVolume(.5);

        this.jpgDirPath = jpgDirPath;
        this.audioPath = audioPath;
        this.fps = fps;
        this.width = width;
        this.height = height;
        this.exportPath = exportPath;
        this.imgNum = imgNum;
    }

    public Boolean doInBackground() throws Exception {
        ExportMp4.exportMp4(jpgDirPath, audioPath, fps, width, height, exportPath);

        for (int i = 1; i <= imgNum; i++) {
            //java.io.File imgFile = new java.io.File(String.format(Jar.getJarPath() + "/image/%06d.jpg", i));
            java.io.File imgFile = new java.io.File(String.format("src/main/resources/image/%06d.jpg", i));
            imgFile.delete();
        }
        return true;
    }

    protected void done() {
        try {
            if(get()) {
                EventLog.eventLog.finish("The file has been exported correctly.");
                mediaPlayer.play();
            }
        } catch (InterruptedException ex) {
            EventLog.eventLog.warning("Interrupted while waiting for export.");
        } catch (ExecutionException ex) {
            EventLog.eventLog.warning("The file can not be exported correctly.");
        }
    }
}