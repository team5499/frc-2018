package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.dashboard.Dashboard;
import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class TurnCommand extends BaseCommand {
    private double setpoint;
    private boolean enabled;
    private double initial_distance;
    private PID turn_controller;
    private boolean wait_for_timeout;

    public TurnCommand(double to, boolean wait_for_timeout, double setpoint) {
        super(to);
        this.setpoint = setpoint;
        this.enabled = false;
        this.wait_for_timeout = wait_for_timeout;
        this.initial_distance = 0;
        this.turn_controller = new PID(Dashboard.getDouble("kTURN_P"), Dashboard.getDouble("kTURN_I"), Dashboard.getDouble("kTURN_D"));
        this.turn_controller.setInverted(false);
        this.turn_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_TURN_ERROR"));
        this.turn_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_TURN_VELOCITY"));
        this.turn_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));
    }

    @Override
    public void start() {
        super.start();

        turn_controller.setP(Dashboard.getDouble("kTURN_P"));
        turn_controller.setI(Dashboard.getDouble("kTURN_I"));
        turn_controller.setD(Dashboard.getDouble("kTURN_D"));

        turn_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_TURN_ERROR"));
        turn_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_TURN_VELOCITY"));
        turn_controller.setOutputRange(-Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"), Dashboard.getDouble("MAX_ANGLE_PID_OUTPUT"));

        initial_distance = Subsystems.drivetrain.getDistance();

        Dashboard.setDouble("angle_setpoint", Dashboard.getDouble("angle_setpoint") + setpoint);
        turn_controller.setSetpoint(Dashboard.getDouble("angle_setpoint"));
        turn_controller.setProcessVariable(Subsystems.drivetrain.getAngle());
        enabled = true;
    }

    @Override
    public void handle() {
        turn_controller.setProcessVariable(Subsystems.drivetrain.getAngle());
        turn_controller.setVelocity(Subsystems.drivetrain.getAngleVelocity());
        double output = turn_controller.calculate();
        Subsystems.drivetrain.setDrivetrain(-output, output);
        System.out.println(turn_controller.getError()); // Debugging purposes
        Dashboard.setDouble("angle_error", turn_controller.getError());
    }

    @Override
    public void reset() {
        super.reset();
        turn_controller.reset();
        enabled = false;
        initial_distance = 0;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || (turn_controller.onTarget() && !wait_for_timeout));
        if(finished) {
            System.out.println(turn_controller.getError()); // Debugging purposes
            Subsystems.drivetrain.stop();
            Dashboard.setDouble("distance_setpoint", Dashboard.getDouble("distance_setpoint") + Subsystems.drivetrain.getDistance() - initial_distance);
            reset();
        }
        return finished;
    }
}