package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

import com.visualize.view.*;

public class EqualizerStereoCommand extends Command<VisualizeMode.Stereo> {

    public EqualizerStereoCommand(VisualizeMode.Stereo oldValue, VisualizeMode.Stereo newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setStereo(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setStereo(oldValue);
    }

}
