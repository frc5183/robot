package frc.robot.control.curve;

/**
 * An abstract class containing a single implementable function that turns one double into another
 */
public abstract class Curve {
    /**
     * Represents the value that under which inputs should be ignored and return 0
     * This is to prevent "stick drift"
     */
    private double deadzone = 0.09;

    /**
     * Implementations turn one double number into another
     * @param x the input to the curved
     * @return the output, processed value of the curve
     */
    protected abstract double internal_curve(double x);

    /**
     * Returns an implementation's curve while also accounting for a deadzone
     * @param x the input to be curved
     * @return the output, processed value of the curve
     */
    public double curve(double x) {
        return (Math.abs(x) < deadzone) ? internal_curve(0) : internal_curve(x);
    }

    /**
     * Sets the deadzone of the controller
     * @param deadzone the new deadzone of the controller
     */
    public void setDeadzone(double deadzone) {
        this.deadzone = deadzone;
    }
}
