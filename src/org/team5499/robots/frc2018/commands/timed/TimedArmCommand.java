package org.team5499.robots.frc2018.commands.timed;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.subsystems.Subsystems;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.commands.pid.ArmCommand.ArmDirection;

public class TimedArmCommand extends BaseCommand {

    private ArmDirection dir;
    private double speed;

    public TimedArmCommand(double to, ArmDirection dir) {
        super(to);
        switch(dir) {
            case UP:
                speed = Reference.getInstance().AUTO_ARM_UP_SPEED;
                // .75 seconds allows it to go all the way up
                break;
            case DOWN:
                speed = Reference.getInstance().AUTO_ARM_DOWN_SPEED;
                // .5 seconds is enough to go all the way down
                break;
            default:
                speed = 0;
                break;
        }

    }

    @Override
    public void handle() {
        Subsystems.intake.setArm(speed);
    }

}