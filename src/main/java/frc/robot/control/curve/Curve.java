package frc.robot.control.curve;

/**
 * An abstract class containing a single implementable function that turns one double into another
 */
public abstract class Curve {

    /**
     * Implementations turn one double number into another
     * @param x the input to the curved
     * @return the output, processed value of the curve
     */
    public abstract double curve(double x);
}
