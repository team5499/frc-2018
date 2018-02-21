package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.timed.TimedArmCommand.Direction;


public class AutoController extends BaseController {

    private Routine center, left, right, nothing, straight, test;
    private Routine currentRoutine;

    public AutoController() {
        super();

        test = new Routine();
        center = new Routine();
        left = new Routine();
        right = new Routine();
        nothing = new Routine();
        straight = new Routine();

        // drives 10 feet
        straight.addCommand(new DriveCommand(15, 120));

        // works at 70 inches
        left.addCommand(new NothingCommand(1));
        left.addCommand(new TimedDriveCommand(0.8, -0.3));
        left.addCommand(new NothingCommand(1));
        left.addCommand(new IntakeCommand(1, -1.0));

        // working center command
        test.addCommand(new DriveCommand(2, 45));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new DriveCommand(2, 45));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(3, 63));
        test.addCommand(new IntakeCommand(1, -1.0));
        test.addCommand(new TimedArmCommand(0.5, Direction.DOWN));
        test.addCommand(new DriveCommand(2, -12));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new IntakeDriveCommand(2,-30, 1));
        test.addCommand(new TimedArmCommand(2, Direction.UP));
        test.addCommand(new DriveCommand(2, 30));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(2, 10));
        test.addCommand(new IntakeCommand(1, -1));
        

        
        currentRoutine = test;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller started!");
        currentRoutine.start();
    }

    @Override
    public void handle() {
        currentRoutine.handle();
    }

    public void reset() {
        Subsystems.drivetrain.reset();
        test.reset();
        left.reset();
    }

}