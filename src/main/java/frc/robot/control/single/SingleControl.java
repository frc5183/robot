package frc.robot.control.single;

import edu.wpi.first.wpilibj.XboxController;

/**
 * An abstract class representing a class that uses
 * an XBOX 360 Controller as input and will dynamically
 * return the appropriate values dynamically every time
 * that getValue() is called.
 * There is a similar class available for Tuple2\<Double\>
 */
public abstract class SingleControl {
    protected XboxController xbox;

    /**
     * Used to set the XboxController used for this SingleController
     * May be skipped if the implementation of SingleControl does not use
     * an XboxController (for example: Autonomous Controls)
     * @param xbox The XboxController to be used for this SingleControl Instance
     * @return Returns this Instance for chaining Purposes.
     */
    public SingleControl setXboxController(XboxController xbox) {
        this.xbox=xbox;
        return this;
    }

    /**
     * @return double value between -1 and 1
     * Implementation return a dynamic value between -1 and 1 based on their inputs (XboxController or Autonomous Input Methods)
     */
    public abstract double getValue();
}
