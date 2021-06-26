package com.visualize.convert;

import com.visualize.gui.*;

import javax.swing.SwingWorker;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class BackgroundWallpaperChanger extends SwingWorker< Boolean, Object > {
    private final String videoPath;
    private final String imgDir;

    public BackgroundWallpaperChanger(String videoPath, String imgDir) {
        this.videoPath = videoPath;
        this.imgDir = imgDir;
    }

    public Boolean doInBackground() throws InterruptedException, IOException {
        WallpaperChanger.convertMovietoJPG(videoPath, imgDir, "jpg", 0);
        WallpaperChanger.playWallpaper(imgDir, 0);
        return true;
    }

    protected void done() {
        try {
            if (get())
                EventLog.eventLog.finish("The wallpaper has been set correctly.");
        } catch (InterruptedException | ExecutionException ex) {
            EventLog.eventLog.warning("The wallpaper-play has been interrupted.");
        } /*catch (java.io.IOException e) {
            EventLog.eventLog.warning("The wallpaper can not be set correctly.");
        }*/
    }
}
