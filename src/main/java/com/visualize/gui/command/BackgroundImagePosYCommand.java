package com.visualize.gui.command;

import com.visualize.gui.ParamUI;

public class BackgroundImagePosYCommand extends Command<Integer> {

    public BackgroundImagePosYCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        ParamUI.paramUI.setBackgroundImagePosY(newValue);
    }

    @Override
    public void unExecute() {
        ParamUI.paramUI.setBackgroundImagePosY(oldValue);
    }

}
