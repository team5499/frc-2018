package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.PID;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class TurnCommand extends BaseCommand {
    private double setpoint;
    private boolean enabled;
    private PID turn_controller;

    public TurnCommand(double to, double setPoint) {
        super(to);
        this.setpoint = setPoint;
        this.enabled = false;
        this.turn_controller = new PID(Dashboard.getDouble("kTURN_P"), Dashboard.getDouble("kTURN_I"), Dashboard.getDouble("kTURN_D"));
        this.turn_controller.setInverted(false);
        this.turn_controller.setAcceptableError(Dashboard.getDouble("ACCEPTABLE_TURN_ERROR"));
        this.turn_controller.setAcceptableVelocity(Dashboard.getDouble("ACCEPTABLE_TURN_VELOCITY"));
        this.turn_controller.setOutputRange(-1, 1);
        this.turn_controller.setSetpoint(setpoint);
    }

    @Override
    public void start() {
        super.start();
        Subsystems.drivetrain.enableEncoder(false);
        enabled = true;
    }

    @Override
    public void handle() {
        turn_controller.setProcessVariable(Subsystems.drivetrain.getAngle());
        turn_controller.setVelocity(Subsystems.drivetrain.getDistanceVelocity());
        double output = turn_controller.calculate();
        Subsystems.drivetrain.setDrivetrain(output, -output);
    }

    @Override
    public void reset() {
        super.reset();
        turn_controller.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished() || turn_controller.onTarget());
        if(finished) {
            Subsystems.drivetrain.stop();
            Subsystems.drivetrain.enableEncoder(true);
            reset();
        }
        return finished;
    }
}