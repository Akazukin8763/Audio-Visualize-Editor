package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import javafx.scene.paint.Color;

public class ColorShadowOffsetYCommand extends Command<Double> {

    public ColorShadowOffsetYCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColorShadowOffsetY(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColorShadowOffsetY(oldValue);
    }

}
