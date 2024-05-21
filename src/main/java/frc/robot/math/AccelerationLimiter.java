package frc.robot.math;

import edu.wpi.first.math.filter.SlewRateLimiter;

public class AccelerationLimiter {
    private double last;
    private SlewRateLimiter slew;
    public AccelerationLimiter(double time) {
        slew = new SlewRateLimiter(time);
    }
    public double limit(double x) {
        if (Math.abs(x) < Math.abs(last)) {
            last=x;
            slew.reset(x);
            return x;
        }  else {
            last=slew.calculate(x);
            return slew.calculate(x);
        }

    }
}
