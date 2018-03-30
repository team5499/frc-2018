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

    /**
     * RO - Right outer
     * RI - Right inner
     * M  - Middle
     * LI - Left inner
     * LO - Left outer
     * 
     * C - Cross Over
     * 
     * NC - No cubes
     * OC - One cube(default)
     * TC - Two cube
     */
    private Routine ro_tc, ro_oc, ro_nc, ro_c_oc, ri_oc, ri_nc, m_nc, m_oc_r, m_oc_l, m_tc_r, m_tc_l, li_oc, li_nc, lo_tc, lo_oc, lo_nc, lo_c_oc, baseline, nothing, tuning;

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
        ro_c_oc = new Routine();
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
        lo_c_oc = new Routine();
        baseline = new Routine();
        nothing = new Routine();
        tuning = new Routine();

        ro_tc.addCommand(new NothingCommand(0));
        ro_tc.addCommand(new ArmCommand(1, true, true, 110));
        ro_tc.addCommand(new DriveCommand(3, false, -150));
        ro_tc.addCommand(new TurnCommand(2, false, 90));
        ro_tc.addCommand(new DriveCommand(1.5, false, -16));
        ro_tc.addCommand(new ArmCommand(1, true, false, 110));
        ro_tc.addCommand(new OuttakeCommand(1, 0.6));
        ro_tc.addCommand(new DriveCommand(1.5, false, 16));
        ro_tc.addCommand(new TurnCommand(2, false, 90));
        ro_tc.addCommand(new DriveCommand(2, false, 36));
        ro_tc.addCommand(new TurnCommand(2, false, -90));
        ro_tc.addCommand(new IntakeDriveCommand(2, false, 26, 0.5, true));
        ro_tc.addCommand(new ArmCommand(2, true, true, 30));
        ro_tc.addCommand(new TurnCommand(2, false, -30));
        ro_tc.addCommand(new ArmCommand(2, true, false, 30));
        ro_tc.addCommand(new OuttakeCommand(0.5, 1.0));


        ro_oc.addCommand(new ArmCommand(0, true, true, 110));
        ro_oc.addCommand(new DriveCommand(3, false, -150));
        ro_oc.addCommand(new TurnCommand(2, false, 90));
        ro_oc.addCommand(new DriveCommand(1.5, false, -30));
        ro_oc.addCommand(new DriveSlowCommand(1, false, -10));
        ro_oc.addCommand(new ArmCommand(0, true, false, 110));
        ro_oc.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        // drives 90 inches(just enough to cross baseline)
        ro_nc.addCommand(new NothingCommand(0));
        ro_nc.addCommand(new DriveCommand(3, false, -90));

        ro_c_oc.addCommand(new NothingCommand(0));

        ri_oc.addCommand(new NothingCommand(0));
        ri_oc.addCommand(new ArmCommand(0, true, true, 110));
        ri_oc.addCommand(new DriveCommand(3, false, -104));
        ri_oc.addCommand(new ArmCommand(0, true, false, 110));
        ri_oc.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        ri_nc.addCommand(new NothingCommand(0));
        ri_nc.addCommand(new DriveCommand(3, false, -104));

        m_nc.addCommand(new NothingCommand(0));
        m_nc.addCommand(new DriveCommand(2, false, -60));

        m_oc_l.addCommand(new NothingCommand(0));
        m_oc_l.addCommand(new ArmCommand(0, true, true, 110));
        m_oc_l.addCommand(new DriveCommand(5, false, -45));
        m_oc_l.addCommand(new TurnCommand(2, false, 90));
        m_oc_l.addCommand(new DriveCommand(2, false, -59));
        m_oc_l.addCommand(new TurnCommand(2, false, -90));
        m_oc_l.addCommand(new DriveCommand(1.5, false, -60));
        m_oc_l.addCommand(new DriveSlowCommand(1, false, -10));
        m_oc_l.addCommand(new ArmCommand(0, true, false, 110));
        m_oc_l.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        m_oc_r.addCommand(new NothingCommand(0));
        m_oc_r.addCommand(new ArmCommand(0, true, true, 110));
        m_oc_r.addCommand(new DriveCommand(5, false, -45));
        m_oc_r.addCommand(new TurnCommand(2, false, -90));
        m_oc_r.addCommand(new DriveCommand(2, false, -47));
        m_oc_r.addCommand(new TurnCommand(2, false, 90));
        m_oc_r.addCommand(new DriveCommand(1.5, false, -60));
        m_oc_r.addCommand(new DriveSlowCommand(1, false, -10));
        m_oc_r.addCommand(new ArmCommand(0, true, false, 110));
        m_oc_r.addCommand(new OuttakeDriveCommand(1, true, 0.6));




        m_tc_l.addCommand(new NothingCommand(0));
        m_tc_l.addCommand(new ArmCommand(0, true, true, 110));
        m_tc_l.addCommand(new DriveCommand(2, false, -45));
        m_tc_l.addCommand(new TurnCommand(2, false, 90));
        m_tc_l.addCommand(new DriveCommand(2, false, -59));
        m_tc_l.addCommand(new TurnCommand(2, false, -90));
        m_tc_l.addCommand(new DriveCommand(1.5, false, -60));
        m_tc_l.addCommand(new DriveSlowCommand(1, false, -10));
        m_tc_l.addCommand(new ArmCommand(0, true, false, 110));
        m_tc_l.addCommand(new OuttakeDriveCommand(0.25, true, 0.6));
        // Two cube section
        m_tc_l.addCommand(new DriveCommand(2, false, 60));
        m_tc_l.addCommand(new ArmCommand(0.5, true, true, -50));
        m_tc_l.addCommand(new ArmCommand(0, false, true, -50));
        m_tc_l.addCommand(new TurnCommand(2, false, 135));
        m_tc_l.addCommand(new ArmCommand(0, false, false, -25));
        m_tc_l.addCommand(new DriveCommand(1, false, 30));
        m_tc_l.addCommand(new IntakeDriveCommand(2.0, false, 35, -1.0, false));
        m_tc_l.addCommand(new ArmCommand(0.5, true, true, 110));
        m_tc_l.addCommand(new DriveCommand(2, false, -55));
        m_tc_l.addCommand(new TurnCommand(2, false, -135));
        m_tc_l.addCommand(new DriveCommand(2, false, -65));
        m_tc_l.addCommand(new DriveSlowCommand(0.5, false, -10));
        m_tc_l.addCommand(new ArmCommand(0, true, false, 110));
        m_tc_l.addCommand(new OuttakeDriveCommand(1, true, 0.4));



        m_tc_r.addCommand(new NothingCommand(0));
        m_tc_r.addCommand(new ArmCommand(0, true, true, 110));
        m_tc_r.addCommand(new DriveCommand(2, false, -45));
        m_tc_r.addCommand(new TurnCommand(2, false, -90));
        m_tc_r.addCommand(new DriveCommand(2, false, -47));
        m_tc_r.addCommand(new TurnCommand(2, false, 90));
        m_tc_r.addCommand(new DriveCommand(1.5, false, -60));
        m_tc_r.addCommand(new DriveSlowCommand(1, false, -10));
        m_tc_r.addCommand(new ArmCommand(0, true, false, 110));
        m_tc_r.addCommand(new OuttakeDriveCommand(0.25, true, 0.6));
        // Two cube section
        m_tc_r.addCommand(new DriveCommand(2, false, 60));
        m_tc_r.addCommand(new ArmCommand(0.5, true, true, -50));
        m_tc_r.addCommand(new ArmCommand(0, false, true, -50));
        m_tc_r.addCommand(new TurnCommand(2, false, -135));
        m_tc_r.addCommand(new ArmCommand(0, false, false, -25));
        m_tc_r.addCommand(new DriveCommand(1, false, 30));
        m_tc_r.addCommand(new IntakeDriveCommand(2.0, false, 35, -1.0, false));
        m_tc_r.addCommand(new ArmCommand(0.5, true, true, 110));
        m_tc_r.addCommand(new DriveCommand(2, false, -55));
        m_tc_r.addCommand(new TurnCommand(2, false, 135));
        m_tc_r.addCommand(new DriveCommand(2, false, -65));
        m_tc_r.addCommand(new DriveSlowCommand(0.5, false, -10));
        m_tc_r.addCommand(new ArmCommand(0, true, false, 110));
        m_tc_r.addCommand(new OuttakeDriveCommand(1, true, 0.4));

        lo_tc.addCommand(new NothingCommand(0));
        lo_tc.addCommand(new ArmCommand(1, true, true, 110));
        lo_tc.addCommand(new DriveCommand(3, false, -150));
        lo_tc.addCommand(new TurnCommand(2, false, -90));
        lo_tc.addCommand(new DriveCommand(1.5, false, -16));
        lo_tc.addCommand(new ArmCommand(1, true, false, 110));
        lo_tc.addCommand(new OuttakeDriveCommand(1, true, 0.6));
        lo_tc.addCommand(new DriveCommand(1.5, false, 16));
        lo_tc.addCommand(new TurnCommand(2, false, -90));
        lo_tc.addCommand(new DriveCommand(2, false, 36));
        lo_tc.addCommand(new TurnCommand(2, false, 90));
        lo_tc.addCommand(new IntakeDriveCommand(2, false, 26, 0.5, true));
        lo_tc.addCommand(new ArmCommand(2, true, true, 30));
        lo_tc.addCommand(new TurnCommand(2, false, 30));
        lo_tc.addCommand(new ArmCommand(2, true, false, 30));
        lo_tc.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        lo_oc.addCommand(new NothingCommand(0));
        lo_oc.addCommand(new ArmCommand(0, true, true, 110));
        lo_oc.addCommand(new DriveCommand(3, false, -150));
        lo_oc.addCommand(new TurnCommand(1.5, false, -90));
        lo_oc.addCommand(new DriveCommand(1.5, false, -23));
        lo_oc.addCommand(new ArmCommand(0, true, false, 110));
        lo_oc.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        // drives 90 inches(just enough to cross baseline)
        lo_nc.addCommand(new NothingCommand(0));
        lo_nc.addCommand(new DriveCommand(3, false, -90));

        li_oc.addCommand(new NothingCommand(0));
        li_oc.addCommand(new ArmCommand(0, true, true, 110));
        li_oc.addCommand(new DriveCommand(3, false, -104));
        li_oc.addCommand(new ArmCommand(0, true, false, 110));
        li_oc.addCommand(new OuttakeDriveCommand(1, true, 0.6));

        li_nc.addCommand(new NothingCommand(0));
        li_nc.addCommand(new DriveCommand(3, false, -104));

        baseline.addCommand(new ArmCommand(0, true, false, 110));
        baseline.addCommand(new DriveCommand(3, false, 106));

        nothing.addCommand(new NothingCommand(0));

        tuning.addCommand(new ArmCommand(1, true, true, 110));
        tuning.addCommand(new ArmCommand(0, true, false, 110));
        tuning.addCommand(new IntakeDriveCommand(10, false, 200, -1, true));
        tuning.addCommand(new NothingCommand(10));


        
        current_routine = nothing;
    }

    @Override
    public void start() {
        System.out.println("Auto Controller Started"); /** Eventually replace with logger */
    }

    @Override
    public void handle() {
        /** Make sure the game data is loaded, and set the correct routine */
        while(DriverStation.getInstance().getGameSpecificMessage() == null || DriverStation.getInstance().getGameSpecificMessage().length() < 3) {
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
            case "baseline":
                current_routine = baseline;
                break;
            case "tune":
                current_routine = tuning;
                break;
            case "none":
                current_routine = nothing;
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