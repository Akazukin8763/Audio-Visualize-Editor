package com.visualize.file;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface Player {

    void play();
    void playFrom(double sec);
    void pause();
    void stop();
    double getVolume();
    void setVolume(double volume);

    EventHandler<ActionEvent> getStartEvent();
    EventHandler<ActionEvent> getStopEvent();

}
