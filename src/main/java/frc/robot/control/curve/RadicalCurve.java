package frc.robot.control.curve;

/**
 * Implements Curve with a radical equation of the form y=(x+c))^(1/a)+b
 */
public class RadicalCurve extends Curve {
    private double strength=2.0;
    private double intercept = 0.0;
    private double xintercept = 0.0;
    @Override
    protected double internal_curve(double x) {
        return Math.pow(x-xintercept, 1/strength)+intercept;
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

    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }

    public void setXIntercept(double xintercept) {
        this.xintercept = xintercept;
    }
}
