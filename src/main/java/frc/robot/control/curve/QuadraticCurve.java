package frc.robot.control.curve;

/**
 * An Implementation of Curve that uses the Quadratic Equation of the form Ax^2 + BX + C
 */
public class QuadraticCurve extends Curve {
    private double A = 1;
    private double B = 0;

    private double intercept = 0;

    @Override
    protected double internal_curve(double x) {
        return A*(Math.pow(x, 2)) + B*x + intercept;
    }

    /**
     * Sets the A value of the Quadratic Equation
     * @param a New A
     */
    public void setA(double a) {
        A = a;
    }

    /** Sets the B value of the Quadratic Equation
     * @param b new B
     */
    public void setB(double b) {
        B = b;
    }

    /** Sets the Y-Intercept of the Quadratic Equation
     * @param intercept new intercept
     */
    public void setIntercept(double intercept) {
        this.intercept = intercept;
    }
}
