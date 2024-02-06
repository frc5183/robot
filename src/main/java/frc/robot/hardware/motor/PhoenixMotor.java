package frc.robot.hardware.motor;


import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Logger;

/**
 * An abstract wrapper class for CTRE/Phoenix Library Motor Controllers
 */
public abstract class PhoenixMotor extends Motor {
    protected MotorController motor;


    /**
     * @return used to retrieve the true motor outside WPILib to use the Follow Function of CTRE
     */
    public abstract MotorController getRawMasterMotor();

    public void set(double speed) {
        motor.set(speed);
        Logger.append(Logger.LogType.HardwareMotor, "phoenix/" + getId() + "/speed", speed);
    }

    public double get() {
        return motor.get();
    }
    public boolean getInverted() {
        return motor.getInverted();
    }
}
