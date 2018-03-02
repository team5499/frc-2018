package org.team5499.robots.frc2018.commands;

import java.util.ArrayList;
import java.util.List;

import org.team5499.robots.frc2018.subsystems.Subsystems;

public class Routine {
    private List<BaseCommand> commands = new ArrayList<BaseCommand>();
    private int step_number;

    public Routine() {
        step_number = 0;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    public BaseCommand getCurrentCommand() {
        return commands.get(step_number);
    }

    public boolean advanceRoutine() {
        if(!(step_number + 1 < commands.size())) return false;
        step_number++;
        System.out.println("Advance routine: " + step_number);
        return true;
    }

    public boolean isFinished() {
        return (!(step_number + 1 < commands.size()) && commands.get(step_number).isFinished());
    }

    public void reset() {
        step_number = 0;
        commands.forEach((command) -> {command.reset();});
    }

}