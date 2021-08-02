package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class PosXCommand extends Command<Double> {

    public PosXCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setPosX(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setPosX(oldValue);
    }

}
