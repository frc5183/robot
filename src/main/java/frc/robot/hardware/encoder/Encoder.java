package frc.robot.hardware.encoder;

import java.util.UUID;

/**
 * An abstract class that represents a 1 axis gyroscope.
 */
public abstract class Encoder {
    private final UUID id = UUID.randomUUID();

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

    /**
     * Resets the enocder to it's 0 state
     */
    public abstract void reset();

    /**
     * Gets the ID of the Encoder
     * @return ID of the Encoder
     */
    public UUID getId() {
        return id;
    }
}
