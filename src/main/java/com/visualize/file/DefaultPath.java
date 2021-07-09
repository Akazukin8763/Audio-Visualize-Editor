package com.visualize.file;

import java.io.File;

public final class DefaultPath {

    public static final String DEFAULT_MUSIC_PATH = new File("src/main/resources/music/__default__.wav").getAbsolutePath();

    public static final String TEMP_VIDEO_PATH = new File("src/main/resources/video/__no_audio_video__.mp4").getAbsolutePath();
    public static final String TEMP_WAV_PATH = new File("src/main/resources/music/__convert_audio__.wav").getAbsolutePath();
    public static final String TEMP_PCM_PATH = new File("src/main/resources/music/__convert_audio__.pcm").getAbsolutePath();

    public static final String ICON_PATH = new File("src/main/resources/icon/icon.png").getAbsolutePath();
    public static final String MUSIC_ICON_PATH = new File("src/main/resources/icon/music-16.png").getAbsolutePath();
    public static final String FOLDER_ICON_PATH = new File("src/main/resources/icon/folder-16.png").getAbsolutePath();
    public static final String CANCEL_ICON_PATH = new File("src/main/resources/icon/cancel-16.png").getAbsolutePath();

}
