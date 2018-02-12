package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;


public class AutoController extends BaseController {

    private Routine center, left, right, test;
    private Routine currentRoutine;

    private String data = "";

    public AutoController() {
        super();

        test = new Routine();
        center = new Routine();
        left = new Routine();
        right = new Routine();

        // works at 70 inches
        left.addCommand(new NothingCommand(1));
        left.addCommand(new TimedDriveCommand(0.8, -0.3));
        left.addCommand(new NothingCommand(1));
        left.addCommand(new IntakeCommand(1, -1.0));

        //test.addCommand(new NothingCommand(1));
        test.addCommand(new ArmCommand(0.75, ArmCommand.Direction.UP));
        // test.addCommand(new IntakeCommand(1, -1.0));
        // test.addCommand(new DriveCommand(100, 1024));
        
        currentRoutine = left;
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
        test.reset();
        left.reset();
    }

}