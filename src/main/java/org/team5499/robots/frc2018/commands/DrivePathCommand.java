package org.team5499.robots.frc2018.commands;

import org.team5499.robots.frc2018.path_pursuit.Path;
import org.team5499.robots.frc2018.path_pursuit.PathFollower;
import org.team5499.robots.frc2018.path_pursuit.PathFollower.PFConfig;
import org.team5499.robots.frc2018.subsystems.Drivetrain;

public class DrivePathCommand extends BaseCommand {

    private Path path;
    private PFConfig config;

    public DrivePathCommand(double to, Path path) {
        this(to, path, new PFConfig());
    }

    public DrivePathCommand(double to, Path path, PFConfig config) {
        super(to);
        this.path = path;
        this.config = config;
    }

    @Override
    public void start() {
        super.start();
        PathFollower.getInstance().configure(path, config);
    }

    @Override
    public void handle() {
        double[] velo = PathFollower.getInstance().update(Math.toRadians(Drivetrain.getInstance().getAngle()));
        Drivetrain.getInstance().setDrivetrainVelocitySetpoints(velo[0] , velo[1]);
    }

    @Override
    public void reset() {
        PathFollower.getInstance().configure(path, config);    
        super.reset();
    }

    @Override
    public boolean isFinished() {
        return PathFollower.getInstance().getDoneWithPath() || super.isFinished();
    }

}