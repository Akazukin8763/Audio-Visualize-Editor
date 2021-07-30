package com.visualize.gui.command;

public interface Undoable {

    void undo();
    boolean canUndo();

}
