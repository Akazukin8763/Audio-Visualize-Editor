package com.visualize.gui.command;

public class BarNumCommand extends Command<Integer> {

    public BarNumCommand(Integer oldValue, Integer newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void execute() {
        System.out.println("BarNumCommand(Execute): " + newValue);
    }

    @Override
    public void unExecute() {
        System.out.println("BarNumCommand(UnExecute): " + oldValue);
    }

}
