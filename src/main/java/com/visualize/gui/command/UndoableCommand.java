package com.visualize.gui.command;

public interface UndoableCommand<T> extends Command<T> {

    void undo(T state);

}
