package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class ColorShadowOffsetXCommand extends Command<Double> {

    public ColorShadowOffsetXCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColorShadowOffsetX(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColorShadowOffsetX(oldValue);
    }

}
