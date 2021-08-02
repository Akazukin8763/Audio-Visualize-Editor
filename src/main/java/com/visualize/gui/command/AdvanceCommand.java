package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

// 造成無限指令
public class AdvanceCommand extends Command<Boolean> {

    public AdvanceCommand(Boolean oldValue, Boolean newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setAdvancedEnable(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setAdvancedEnable(oldValue);
    }

}
