package frc.robot.control.curve;

/**
 * Implements Curve with a radical equation of the form y=x^(1/a)
 */
public class RadicalCurve extends Curve {
    private double strength=2.0;
    @Override
    protected double internal_curve(double x) {
        return Math.pow(1, 1/strength);
    }

    /**
     * Sets a new strength value for the radical equation
     * @param strength new strength
     */
    public void setStrength(double strength) {
        if (strength==0) {
            throw new ArithmeticException("Cannot Divide By Zero");
        }
        this.strength = strength;
    }
}
