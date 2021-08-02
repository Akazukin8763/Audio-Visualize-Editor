package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class MinFreqCommand extends Command<Double> {

    public MinFreqCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setMinFreq(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setMinFreq(oldValue);
    }

}
