package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Routine {

    private List<BaseCommand> commands = new ArrayList<BaseCommand>();
    private BaseCommand currentCommand;
    private int stepNumber;
    private boolean finished;

    public Routine() {
        finished = false;
        stepNumber = 0;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
        currentCommand = commands.get(0);
    }

    public void start() {
        // System.out.println("Command started");
        currentCommand = commands.get(0);
        currentCommand.start();
    }

    public void handle() {
        if(currentCommand.isFinished()) {
            Subsystems.drivetrain.stop();
            Subsystems.intake.stop();
            Subsystems.climber.stop();
            if(!advanceRoutine()) {
                finished = true;
                return;
            }
        } else {
            currentCommand.handle();
        }
    }

    public boolean advanceRoutine() {
        if(!(stepNumber + 1 < commands.size())) return false;
        stepNumber++;
        System.out.println("Advance routine: " + stepNumber);
        currentCommand = commands.get(stepNumber);
        currentCommand.start();
        return true;
    }

    public void reset() {
        stepNumber = 0;
        commands.forEach((command) -> {command.reset();});
        currentCommand = commands.get(0);
        finished = false;
    }

    public boolean isFinished() {
        return finished;
    }



}