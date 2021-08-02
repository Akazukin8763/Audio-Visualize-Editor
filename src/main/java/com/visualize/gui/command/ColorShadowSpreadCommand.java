package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class ColorShadowSpreadCommand extends Command<Double>  {

    public ColorShadowSpreadCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setColorShadowSpread(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setColorShadowSpread(oldValue);
    }

}
