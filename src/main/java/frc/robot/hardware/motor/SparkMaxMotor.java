package frc.robot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.hardware.encoder.NEOEncoder;

/**
 * A wrapper class around CANSparkMax to make it compatible with other motor types
 */
public class SparkMaxMotor extends Motor {
    private final CANSparkMax motor;

    public SparkMaxMotor(int id, MotorType motortype) {
        motor = new CANSparkMax(id, motortype);
    }

    @Override
    public void set(double speed) {
        motor.set(speed);
    }

    @Override
    public void periodic() {
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setSafety(boolean on) {
    }

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }
    public EncodedMotor getEncodedMotor() {
        return new EncodedMotor(this, new NEOEncoder(this));
    }

    @Override
    public MotorController getRawMotor() {
        return motor;
    }

    public CANSparkMax getTrueRawMotor() {
        return motor;
    }
}
