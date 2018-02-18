package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.timed.TimedArmCommand.Direction;


public class AutoController extends BaseController {

    private Routine center, left, right, nothing, straight, test, test1;
    private Routine currentRoutine;

    private String data = "";

    public AutoController() {
        super();

        test = new Routine();
        test1 = new Routine();
        center = new Routine();
        left = new Routine();
        right = new Routine();
        nothing = new Routine();
        straight = new Routine();

        // drives 10 feet
        straight.addCommand(new DriveCommand(20, 50));

        // works at 70 inches
        left.addCommand(new NothingCommand(1));
        left.addCommand(new TimedDriveCommand(0.8, -0.3));
        left.addCommand(new NothingCommand(1));
        left.addCommand(new IntakeCommand(1, -1.0));

        // working center command
        test.addCommand(new DriveCommand(5, -45));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new DriveCommand(2, -45));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(3, -63));
        test.addCommand(new IntakeCommand(1, -1.0));
        test.addCommand(new TimedArmCommand(0.5, Direction.DOWN));
        test.addCommand(new DriveCommand(2, 12));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new IntakeDriveCommand(2,-30, 1));
        test.addCommand(new TimedArmCommand(2, Direction.UP));
        test.addCommand(new DriveCommand(2, -30));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(2, -10));
        test.addCommand(new IntakeCommand(1, -1));

        test1.addCommand(new DriveCommand(100, -40));
        test1.addCommand(new TurnCommand(0.7, -30));
        test1.addCommand(new DriveCommand(2, -80));
        test1.addCommand(new IntakeCommand(0.8, -0.6));
        test1.addCommand(new DriveCommand(0.8, 10));
        test1.addCommand(new TurnCommand(0.8, -60));
        test1.addCommand(new DriveCommand(0.5, -20));
        test1.addCommand(new TimedArmCommand(0.75, Direction.DOWN));
        test1.addCommand(new NothingCommand(1));
        test1.addCommand(new IntakeDriveCommand(2, -30, 0.65));
        test1.addCommand(new TimedArmCommand(2, Direction.UP));
        test1.addCommand(new DriveCommand(2, -5));
        test1.addCommand(new TurnCommand(2, 90));
        test1.addCommand(new DriveCommand(2, -30));
        test1.addCommand(new IntakeCommand(2, -0.65));
        
        currentRoutine = test1;
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

    @Override
    public void loadGameData(String data) { 
        this.data = data;
        System.out.println("Data loaded: " + data);
    }

    public void reset() {
        Subsystems.drivetrain.reset();
        Subsystems.intake.reset();
        test.reset();
        left.reset();
    }

}