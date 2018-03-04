package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.pid.ArmCommand.ArmDirection;


public class AutoController extends BaseController {
    /**
     * THIS CLASS SHOULD CONTAIN ONLY CONTROL LOGIC FOR THE AUTONOMOUS PERIOD
     */

    private Routine center, left, right, nothing, straight, test, test1;
    private Routine current_routine;
    private BaseCommand current_command;

    public AutoController() {
        super();
        center = new Routine();
        left = new Routine();
        right = new Routine();
        nothing = new Routine();
        straight = new Routine();
        test = new Routine();
        test1 = new Routine();

        // drives 10 feet
        straight.addCommand(new DriveCommand(20, 0));

        // works at 70 inches
        left.addCommand(new NothingCommand(1));
        left.addCommand(new TimedDriveCommand(0.8, -0.3));
        left.addCommand(new NothingCommand(1));
        left.addCommand(new IntakeCommand(1, 0.75, false)); /** Positive is outtake */

        // working center command
        test.addCommand(new DriveCommand(5, -45));
        test.addCommand(new TurnCommand(2, -90));
        test.addCommand(new DriveCommand(2, -51));
        test.addCommand(new TurnCommand(2, 90));
        test.addCommand(new DriveCommand(3, -63));
        test.addCommand(new IntakeCommand(1, 0.75, false));
        test.addCommand(new DriveCommand(2, 20));
        test.addCommand(new ArmCommand(1, ArmDirection.DOWN));
        test.addCommand(new TurnCommand(2, -110));
        test.addCommand(new IntakeDriveCommand(3, 70, -1.0, true));
        test.addCommand(new ArmCommand(2, ArmDirection.UP));
        test.addCommand(new DriveCommand(2, -70));
        test.addCommand(new TurnCommand(2.5, 110));
        test.addCommand(new DriveCommand(2, -20));
        test.addCommand(new IntakeCommand(1, 0.75, false));

        test1.addCommand(new DriveCommand(100, -40));
        test1.addCommand(new TurnCommand(0.7, -30));
        test1.addCommand(new DriveCommand(2, -80));
        test1.addCommand(new IntakeCommand(0.8, 0.75, false));
        test1.addCommand(new DriveCommand(0.8, 10));
        test1.addCommand(new TurnCommand(0.8, -60));
        test1.addCommand(new DriveCommand(0.5, -20));
        test1.addCommand(new ArmCommand(0.75, ArmDirection.DOWN));
        test1.addCommand(new NothingCommand(1));
        test1.addCommand(new IntakeDriveCommand(2, -30, -1.0, true));
        test1.addCommand(new ArmCommand(2, ArmDirection.UP));
        test1.addCommand(new DriveCommand(2, -5));
        test1.addCommand(new TurnCommand(2, 90));
        test1.addCommand(new DriveCommand(2, -30));
        test1.addCommand(new IntakeCommand(2, 0.75, false));
        
        current_routine = test;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller Started"); /** Eventually replace with logger */
    }

    @Override
    public void handle() {
        /** Make sure the game data is loaded, and set the correct routine */


        /** Once the correct routine is choosen, handle it */
        if(current_routine.isFinished()) {
            return;
        }

        if(current_command == null) {
            current_command = current_routine.getCurrentCommand();
            current_command.start();
        } else if(current_command.isFinished()) {
            current_routine.advanceRoutine();
            current_command = current_routine.getCurrentCommand();
            current_command.start();
        } else {
            current_command.handle();
        }
    }

    @Override
    public void reset() {
        Subsystems.drivetrain.stop();
        Subsystems.intake.stopArm();
        Subsystems.intake.stopIntake();
        Subsystems.climber.stop();

        Subsystems.drivetrain.setAngle(0);
        Subsystems.drivetrain.setDistance(0);
    }

}