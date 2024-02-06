package frc.robot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Logger;

/**
 * A wrapper class around Spark to make it compatible with other motor types
 */
public class SparkMotor extends Motor{
    private final Spark sparkMotor;

    public SparkMotor(int port) {
        sparkMotor = new Spark(port);

        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/port", port);

        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId(), "New SparkMotor created with port: " + port);
    }

    public void set(double speed) {
        sparkMotor.set(speed);
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/speed", speed);
    }

    @Override
    public void setVoltage(double outputVolts) {
        sparkMotor.setVoltage(outputVolts);
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/outVoltage", outputVolts);
    }

    public void periodic() {
        sparkMotor.feed();
    }

    public double get() {
        return sparkMotor.get();
    }

    public void setSafety(boolean on) {
        sparkMotor.setSafetyEnabled(on);
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/safety", on);
    }

    public void setInverted(boolean inverted) {
        sparkMotor.setInverted(inverted);
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/inverted", inverted);
    }

    public boolean getInverted() {
        return sparkMotor.getInverted();
    }

    @Override
    public void stopMotor() {
        sparkMotor.disable();
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/stopped", true);
    }

    @Override
    public void disable() {
        sparkMotor.disable();
        Logger.append(Logger.LogType.HardwareMotor, "spark/" + getId() + "/disabled", true);
    }

    public Spark getRawMotor() {
        return sparkMotor;
    }
}
