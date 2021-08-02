package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class ColorCommand extends Command<Color> {

    public ColorCommand(Color oldValue, Color newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColor(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColor(oldValue);
    }

}
