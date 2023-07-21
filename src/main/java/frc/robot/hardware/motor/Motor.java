package frc.robot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * An abstract classes used for building compatible wrapper classes around hardware motors
 */
public abstract class Motor implements MotorController {
    /**
     * @param speed The speed to set. Value should be between -1.0 and 1.0.
     */
    public abstract void set(double speed);

    /**
     * A method to be called every periodic cycle
     */
    public abstract void periodic();

    /**
     * @return the speed set by set()
     */
    public abstract double get();

    /**
     * THIS SHOULD ALWAYS BE SET TO TRUE APART FROM VERY RARE CIRCUMSTANCES OR IN CONTROLLED ENVIRONMENTS
     * @param on Sets the state of Motor Safety
     */
    public abstract void setSafety(boolean on);

    /**
     * @param inverted The state of inversion true is inverted.
     */
    public abstract void setInverted(boolean inverted);

    /**
     * @return the inversion state
     */
    public abstract boolean getInverted();

    /**
     * Used to retrieve the original hardware motor class when necessary
     * @return the true hardware motor
     */
    public abstract MotorController getRawMotor();
}
