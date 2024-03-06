package frc.robot.math.curve;

/**
 * An implementation of Curve that utilizes an exponential curve
 */
public class ExponentialCurve extends Curve {
    private double exaggeration = 1.0;

    @Override
    public double internal_curve(double x) {
        if (x == 0) {
            return 0;
        }
        return (x / Math.abs(x)) * ((Math.pow(1 + exaggeration, Math.abs(x))) - 1) / exaggeration;
    }

    /**
     * Used to set the exponential exaggeration value
     * @param exaggeration The new exponential exaggeration value
     */
    public void setExaggeration(double exaggeration) {
        this.exaggeration = exaggeration;
    }
}
