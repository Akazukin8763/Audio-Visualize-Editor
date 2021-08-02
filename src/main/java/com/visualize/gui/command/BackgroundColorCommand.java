package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class BackgroundColorCommand extends Command<Color> {

    public BackgroundColorCommand(Color oldValue, Color newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setBackgroundColor(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setBackgroundColor(oldValue);
    }

}
