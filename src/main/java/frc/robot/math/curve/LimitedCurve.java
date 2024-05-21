package frc.robot.math.curve;

public class LimitedCurve extends Curve {
    private final Curve curve;
    private double limit = 1.0;
    public LimitedCurve(Curve curve, double limit) {
        this.curve = curve;
        this.limit = limit;
    }
    public LimitedCurve(Curve curve) {
        this.curve = curve;
    }
    @Override
    protected double internal_curve(double x) {
        return limit*curve.curve(x);
    }
}
