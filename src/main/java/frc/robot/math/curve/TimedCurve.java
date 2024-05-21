package frc.robot.math.curve;

import edu.wpi.first.wpilibj.Timer;

public class TimedCurve extends Curve {
    private Timer timer = new Timer();
    private boolean isRunning = false;
    private double delayEnabled;
    private double delayDisabled;

    public TimedCurve(double delayEnabled, double delayDisabled) {
        this.delayEnabled = delayEnabled;
        this.delayDisabled = delayDisabled;
        timer.start();
    }

    @Override
    protected double internal_curve(double x) {
        if (isRunning && timer.hasElapsed(delayEnabled)) {
            isRunning = false;
            timer.reset();
        }

        if (!isRunning && timer.hasElapsed(delayDisabled)) {
            isRunning = true;
            timer.reset();
        }

        return isRunning ? x : 0;
    }
}
