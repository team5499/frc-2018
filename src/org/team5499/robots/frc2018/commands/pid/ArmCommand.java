package org.team5499.robots.frc2018.commands.pid;


import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class ArmCommand extends BaseCommand {

    public enum ArmDirection {
        UP,
        DOWN
    }

    private ArmDirection direction;
    private double setpoint;

    public ArmCommand(double to, ArmDirection direction) {
        super(to);
        this.direction = direction;
        if(this.direction == ArmDirection.UP) {
            this.setpoint = Reference.getInstance().ARM_UP_SETPOINT;
        } else {
            this.setpoint = Reference.getInstance().ARM_DOWN_SETPOINT;
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle() {
        
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public boolean isFinished() {
        return super.isFinished();
    }

}