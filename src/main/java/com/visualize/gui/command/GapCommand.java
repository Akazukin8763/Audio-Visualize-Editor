package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class GapCommand extends Command<Integer> {

    public GapCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setGap(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setGap(oldValue);
    }

}
