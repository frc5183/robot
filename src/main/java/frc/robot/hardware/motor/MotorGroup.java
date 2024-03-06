package frc.robot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * A class used to virtually combine two motors.
 * Useful for motors which share a gearbox such as on a tank or WCD drivetrain
 */
public class MotorGroup extends Motor {
    public final Motor motor1;
    public final Motor motor2;

    public MotorGroup(Motor motor1, Motor motor2) {
        this.motor1 = motor1;
        this.motor2 = motor2;
    }

    @Override
    public void set(double speed) {
        motor1.set(speed);
        motor2.set(speed);
    }

    @Override
    public void setVoltage(double outputVolts) {
        motor1.setVoltage(outputVolts);
        motor2.setVoltage(outputVolts);
    }

    @Override
    public void periodic() {
        motor1.periodic();
        motor2.periodic();
    }

    @Override
    public double get() {
        return motor1.get();
    }

    @Override
    public void setSafety(boolean on) {
        motor1.setSafety(on);
        motor2.setSafety(on);
    }

    @Override
    public void setInverted(boolean inverted) {
        motor1.setInverted(inverted);
        motor2.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return motor1.getInverted();
    }

    @Override
    public void disable() {
        motor1.disable();
        motor2.disable();
    }

    @Override
    public void stopMotor() {
        motor1.stopMotor();
        motor2.stopMotor();
    }

    /**
     * @return null, as there is no single motor controller
     * @throws RuntimeException if this method is called
     */
    @Override
    public MotorController getRawMotor() {
        throw new RuntimeException("Cannot Retrieve Single MotorController from MotorGroup. Use MotorGroup.motor1 and MotorGroup.motor2 instead.");
    }
}
