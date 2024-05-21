package frc.robot.hardware.encoder;

/**
 * An abstract class that represents a 1 axis gyroscope.
 */
public abstract class Encoder {
    /**
     * @return the angle in Radians
     */
    public abstract double getUnitsRadians();

    /**
     * @return the angle in Rotations
     */
    public abstract double getUnitsRotations();

    /**
     * @return the angle in Degrees
     */
    public abstract double getUnitsDegrees();
    public abstract double getVelocityRadiansPerSecond();
    public abstract double getVelocityRotationsPerMinute();
    public abstract double getVelocityRotationsPerSecond();
    public abstract double getVelocityDegrees();

    /**
     * Resets the encoder to it's 0 state
     */
    public abstract void reset();
}
