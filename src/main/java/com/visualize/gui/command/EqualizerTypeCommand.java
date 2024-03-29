package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import com.visualize.view.*;

public class EqualizerTypeCommand extends Command<VisualizeMode.View> {

    public EqualizerTypeCommand(VisualizeMode.View oldValue, VisualizeMode.View newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setView(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setView(oldValue);
    }

}
