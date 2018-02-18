package org.team5499.robots.frc2018.commands.pid;

import org.team5499.robots.frc2018.Reference;
import org.team5499.robots.frc2018.Hardware;
import org.team5499.robots.frc2018.commands.BaseCommand;
import org.team5499.robots.frc2018.subsystems.Subsystems;

public class DriveCommand extends BaseCommand {

    private double setPoint;
    private double startAngle;
    private boolean enabled;

    public DriveCommand(double to, double setPoint) {
        super(to);
        this.setPoint = setPoint;
        this.enabled = false;
    }

    @Override
    public void start() {
        // System.out.println("Command started!");
        super.start();
        enabled = true;
        Subsystems.drivetrain.pidSet(setPoint, 0);
        Subsystems.drivetrain.pidEnable(true, true);
    }

    @Override
    public void handle() {
    //     System.out.println("Error: " + Subsystems.drivetrain.pidDistanceError());
    //     System.out.println("Encoder: " + Subsystems.drivetrain.encoder.pidGet());
        //System.out.println(Subsystems.drivetrain.pidDistanceError() + ":" + Subsystems.drivetrain.getDistanceSetpoint() + ":" + Subsystems.drivetrain.getDistanceControllerSetpoint());
    }

    @Override
    public void reset() {
        super.reset();
        enabled = false;
    }

    @Override
    public boolean isFinished() {
        boolean finished = (super.isFinished()/* || (Subsystems.drivetrain.angleOnTarget() && Subsystems.drivetrain.distanceOnTarget())*/);
        if(finished) {
            System.out.println((Subsystems.drivetrain.angleOnTarget() && Subsystems.drivetrain.distanceOnTarget()) + ":" + super.isFinished());
            Subsystems.drivetrain.pidDisable();
            reset();
        }
        return finished;
    }

}