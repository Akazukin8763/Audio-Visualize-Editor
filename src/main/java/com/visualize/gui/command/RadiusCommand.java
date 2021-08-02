package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class RadiusCommand extends Command<Integer> {

    public RadiusCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setRadius(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setRadius(oldValue);
    }

}
