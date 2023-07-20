package frc.robot.hardware.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

/**
 * An abstract wrapper class for CTRE/Phoenix Library Motor Controllers
 */
public abstract class PhoenixMotor extends Motor {
    protected BaseMotorController motor;

    public void follow(PhoenixMotor master, InvertType inverted) {
        motor.follow(master.getRawMasterMotor());
        motor.setInverted(inverted);
    }

    /**
     * @return used to retrieve the true motor outside WPILib to use the Follow Function of CTRE
     */
    public abstract BaseMotorController getRawMasterMotor();

    /** Used to set the Deadband of CTRE motor Controllers
     * @param deadband deadband amount
     */
    public void setDeadband(double deadband) {
        motor.configNeutralDeadband(deadband);
    }
    public void set(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public double get() {
        return motor.getMotorOutputPercent();
    }
    public boolean getInverted() {
        return motor.getInverted();
    }
}
