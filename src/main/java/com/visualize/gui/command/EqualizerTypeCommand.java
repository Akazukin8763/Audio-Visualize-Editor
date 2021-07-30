package com.visualize.gui.command;

import com.visualize.view.*;

public class EqualizerTypeCommand extends Command<VisualizeMode.View> {

    public EqualizerTypeCommand(VisualizeMode.View view) {
        this.oldValue = view;
    }

    @Override
    public void execute() {
        System.out.println("沒做事 EqualizerTypeCommand");
    }

}
