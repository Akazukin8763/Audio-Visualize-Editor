package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import com.visualize.view.*;

public class EqualizerSideCommand extends Command<VisualizeMode.Side> {

    public EqualizerSideCommand(VisualizeMode.Side oldValue, VisualizeMode.Side newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setSide(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setSide(oldValue);
    }

}
