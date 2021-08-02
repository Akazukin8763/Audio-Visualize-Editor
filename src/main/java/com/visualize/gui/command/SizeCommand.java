package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class SizeCommand extends Command<Integer> {

    public SizeCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setSize(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setSize(oldValue);
    }

}
