package org.team5499.robots.frc2018.controllers;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.subsystems.Subsystems;

import edu.wpi.first.wpilibj.DriverStation;

import org.team5499.robots.frc2018.commands.*;
import org.team5499.robots.frc2018.commands.pid.*;
import org.team5499.robots.frc2018.commands.timed.*;
import org.team5499.robots.frc2018.commands.pid.ArmCommand.ArmDirection;


public class AutoController extends BaseController {
    /**
     * THIS CLASS SHOULD CONTAIN ONLY CONTROL LOGIC FOR THE AUTONOMOUS PERIOD
     */
    private Routine current_routine;


    private Routine straight, straight_score, left_straight_score, right_straight_score, left_far_score, right_far_score, center_left_score, center_right_score, tuning;

    private BaseCommand current_command;
    private String game_data;

    public AutoController() {
        super();
        game_data = null;
        Dashboard.setDouble("distance_setpoint", 0);
        Dashboard.setDouble("angle_setpoint", 0);

        center_left_score = new Routine();
        center_right_score = new Routine();
        left_far_score = new Routine();
        right_far_score = new Routine();
        left_straight_score = new Routine();
        right_straight_score = new Routine();
        straight = new Routine();
        straight_score = new Routine();
        tuning = new Routine();

        // drives 10 feet
        straight.addCommand(new NothingCommand(10));
        straight.addCommand(new DriveCommand(20, -100));

        // working center right command
        center_right_score.addCommand(new DriveCommand(5, -45));
        center_right_score.addCommand(new TurnCommand(2, -90));
        center_right_score.addCommand(new DriveCommand(2, -51));
        center_right_score.addCommand(new TurnCommand(2, 90));
        center_right_score.addCommand(new DriveCommand(3, -63));
        center_right_score.addCommand(new IntakeCommand(1, 0.75, false));
        center_right_score.addCommand(new DriveCommand(2, 20));
        center_right_score.addCommand(new ArmCommand(1, ArmDirection.DOWN));
        center_right_score.addCommand(new TurnCommand(2, -110));
        center_right_score.addCommand(new IntakeDriveCommand(3, 70, -1.0, true));
        center_right_score.addCommand(new ArmCommand(2, ArmDirection.UP));
        center_right_score.addCommand(new DriveCommand(2, -70));
        center_right_score.addCommand(new TurnCommand(2.5, 110));
        center_right_score.addCommand(new DriveCommand(2, -20));
        center_right_score.addCommand(new IntakeCommand(1, 0.75, false));

        center_left_score.addCommand(new DriveCommand(5, -45));
        center_left_score.addCommand(new TurnCommand(2, 90));
        center_left_score.addCommand(new DriveCommand(2, -51));
        center_left_score.addCommand(new TurnCommand(2, -90));
        center_left_score.addCommand(new DriveCommand(3, -63));
        center_left_score.addCommand(new IntakeCommand(1, 0.75, false));
        center_left_score.addCommand(new DriveCommand(2, 50));
        center_left_score.addCommand(new ArmCommand(1, ArmDirection.DOWN));
        center_left_score.addCommand(new TurnCommand(2, 130));
        center_left_score.addCommand(new IntakeDriveCommand(3, 50, -1.0, false));
        center_left_score.addCommand(new ArmCommand(2, ArmDirection.UP));
        center_left_score.addCommand(new DriveCommand(2, -40));
        center_left_score.addCommand(new TurnCommand(2.5, -130));
        center_left_score.addCommand(new DriveCommand(2, -60));
        center_left_score.addCommand(new IntakeCommand(1, 0.75, false));

        left_straight_score.addCommand(new DriveCommand(3, -160));
        left_straight_score.addCommand(new TurnCommand(2, -90));
        left_straight_score.addCommand(new DriveCommand(2, -40));
        left_straight_score.addCommand(new OuttakeCommand(1, 0.5));

        tuning.addCommand(new DriveCommand(10, 36)); // Drives three feet forward
        
        current_routine = straight;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller Started"); /** Eventually replace with logger */
    }

    @Override
    public void handle() {
        /** Make sure the game data is loaded, and set the correct routine */
        while(DriverStation.getInstance().getGameSpecificMessage().length() < 3) {
            return;
        }
        game_data = DriverStation.getInstance().getGameSpecificMessage();
        Dashboard.setString("game_data", game_data);
        switch(Dashboard.getString("automode")) {
            case "left":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = straight_score;
                } else {
                    current_routine = left_far_score;
                }
                break;
            case "center":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = center_left_score;
                } else {
                    current_routine = center_right_score;
                }
                break;
            case "right":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = right_far_score;
                } else {
                    current_routine = straight_score;
                }
                break;
            case "tuning":
                current_routine = tuning;
                break;
            default:
                System.out.println("Automode mot recognized");
                break;
        }

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

        Dashboard.setDouble("distance_setpoint", 0); // Reset the global distance setpoint
        Dashboard.setDouble("angle_setpoint", 0); // Reset the global angle setpoint

        center_left_score.reset();
        center_right_score.reset();
        left_far_score.reset();
        right_far_score.reset();
        left_straight_score.reset();
        right_straight_score.reset();
        straight.reset();
        straight_score.reset();
        tuning.reset();

        game_data = null;
    }

}