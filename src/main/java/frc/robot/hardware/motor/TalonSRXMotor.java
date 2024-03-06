package frc.robot.hardware.motor;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class TalonSRXMotor extends Motor {
    WPI_TalonSRX motor;
    public TalonSRXMotor(int port) {
        motor = new WPI_TalonSRX(port);
    }
    @Override
    public void set(double speed) {
        motor.set(TalonSRXControlMode.PercentOutput, speed);
    }

    @Override
    public void periodic() {
        motor.feed();
    }

    @Override
    public double get() {
        return motor.getMotorOutputPercent();
    }

    @Override
    public void setSafety(boolean on) {
        motor.setSafetyEnabled(on);
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

    @Override
    public MotorController getRawMotor() {
        return motor;
    }
}
