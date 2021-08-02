package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class RotationCommand extends Command<Double> {

    public RotationCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setRotation(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setRotation(oldValue);
    }

}
