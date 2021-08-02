package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class MaxFreqCommand extends Command<Double> {

    public MaxFreqCommand(Double oldValue, Double newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setMaxFreq(newValue);
    }

    @Override
    public void unExecute() {
        //System.out.println(ParamUI.paramUI.getMinFreq() + " " + oldValue);
        ParamUI.paramUI.setMaxFreq(oldValue);
    }

}
