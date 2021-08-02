package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class ColorShadowRadiusCommand extends Command<Integer> {

    public ColorShadowRadiusCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColorShadowRadius(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColorShadowRadius(oldValue);
    }

}
