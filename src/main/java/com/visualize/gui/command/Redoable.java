package com.visualize.gui.command;

public interface Redoable {

    void redo();
    boolean canRedo();

}
