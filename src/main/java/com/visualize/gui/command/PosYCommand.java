package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class PosYCommand extends Command<Double> {

    public PosYCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setPosY(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setPosY(oldValue);
    }

}
