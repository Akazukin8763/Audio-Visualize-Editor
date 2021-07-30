package com.visualize.gui.command;

public abstract class Command<T> {

    T oldValue;
    T newValue;

    void execute() {};
    void unExecute() {};

}
