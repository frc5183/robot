package frc.robot.control.single;

import frc.robot.Logger;

/**
 * A simple implementation of SingleControl that can be used by Commands as a way to interface with Subsystems.
 * By using the setValue() function, the value outputted by getValue can be changed.
 * Commands can use this function every periodic cycle to update the value sent to the subsystem
 */
public class AutonomousSingleControl extends SingleControl {
    private double value;

    public AutonomousSingleControl(double value) {
        this.value=value;

        Logger.append(Logger.LogType.Control, "autonomous-single-control/" + this.getId() + "/value", this.value);

        Logger.append(Logger.LogType.Control, "autonomous-single-control/"  + this.getId(), "New AutonomousSingleControl created with value " + value);
    }

    /**
     * Sets the value to be output by getValue()
     * @param value the new value to be output by getValue()
     */
    public void setValue(double value) {
        this.value=value;

        Logger.append(Logger.LogType.Control, "autonomous-single-control/" + this.getId() + "/value", this.value);
    }

    public double getValue() {
        return value;
    }
}
