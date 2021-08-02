package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class SensitivityCommand extends Command<Double> {

    public SensitivityCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setSensitivity(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setSensitivity(oldValue);
    }

}
