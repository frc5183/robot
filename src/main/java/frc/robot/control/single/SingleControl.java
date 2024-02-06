package frc.robot.control.single;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Logger;

import java.util.UUID;

/**
 * An abstract class representing a class that uses
 * an XBOX 360 Controller as input and will dynamically
 * return the appropriate values dynamically every time
 * that getValue() is called.
 * There is a similar class available for Tuple2\<Double\>
 */
public abstract class SingleControl {
    private final UUID id = UUID.randomUUID();

    /**
     * Gets the ID of the SingleControl
     * @return ID of the SingleControl
     */
    public UUID getId() {
        return id;
    }

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
        Logger.append(Logger.LogType.Control, "singleControl/" + this.id + "/xbox", xbox.getPort());
        return this;
    }

    /**
     * @return double value between -1 and 1
     * Implementation return a dynamic value between -1 and 1 based on their inputs (XboxController or Autonomous Input Methods)
     */
    public abstract double getValue();
}
