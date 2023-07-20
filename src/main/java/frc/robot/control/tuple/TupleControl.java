package frc.robot.control.tuple;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Tuple2;
/**
 * An abstract class representing a class that uses
 * an XBOX 360 Controller as input and will dynamically
 * return the appropriate values dynamically every time
 * that getValue() is called.
 * There is a similar class available for Doubles
 */
public abstract class TupleControl {
    protected XboxController xbox;
    /**
     * @return Tuple2<Double> Value with doubles between -1 and 1
     * Implementation returns a dynamic value between -1 and 1 based on their inputs (XboxController or Autonomous Input Methods)
     */
    public abstract Tuple2<Double> getValue();
    /**
     * Used to set the XboxController used for this TupleControl
     * May be skipped if the implementation of TupleControl does not use
     * an XboxController (for example: Autonomous Controls)
     * @param xbox The XboxController to be used for this TupleControl Instance
     * @return Returns this Instance for chaining Purposes.
     */
    public TupleControl setXboxController(XboxController xbox) {
        this.xbox = xbox;
        return this;
    }
}
