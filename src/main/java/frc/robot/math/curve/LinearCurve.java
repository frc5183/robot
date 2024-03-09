package frc.robot.math.curve;

/**
 * An implementation of curve using the linear equation y=mx+b
 */
public class LinearCurve extends Curve{
    private double slope;
    private double intercept;

    @Override
    protected double internal_curve(double x) {
        return (slope * x) + intercept;
    }

    /**
     * Sets the slope of the linear equation
     * @param slope the new slope
     */
    public LinearCurve setSlope(double slope) {
        this.slope = slope;
        return this;
    }

    /**
     * Sets the Y-Intercept of the linear equation
     * @param intercept the new intercept
     */
    public LinearCurve setIntercept(double intercept) {
        this.intercept = intercept;
        return this;
    }
}
