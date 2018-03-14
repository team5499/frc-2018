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


    //private Routine straight, straight_score, left_straight_score, right_straight_score, left_far_score, right_far_score, center_left_score, center_right_score, tuning;
    /**
     * RO - Right outer
     * RI - Right inner
     * M  - Middle
     * LI - Left inner
     * LO - Left outer
     * 
     * NC - No cubes
     * OC - One cube(default)
     * TC - Two cube
     */
    private Routine ro_oc, ro_nc, ri_oc, ri_nc, m_nc, m_oc_r, m_oc_l, m_tc_r, m_tc_l, li_oc, li_nc, lo_oc, lo_nc, nothing, tuning;

    private BaseCommand current_command;
    private String game_data;

    public AutoController() {
        super();
        game_data = null;
        Dashboard.setDouble("distance_setpoint", 0);
        Dashboard.setDouble("angle_setpoint", 0);

        ro_oc = new Routine();
        ro_nc = new Routine();
        ri_oc = new Routine();
        ri_nc = new Routine();
        m_nc = new Routine(); // Just drive straight a bit
        m_oc_r = new Routine();
        m_oc_l = new Routine();
        m_tc_r = new Routine();
        m_tc_l = new Routine();
        li_oc = new Routine();
        li_nc = new Routine();
        lo_oc = new Routine();
        lo_nc = new Routine();
        nothing = new Routine();
        tuning = new Routine();

        ro_oc.addCommand(new NothingCommand(0));
        ro_oc.addCommand(new ArmCommand(1, true, 110));
        

        // drives 90 inches(just enough to cross baseline)
        ro_nc.addCommand(new NothingCommand(0));
        ro_nc.addCommand(new DriveCommand(3, false, -90));

        // working center right command
        center_right_score.addCommand(new DriveCommand(5, false, -45));
        center_right_score.addCommand(new TurnCommand(2, false, -90));
        center_right_score.addCommand(new DriveCommand(2, false, -51));
        center_right_score.addCommand(new TurnCommand(2, false, 90));
        center_right_score.addCommand(new DriveCommand(3, false, -63));
        center_right_score.addCommand(new IntakeCommand(1, 0.75, false));
        center_right_score.addCommand(new DriveCommand(2, false, 20));
        center_right_score.addCommand(new ArmCommand(1, false, -25));
        center_right_score.addCommand(new TurnCommand(2, false, -110));
        center_right_score.addCommand(new IntakeDriveCommand(3, false, 70, -1.0, true));
        center_right_score.addCommand(new ArmCommand(2, false, 100));
        center_right_score.addCommand(new DriveCommand(2, false, -70));
        center_right_score.addCommand(new TurnCommand(2.5, false, 110));
        center_right_score.addCommand(new DriveCommand(2, false, -20));
        center_right_score.addCommand(new IntakeCommand(1, 0.75, false));

        center_left_score.addCommand(new DriveCommand(5, false, -45));
        center_left_score.addCommand(new TurnCommand(2, false, 90));
        center_left_score.addCommand(new DriveCommand(2, false, -51));
        center_left_score.addCommand(new TurnCommand(2, false, -90));
        center_left_score.addCommand(new DriveCommand(3, false, -63));
        center_left_score.addCommand(new IntakeCommand(1, 0.75, false));
        center_left_score.addCommand(new DriveCommand(2, false, 50));
        center_left_score.addCommand(new ArmCommand(1, false, -25));
        center_left_score.addCommand(new TurnCommand(2, false, 130));
        center_left_score.addCommand(new IntakeDriveCommand(3, false, 50, -1.0, false));
        center_left_score.addCommand(new ArmCommand(2, false, 100));
        center_left_score.addCommand(new DriveCommand(2, false, -40));
        center_left_score.addCommand(new TurnCommand(2.5, false, -130));
        center_left_score.addCommand(new DriveCommand(2, false, -60));
        center_left_score.addCommand(new IntakeCommand(1, 0.75, false));

        left_straight_score.addCommand(new DriveCommand(3, false, -160));
        left_straight_score.addCommand(new TurnCommand(2, false, -90));
        left_straight_score.addCommand(new DriveCommand(2, false, -40));
        left_straight_score.addCommand(new OuttakeCommand(1, 0.5));

        right_straight_score.addCommand(new DriveCommand(3, false, -160));
        right_straight_score.addCommand(new TurnCommand(2, false, 90));
        right_straight_score.addCommand(new DriveCommand(2, false, -40));
        right_straight_score.addCommand(new OuttakeCommand(1, 0.5));

        tuning.addCommand(new ArmCommand(10, true, 110));
        tuning.addCommand(new ArmCommand(0, false, 110));
        
        current_routine = straight;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller Started"); /** Eventually replace with logger */
        Subsystems.intake.setPidEnabled(false);
    }

    @Override
    public void handle() {
        /** Make sure the game data is loaded, and set the correct routine */
        while(DriverStation.getInstance().getGameSpecificMessage().length() < 3) {
            return;
        }
        game_data = DriverStation.getInstance().getGameSpecificMessage();
        Dashboard.setString("game_data", game_data);
        System.out.println(Dashboard.getString("automode"));
        switch(Dashboard.getString("automode")) {
            case "left_inner":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = straight_score;
                } else {
                    current_routine = straight;
                }
                break;
            case "left_outer":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = straight_score;
                } else {
                    current_routine = straight;
                }
                break;
            case "center":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = center_left_score;
                } else {
                    current_routine = center_right_score;
                }
                break;
            case "right_inner":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = straight;
                } else {
                    current_routine = straight_score;
                }
                break;
            case "right_outer":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = straight;
                } else {
                    current_routine = straight_score;
                }
                break;
            case "tune":
                current_routine = tuning;
                break;
            default:
                System.out.println("Automode mode not recognized");
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

        Subsystems.drivetrain.setAngle(0); // Does this work?
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
        current_command = null;
    }

}