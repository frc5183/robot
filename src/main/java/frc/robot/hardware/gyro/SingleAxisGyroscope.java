package frc.robot.hardware.gyro;

/**
 * Represents a Single Axis Gyroscope
 * Implementations connect this with hardware
 */
public abstract class SingleAxisGyroscope {
    /**
     * @return the angle in Degrees
     */
    public abstract double getDegrees();
    /**
     * @return the angle in Radians
     */
    public abstract double getRadians();
    /**
     * @return the angle in Rotations
     */

    public abstract double getRotations();

    /**
     * Calibrates the gyroscope.
     * A heavy command that should not be run frequently.
     */
    public abstract void calibrate();

    /**
     * Resets the gyroscope back to it's 0 state
     */
    public abstract void reset();

    /**
     * Represents the Axis in a 3 axis gyroscope.
     */
    public enum Axis {
        YAW, PITCH, ROLL
    }
}
