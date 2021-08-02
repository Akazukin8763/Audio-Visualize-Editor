package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class ColorShadowCommand extends Command<Color> {

    public ColorShadowCommand(Color oldValue, Color newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColorShadow(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColorShadow(oldValue);
    }

}
