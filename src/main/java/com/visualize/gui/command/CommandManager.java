package com.visualize.gui.command;

import java.util.Stack;

import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CannotRedoException;

public class CommandManager implements Undoable, Redoable{

    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    // Constructor
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    // Methods
    public <T> void execute(Command<T> command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void clearHistory() {
        undoStack.clear();
        redoStack.clear();
    }

    @Override
    public void undo() {
        if (canUndo()) {
            Command command = undoStack.pop();
            command.unExecute(); // 返回上一步
            redoStack.push(command);
        }
        else
            throw new CannotUndoException();
    }

    @Override
    public boolean canUndo() {
        return !undoStack.empty();
    }

    @Override
    public void redo() {
        if (canRedo()) {
            Command command = redoStack.pop();
            command.execute(); // 重新執行下一步
            undoStack.push(command);
        }
        else
            throw new CannotRedoException();
    }

    @Override
    public boolean canRedo() {
        return !redoStack.empty();
    }
}
