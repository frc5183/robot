package frc.robot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Logger;

/**
 * A wrapper class around CANSparkMax to make it compatible with other motor types
 */
public class SparkMaxMotor extends Motor {
    private final CANSparkMax motor;

    public SparkMaxMotor(int id, MotorType motortype) {
        motor = new CANSparkMax(id, motortype);

        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/id", id);
        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/type", motortype.toString());

        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId(), "New SparkMaxMotor created with motor id: " + id + " and type: " + motortype);
    }

    @Override
    public void set(double speed) {
        motor.set(speed);
        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/speed", speed);
    }

    @Override
    public void periodic() {}

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setSafety(boolean on) {}

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/inverted", inverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/disabled", true);
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
        Logger.append(Logger.LogType.HardwareMotor, "sparkMax/" + getId() + "/stopped", true);
    }

    @Override
    public MotorController getRawMotor() {
        return motor;
    }

    public CANSparkMax getTrueRawMotor() {
        return motor;
    }
}
