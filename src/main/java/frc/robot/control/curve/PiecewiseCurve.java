package frc.robot.control.curve;

/**
 * Am implementation that allows the combination of 2 different Curves using a limiter for left and right
 * Can create extremely complex curves by using piecewise curves within piecewise curves.
 */
public class PiecewiseCurve extends Curve {
    private Curve leftCurve;
    private Curve rightCurve;
    private double limit=0.5;
    @Override
    protected double internal_curve(double x) {
        if (x<limit) {
            return leftCurve.curve(x);
        } else {
            return rightCurve.curve(x);
        }
    }
    public PiecewiseCurve(Curve lcurve, Curve rcurve, double limit) {
        leftCurve=lcurve;
        rightCurve=rcurve;
        this.limit=limit;
    }

    public void setLeftCurve(Curve leftCurve) {
        this.leftCurve = leftCurve;
    }

    public void setRightCurve(Curve rightCurve) {
        this.rightCurve = rightCurve;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
