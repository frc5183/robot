package frc.robot.hardware.motor;


import edu.wpi.first.wpilibj.motorcontrol.MotorController;

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
    }

    public double get() {
        return motor.get();
    }
    public boolean getInverted() {
        return motor.getInverted();
    }
}
