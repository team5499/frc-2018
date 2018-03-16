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
    private Routine ro_tc, ro_oc, ro_nc, ri_oc, ri_nc, m_nc, m_oc_r, m_oc_l, m_tc_r, m_tc_l, li_oc, li_nc, lo_tc, lo_oc, lo_nc, nothing, tuning;

    private BaseCommand current_command;
    private String game_data;

    public AutoController() {
        super();
        game_data = null;
        Dashboard.setDouble("distance_setpoint", 0);
        Dashboard.setDouble("angle_setpoint", 0);

        ro_tc = new Routine();
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
        lo_tc = new Routine();
        lo_oc = new Routine();
        lo_nc = new Routine();
        nothing = new Routine();
        tuning = new Routine();

        ro_tc.addCommand(new NothingCommand(0));
        ro_tc.addCommand(new ArmCommand(1, true, 110));
        ro_tc.addCommand(new DriveCommand(3, false, -150));
        ro_tc.addCommand(new TurnCommand(2, false, 90));
        ro_tc.addCommand(new DriveCommand(1.5, false, -16));
        ro_tc.addCommand(new OuttakeCommand(1, 0.6));
        ro_tc.addCommand(new DriveCommand(1.5, false, 16));
        ro_tc.addCommand(new TurnCommand(2, false, 90));
        ro_tc.addCommand(new DriveCommand(2, false, 36));
        ro_tc.addCommand(new TurnCommand(2, false, -90));
        ro_tc.addCommand(new IntakeDriveCommand(2, false, 26, 0.5, true));
        ro_tc.addCommand(new ArmCommand(2, true, 30));
        ro_tc.addCommand(new TurnCommand(2, false, -30));
        ro_tc.addCommand(new OuttakeCommand(0.5, 1.0));

        ro_oc.addCommand(new NothingCommand(0));
        ro_oc.addCommand(new ArmCommand(1, true, 110));
        ro_oc.addCommand(new DriveCommand(3, false, -150));
        ro_oc.addCommand(new TurnCommand(2, false, 90));
        ro_oc.addCommand(new DriveCommand(1.5, false, -23));
        ro_oc.addCommand(new OuttakeCommand(1, 0.6));

        // drives 90 inches(just enough to cross baseline)
        ro_nc.addCommand(new NothingCommand(0));
        ro_nc.addCommand(new DriveCommand(3, false, -90));

        ri_oc.addCommand(new NothingCommand(0));
        ri_oc.addCommand(new ArmCommand(1, true, 110));
        ri_oc.addCommand(new DriveCommand(3, false, -104));
        ri_oc.addCommand(new OuttakeCommand(1, 0.6));

        ri_nc.addCommand(new NothingCommand(0));
        ri_nc.addCommand(new DriveCommand(3, false, -104));

        m_nc.addCommand(new NothingCommand(0));
        m_nc.addCommand(new DriveCommand(2, false, -60));

        m_oc_l.addCommand(new NothingCommand(0));
        m_oc_l.addCommand(new ArmCommand(1, true, 110));
        m_oc_l.addCommand(new DriveCommand(5, false, -45));
        m_oc_l.addCommand(new TurnCommand(2, false, 90));
        m_oc_l.addCommand(new DriveCommand(2, false, -59));
        m_oc_l.addCommand(new TurnCommand(2, false, -90));
        m_oc_l.addCommand(new DriveCommand(3, false, -63));
        m_oc_l.addCommand(new OuttakeCommand(1, 0.6));

        m_oc_r.addCommand(new NothingCommand(0));
        m_oc_r.addCommand(new ArmCommand(1, true, 110));
        m_oc_r.addCommand(new DriveCommand(5, false, -45));
        m_oc_r.addCommand(new TurnCommand(2, false, -90));
        m_oc_r.addCommand(new DriveCommand(2, false, -47));
        m_oc_r.addCommand(new TurnCommand(2, false, 90));
        m_oc_r.addCommand(new DriveCommand(3, false, -58.5));
        m_oc_r.addCommand(new OuttakeCommand(1, 0.6));

        m_tc_l.addCommand(new NothingCommand(0));
        m_tc_l.addCommand(new ArmCommand(1, true, 110));
        m_tc_l.addCommand(new DriveCommand(5, false, -45));
        m_tc_l.addCommand(new TurnCommand(2, false, 90));
        m_tc_l.addCommand(new DriveCommand(2, false, -59));
        m_tc_l.addCommand(new TurnCommand(2, false, -90));
        m_tc_l.addCommand(new DriveCommand(3, false, -63));
        m_tc_l.addCommand(new OuttakeCommand(1, 0.6));
        m_tc_l.addCommand(new DriveCommand(2, false, 63));
        m_tc_l.addCommand(new ArmCommand(1, true, -25));
        m_tc_l.addCommand(new ArmCommand(0, false, -25));
        m_tc_l.addCommand(new TurnCommand(2, false, 120));
        m_tc_l.addCommand(new IntakeDriveCommand(3.5, false, 65, -1.0, true)); // SET WAIT_FOR_CUBE to false, unless distance sensor works!
        m_tc_l.addCommand(new ArmCommand(1, true, 110));
        m_tc_l.addCommand(new DriveCommand(2, false, -65));
        m_tc_l.addCommand(new TurnCommand(2, false, -120));
        m_tc_l.addCommand(new DriveCommand(2, false, -63));
        m_tc_l.addCommand(new OuttakeCommand(1, 0.6));

        m_tc_r.addCommand(new NothingCommand(0));
        m_tc_r.addCommand(new ArmCommand(1, true, 110));
        m_tc_r.addCommand(new DriveCommand(5, false, -45));
        m_tc_r.addCommand(new TurnCommand(2, false, -90));
        m_tc_r.addCommand(new DriveCommand(2, false, -47));
        m_tc_r.addCommand(new TurnCommand(2, false, 90));
        m_tc_r.addCommand(new DriveCommand(3, false, -58.5));
        m_tc_r.addCommand(new OuttakeCommand(1, 0.6));
        m_tc_r.addCommand(new DriveCommand(2, false, 63));
        m_tc_r.addCommand(new ArmCommand(1, true, -25));
        m_tc_r.addCommand(new ArmCommand(0, false, -25));
        m_tc_r.addCommand(new TurnCommand(2, false, -120));
        m_tc_r.addCommand(new IntakeDriveCommand(3.5, false, 65, -1.0, true)); // SET WAIT_FOR_CUBE to false, unless distance sensor works!
        m_tc_r.addCommand(new ArmCommand(1, true, 110));
        m_tc_r.addCommand(new DriveCommand(2, false, -65));
        m_tc_r.addCommand(new TurnCommand(2, false, 120));
        m_tc_r.addCommand(new DriveCommand(2, false, -63));
        m_tc_r.addCommand(new OuttakeCommand(1, 0.6));

        lo_tc.addCommand(new NothingCommand(0));
        lo_tc.addCommand(new ArmCommand(1, true, 110));
        lo_tc.addCommand(new DriveCommand(3, false, -150));
        lo_tc.addCommand(new TurnCommand(2, false, -90));
        lo_tc.addCommand(new DriveCommand(1.5, false, -16));
        lo_tc.addCommand(new OuttakeCommand(1, 0.6));
        lo_tc.addCommand(new DriveCommand(1.5, false, 16));
        lo_tc.addCommand(new TurnCommand(2, false, -90));
        lo_tc.addCommand(new DriveCommand(2, false, 36));
        lo_tc.addCommand(new TurnCommand(2, false, 90));
        lo_tc.addCommand(new IntakeDriveCommand(2, false, 26, 0.5, true));
        lo_tc.addCommand(new ArmCommand(2, true, 30));
        lo_tc.addCommand(new TurnCommand(2, false, 30));
        lo_tc.addCommand(new OuttakeCommand(0.5, 1.0));

        lo_oc.addCommand(new NothingCommand(0));
        lo_oc.addCommand(new ArmCommand(1, true, 110));
        lo_oc.addCommand(new DriveCommand(3, false, -150));
        lo_oc.addCommand(new TurnCommand(2, false, -90));
        lo_oc.addCommand(new DriveCommand(1.5, false, -23));
        lo_oc.addCommand(new OuttakeCommand(1, 0.6));

        // drives 90 inches(just enough to cross baseline)
        lo_nc.addCommand(new NothingCommand(0));
        lo_nc.addCommand(new DriveCommand(3, false, -90));

        li_oc.addCommand(new NothingCommand(0));
        li_oc.addCommand(new ArmCommand(1, true, 110));
        li_oc.addCommand(new DriveCommand(3, false, -104));
        li_oc.addCommand(new OuttakeCommand(1, 0.6));

        li_nc.addCommand(new NothingCommand(0));
        li_nc.addCommand(new DriveCommand(3, false, -104));

        nothing.addCommand(new NothingCommand(0));

        tuning.addCommand(new ArmCommand(10, true, 110));
        tuning.addCommand(new ArmCommand(0, false, 110));
        
        current_routine = nothing;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller Started"); /** Eventually replace with logger */
        ro_oc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        ro_nc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        ri_oc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        ri_nc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        m_nc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        m_oc_r.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        m_oc_l.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        m_tc_r.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        m_tc_l.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        li_oc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        li_nc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        lo_oc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
        lo_nc.setCommand(0, new NothingCommand(Dashboard.getDouble("timeout")));
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
                    current_routine = li_oc;
                } else {
                    current_routine = li_nc;
                }
                break;
            case "left_outer":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = lo_oc;
                } else {
                    current_routine = lo_nc;
                }
                break;
            case "center":
                if(game_data.substring(0, 1).equals("L")) {
                    if(Dashboard.getString("cubemode").equals("one")) {
                        current_routine = m_oc_l;
                    } else if(Dashboard.getString("cubemode").equals("two")) {
                        current_routine = m_tc_l;
                    } else {
                        current_routine = m_nc;
                    }
                } else {
                    if(Dashboard.getString("cubemode").equals("one")) {
                        current_routine = m_oc_r;
                    } else if(Dashboard.getString("cubemode").equals("two")) {
                        current_routine = m_tc_r;
                    } else {
                        current_routine = m_nc;
                    }
                }
                break;
            case "right_inner":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = ri_nc;
                } else {
                    current_routine = ri_oc;
                }
                break;
            case "right_outer":
                if(game_data.substring(0, 1).equals("L")) {
                    current_routine = ro_nc;
                } else {
                    current_routine = ro_oc;
                }
                break;
            case "tune":
                current_routine = tuning;
                break;
            case "none":
                current_routine = nothing;
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

        lo_nc.reset();
        lo_oc.reset();
        li_nc.reset();
        li_oc.reset();
        m_nc.reset();
        m_oc_l.reset();
        m_oc_r.reset();
        m_tc_l.reset();
        m_tc_r.reset();
        ro_nc.reset();
        ro_oc.reset();
        ri_nc.reset();
        ri_oc.reset();
        tuning.reset();
        nothing.reset();

        game_data = null;
        current_command = null;
    }

}