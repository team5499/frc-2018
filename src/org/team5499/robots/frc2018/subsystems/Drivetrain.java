package src.org.team5499.robots.frc2018.subsystems;

public class Drivetrain {

    private TalonSRX left1, left2, right1, right2;

    public Drivetrain() {
        left1 = new TalonSRX(0);
        left2 = new TalonSRX(1);
        right1 = new TalonSRX(2);
        right2 = new TalonSRX(3);
        right1.setInverted(true);
        right2.setInverted(true);

    }



}