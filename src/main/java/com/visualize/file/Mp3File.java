package com.visualize.file;

//import com.visualize.Jar;
import com.visualize.export.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;

public class Mp3File extends AudioFile{

    private final MediaPlayer mediaPlayer;

    //private final String wavPath = Jar.getJarPath() + "/music/__temp__.wav"; // 轉換的暫存（我想刪，但我刪不掉）

    // Constructor
    public Mp3File (String path) throws javax.sound.sampled.UnsupportedAudioFileException, java.io.IOException{
        super(path);

        MP3toWAV.mp3ToWav(this.getAbsolutePath(), DefaultPath.TEMP_WAV_PATH);
        //BackgroundMP3toWAV backgroundMP3toWAV = new BackgroundMP3toWAV(this.getAbsolutePath(), wavPath);
        //backgroundMP3toWAV.execute();

        this.audioInputStream = AudioSystem.getAudioInputStream(new File(DefaultPath.TEMP_WAV_PATH));
        this.audioFormat = audioInputStream.getFormat();

        this.frameLength = (int) audioInputStream.getFrameLength();
        this.frameSize = audioFormat.getFrameSize();
        this.frameRate = audioFormat.getFrameRate();
        this.bitDepth = audioFormat.getSampleSizeInBits();
        this.channels = audioFormat.getChannels();
        this.duration = frameLength / frameRate;

        byte[] bytes = new byte[frameLength * frameSize];  // 原始檔案 (不包含 Header)
        int result = audioInputStream.read(bytes);
        this.audioData = transfers(bytes, audioFormat);

        this.mediaPlayer = new MediaPlayer(new Media(this.toURI().toString()));
    }

    // Method
    private double[][] transfers(byte[] bytes, AudioFormat audioFormat) throws javax.sound.sampled.UnsupportedAudioFileException {
        if (!audioFormat.getEncoding().toString().equals("PCM_SIGNED") &&
                !audioFormat.getEncoding().toString().equals("PCM_UNSIGNED"))
            throw new javax.sound.sampled.UnsupportedAudioFileException("Only supports \"PCM_SIGNED\" and \"PCM_UNSIGNED\"");

        int channels = audioFormat.getChannels();
        int frameLength = bytes.length / audioFormat.getFrameSize();
        int bitDepth = audioFormat.getSampleSizeInBits();

        long[][] transfer = new long[channels][frameLength]; // 原始檔案組建後的內容
        double[][] audioData = new double[channels][frameLength]; // 振幅 (介於 -1 ~ 1 之間)
        final int blocks = bitDepth / 8;
        final long shift = 64 - bitDepth;
        final long offset = 1L << (bitDepth - 1);

        // 將位元組依照 bitDepth 切開，並且依照 channel 分類
        // └ wav 檔基本上 bitDepth = 16-bit, 24-bit; encoding = PCM_SIGNED
        // └ wav 檔資料擺放方式為 Little-Endian
        // └ wav 檔只有單雙聲道，而雙聲道資料擺放方式為 [LR|LR|LR|LR...]
        for (int i = 0; i < bytes.length;) {
            for (int channel = 0; channel < channels; channel++) {
                long sample = 0;

                for (int block = 0; block < blocks; block++) { // 統整 byte
                    sample |=  (bytes[i + block] & 0xffL) << (8 * block);
                }

                transfer[channel][i / (channels * blocks)] = sample;
                i += blocks;
            }
        }
        // └ 將有號進行位元延伸，並將無號改為有號
        if (audioFormat.getEncoding().toString().equals("PCM_SIGNED")) { // 位元延伸
            for (int channel = 0; channel < channels; channel++) {
                for (int length = 0; length < frameLength; length++) {
                    transfer[channel][length] = (transfer[channel][length] << shift) >> shift;
                }
            }
        }
        else if (audioFormat.getEncoding().toString().equals("PCM_UNSIGNED")) { // 校正偏移
            for (int channel = 0; channel < channels; channel++) {
                for (int length = 0; length < frameLength; length++) {
                    transfer[channel][length] ^= offset;
                }
            }
        }
        // └ 將振幅轉為 -1 ~ 1 之間的波
        for (int channel = 0; channel < channels; channel++) {
            for (int length = 0; length < frameLength; length++) {
                audioData[channel][length] = 1.0 * transfer[channel][length] / offset;
            }
        }

        return audioData;
    }

    @Override
    public void play() {
        mediaPlayer.play();
    }

    @Override
    public void playFrom(double sec) {
        mediaPlayer.seek(new javafx.util.Duration(sec * 1000));
        mediaPlayer.play();
    }

    @Override
    public void stop() {
        mediaPlayer.stop();
    }

    @Override
    public double getVolume() {
        return mediaPlayer.getVolume();
    }

    @Override
    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    @Override
    public EventHandler<ActionEvent> getStartEvent() {
        return (event) -> mediaPlayer.play();
    };

    @Override
    public EventHandler<ActionEvent> getStopEvent() {
        return (event) -> mediaPlayer.stop();
    };
}
