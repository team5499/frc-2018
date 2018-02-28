package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.GameData;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.timed.TimedArmCommand.Direction;


public class AutoController extends BaseController {

    public static enum Option {
        SIDE,
        CENTER,
        STRAIGHT,
        NOTHING
    }

    private Routine centerLeft, centerRight, left, right, nothing, straight, test, test1;
    private Routine currentRoutine;
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
        left.reset();
    }

    public void updateRoutine() {
        // check smart dashboard here
        switch(currentOption) {
            case NOTHING:
                currentRoutine = nothing;
                break;
            case SIDE:
                if(GameData.closePlate.equals("L")) currentRoutine = left;
                else if(GameData.closePlate.equals("R")) currentRoutine = right;
                else {
                    System.err.println("ERROR: Could not find load routine!");
                    currentRoutine = nothing;
                }
                break;
            case CENTER:
                if(GameData.closePlate.equals("L")) currentRoutine = centerLeft;
                else if(GameData.closePlate.equals("R")) currentRoutine = centerRight;
                else {
                    System.err.println("ERROR: Could not find load routine!");
                    currentRoutine = nothing;
                }
                break;
            case STRAIGHT:
                currentRoutine = straight;
                break;
        }
    }

    private void initRoutines() {
        test = new Routine();
        test1 = new Routine();
        centerLeft = new Routine();
        centerRight = new Routine();
        left = new Routine();
        right = new Routine();
        nothing = new Routine();
        straight = new Routine();

        nothing.addCommand(new NothingCommand(15));

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