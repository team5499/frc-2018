package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Routine {
    private List<BaseCommand> commands = new ArrayList<BaseCommand>();
    private int stepNumber;

    public Routine() {
        stepNumber = 0;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    public BaseCommand getCurrentCommand() {
    }

    public boolean advanceRoutine() {
        if(!(stepNumber + 1 < commands.size())) return false;
        stepNumber++;
        System.out.println("Advance routine: " + stepNumber);
        return true;
    }

    public boolean routineFinished() {
    }

    public void reset() {
        stepNumber = 0;
        commands.forEach((command) -> {command.reset();});
    }

}