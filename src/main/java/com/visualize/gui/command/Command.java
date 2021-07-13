package com.visualize.gui.command;

public interface Command<T> {

    void execute(T t);

    Class<T> getStateClass();

}
