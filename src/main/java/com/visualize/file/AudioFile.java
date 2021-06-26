package com.visualize.file;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public abstract class AudioFile extends File implements Player{

    protected AudioInputStream audioInputStream;
    protected AudioFormat audioFormat;

    protected int frameLength; // 偵數長度
    protected int frameSize; // 偵數大小 (Bytes = bitDepth * channel / 8)
    protected float frameRate; // 取樣率 (44100Hz, 48000Hz)
    protected int bitDepth; // 位元深度
    protected int channels; // 聲道
    protected float duration; // 時間

    protected double[][] audioData; // 音檔內容 [channels][frameLength] (時域: 振幅介於 -1 ~ 1 之間)

    // Constructor
    public AudioFile (String path) {
        super(path);
    }

    // Method
    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public int getFrameLength() {
        return frameLength;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public float getFrameRate() {
        return frameRate;
    }

    public int getBitDepth() {
        return bitDepth;
    }

    public int getChannels() {
        return channels;
    }

    public float getDuration() {
        return duration;
    }

    public double[][] getAudioData() {
        if (audioData == null)
            throw new NullPointerException("Subclass doesn't define audioData.");
        return audioData;
    }

}