package org.team5499.robots.frc2018.subsystems;

import org.team5499.robots.frc2018.Reference;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.ParamEnum;

public class PID {
    
    private TalonSRX talon;
    private final int PID_LOOP = 0;
    
    public PID(TalonSRX talon, double p, double i, double d, double f, double maxOut) {
        this.talon = talon;
        talonConfig(p, i, d, f, maxOut);
    }

    private void talonConfig(double p, double i, double d, double f, double maxOut) {
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_LOOP, Reference.mTimeout);
        talon.configRemoteFeedbackFilter(Reference.PIGEON_PORT, RemoteSensorSource.Pigeon_Yaw, 0, Reference.mTimeout);
        talon.configNominalOutputForward(0, Reference.mTimeout);
        talon.configNominalOutputReverse(0, Reference.mTimeout);
        talon.configPeakOutputForward(maxOut, Reference.mTimeout);
        talon.configPeakOutputReverse(-maxOut, Reference.mTimeout);

        talon.config_kP(PID_LOOP, p, Reference.mTimeout);
        talon.config_kI(PID_LOOP, i, Reference.mTimeout);
        talon.config_kD(PID_LOOP, d, Reference.mTimeout);
        talon.config_kF(PID_LOOP, f, Reference.mTimeout);
    }

    public void setPID(double p, double i, double d, double f) {
        talon.config_kP(PID_LOOP, p, Reference.mTimeout);
        talon.config_kI(PID_LOOP, i, Reference.mTimeout);
        talon.config_kD(PID_LOOP, d, Reference.mTimeout);
        talon.config_kF(PID_LOOP, f, Reference.mTimeout);
    }

    public void setMaxOutput(double maxOut) {
        talon.configPeakOutputForward(maxOut, Reference.mTimeout);
        talon.configPeakOutputReverse(-maxOut, Reference.mTimeout);
    }

    /**
     * gets error from the PID loop
     * @return error value as double
     */
    public double getError() {
        return talon.getClosedLoopError(PID_LOOP);
    }

    public void reset() {
        resetEncoder();
    }

    private void resetEncoder() {
        talon.setSelectedSensorPosition(0, PID_LOOP, Reference.mTimeout);
    }

    public int getEncoderData() {
        return talon.getSensorCollection().getQuadraturePosition();
    }


}