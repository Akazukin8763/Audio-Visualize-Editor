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
    public static final String ARROW_UP_ICON_PATH = new File("src/main/resources/icon/arrow_up-16.png").getAbsolutePath();
    public static final String ARROW_DOWN_ICON_PATH = new File("src/main/resources/icon/arrow_down-16.png").getAbsolutePath();
    public static final String PLAY_ICON_PATH = new File("src/main/resources/icon/play-16.png").getAbsolutePath();
    public static final String PAUSE_ICON_PATH = new File("src/main/resources/icon/pause-16.png").getAbsolutePath();
    public static final String REPLAY_ICON_PATH = new File("src/main/resources/icon/replay-16.png").getAbsolutePath();
    public static final String SOUND_ON_ICON_PATH = new File("src/main/resources/icon/sound_on-16.png").getAbsolutePath();
    public static final String SOUND_OFF_ICON_PATH = new File("src/main/resources/icon/sound_off-16.png").getAbsolutePath();

    public static final String PROJECT_PATH = new File("src/main/resources/project").getAbsolutePath();

}
