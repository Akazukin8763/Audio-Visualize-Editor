package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class BackgroundImagePosXCommand extends Command<Integer> {

    public BackgroundImagePosXCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setBackgroundImagePosX(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setBackgroundImagePosX(oldValue);
    }

}
