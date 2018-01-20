package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Routine {

    private List<BaseCommand> commands = new ArrayList<>();
    private BaseCommand currentCommand;
    private int stepNumber;

    public Routine() {
        stepNumber = 0;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
        currentCommand = commands.get(0);
    }

    public void start() {
        currentCommand.start();
    }

    public void handle() {
        if(currentCommand.isFinished()) {
            Subsystems.drivetrain.stop();
            if(!advanceRoutine()) return;
        } else {
            currentCommand.handle();
        }
    }

    public boolean advanceRoutine() {
        if(!(stepNumber + 1 < commands.size())) return false;
        stepNumber++;
        currentCommand = commands.get(stepNumber);
        currentCommand.start();
        return true;
    }

    public void reset() {
        stepNumber = 0;
    }



}