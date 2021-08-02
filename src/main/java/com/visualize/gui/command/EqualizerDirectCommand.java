package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import com.visualize.view.*;

public class EqualizerDirectCommand extends Command<VisualizeMode.Direct> {

    public EqualizerDirectCommand(VisualizeMode.Direct oldValue, VisualizeMode.Direct newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setDirect(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setDirect(oldValue);
    }

}
