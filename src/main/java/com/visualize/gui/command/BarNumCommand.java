package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class BarNumCommand extends Command<Integer> {

    public BarNumCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setBarNum(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setBarNum(oldValue);
    }

}
