package frc.robot.control.single;

import frc.robot.Logger;
import frc.robot.control.curve.Curve;
import frc.robot.control.curve.LinearCurve;
import frc.robot.control.enumeration.StickMode;

/**
 * A single control style that uses a single axis stick
 * Uses a StickMode to determine the Stick to use
 * Allows for setting of a MaxSpeed
 * Allows for setting a Curve to curve the output
 */
public class HalfStick extends SingleControl {
    private double maxSpeed = 1;
    private Curve curve;
    private StickMode mode;

    public HalfStick(StickMode mode) {
        LinearCurve curve = new LinearCurve();
        curve.setSlope(1);
        curve.setIntercept(0);
        this.curve = curve;
        this.mode = mode;

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/curve", this.curve.getClass().getName());
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/mode", this.mode.name());

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId(), "New default HalfStick created with mode " + this.mode.name());
    }

    public HalfStick(StickMode mode, Curve curve) {
        this.curve = curve;
        this.mode = mode;

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/curve", this.curve.getClass().getName());
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/mode", this.mode.name());

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId(), "New HalfStick created with mode " + this.mode.name() + " and curve " + this.curve.getClass().getName());
    }
    public HalfStick(StickMode mode, Curve curve, double max) {
        this.curve = curve;
        this.mode = mode;
        this.maxSpeed = max;

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/curve", this.curve.getClass().getName());
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/mode", this.mode.name());
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/maxSpeed", this.maxSpeed);

        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId(), "New HalfStick created with mode " + this.mode.name() + " and curve " + this.curve.getClass().getName() + " and max speed " + this.maxSpeed);
    }


    @Override
    public double getValue() {
        return maxSpeed * curve.curve(StickMode.getStickValue(mode, xbox));
    }

    /**
     * Sets a new curve for the Stick
     * @param curve new curve
     */
    public void setCurve(Curve curve) {
        this.curve = curve;
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/curve", this.curve.getClass().getName());
    }

    /**
     * Sets a new StickMode for the Stick
     * @param mode new mode
     */
    public void setMode(StickMode mode) {
        this.mode = mode;
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/mode", this.mode.name());
    }

    /**
     * Sets a new Maximum Speed for the Stick
     * @param maxSpeed new max speed
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
        Logger.append(Logger.LogType.Control, "half-stick/" + this.getId() + "/maxSpeed", this.maxSpeed);
    }
}
