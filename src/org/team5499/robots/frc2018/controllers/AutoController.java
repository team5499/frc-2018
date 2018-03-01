package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.GameData;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.timed.TimedArmCommand.Direction;


public class AutoController extends BaseController {

    // starting position or generic routines set here
    public static enum Option {
        LEFT,
        RIGHT,
        CENTER,
        STRAIGHT,
        NOTHING,
        TEST,
        TEST1
    }

    // center routines
    private Routine centerLeft, centerRight;
    // side rountines
    private Routine sideLeft, sideRight, sideLeftCross, sideRightCross;
    // misc routines
    private Routine nothing, straight;
    // test routines
    private Routine test, test1;
    // current routine
    private Routine currentRoutine;
    // routine option
    private Option currentOption;

    public AutoController() {
        super();
        currentOption = Option.NOTHING;
        initRoutines();
        updateRoutine();
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
        Subsystems.intake.reset();
        test.reset();
        sideLeft.reset();
    }

    public void updateRoutine() {
        // check smart dashboard here
        switch(currentOption) {
            case NOTHING:
                currentRoutine = nothing;
                break;
            case LEFT:
                if(GameData.closePlate.equals("L")) currentRoutine = sideLeft;
                else if(GameData.closePlate.equals("R")) currentRoutine = sideLeftCross;
                else {
                    System.err.println("ERROR: Could not load routine!");
                    currentRoutine = nothing;
                }
                break;
            case RIGHT:
                if(GameData.closePlate.equals("L")) currentRoutine = sideRightCross;
                else if(GameData.closePlate.equals("R")) currentRoutine = sideRight;
                else {
                    System.err.println("ERROR: Could not load routine!");
                    currentRoutine = nothing;
                }
                break;
            case CENTER:
                if(GameData.closePlate.equals("L")) currentRoutine = centerLeft;
                else if(GameData.closePlate.equals("R")) currentRoutine = centerRight;
                else {
                    System.err.println("ERROR: Could not load routine!");
                    currentRoutine = nothing;
                }
                break;
            case STRAIGHT:
                currentRoutine = straight;
                break;
            case TEST:
                currentRoutine = test;
                break;
            case TEST1:
                currentRoutine = test1;
                break;
        }
    }

    private void initRoutines() {

        centerLeft = new Routine();
        centerRight = new Routine();
        sideLeft = new Routine();
        sideRight = new Routine();
        sideLeftCross = new Routine();
        sideRightCross = new Routine();
        nothing = new Routine();
        straight = new Routine();
        test = new Routine();
        test1 = new Routine();

        // do nothing for 15 seconds
        nothing.addCommand(new NothingCommand(15));

        // drives 10 feet
        straight.addCommand(new DriveCommand(10, 50));

        // left side auto
        sideLeft.addCommand(new NothingCommand(1));
        sideLeft.addCommand(new TimedDriveCommand(0.8, -0.3));
        sideLeft.addCommand(new NothingCommand(1));
        sideLeft.addCommand(new IntakeCommand(1, -1.0));

        // working center command
        test.addCommand(new DriveCommand(5, -45));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new DriveCommand(2, -45));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(3, -63));
        test.addCommand(new IntakeCommand(1, -1.0));
        test.addCommand(new DriveCommand(2, 12));
        test.addCommand(new TimedArmCommand(1, Direction.DOWN));
        test.addCommand(new TurnCommand(2, -100));
        test.addCommand(new IntakeDriveCommand(3, 45, 1));
        test.addCommand(new TimedArmCommand(2, Direction.UP));
        test.addCommand(new DriveCommand(2, -45));
        test.addCommand(new TurnCommand(2.5, 100));
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
    }

}