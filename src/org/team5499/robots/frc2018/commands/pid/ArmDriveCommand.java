package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.commands.timed.TimedArmCommand.Direction;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class ArmDriveCommand extends DriveCommand {

    private double speed;

    public ArmDriveCommand(double to, double setPoint, Direction dir) {
        super(to, setPoint);
        switch(dir) {
            case UP: 
                speed = Reference.getInstance().AUTO_ARM_UP_SPEED;;
                break;
            case DOWN:
                speed = Reference.getInstance().AUTO_ARM_DOWN_SPEED;
                break;
            default:
                speed = 0;
                break;
        }
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void handle() {
        super.handle();
        Subsystems.intake.setArm(speed);
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