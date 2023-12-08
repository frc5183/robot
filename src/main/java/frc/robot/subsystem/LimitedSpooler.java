package frc.robot.subsystem;

import frc.robot.control.single.SingleControl;
import frc.robot.hardware.encoder.Encoder;
import frc.robot.hardware.motor.Motor;
import frc.robot.hardware.sensor.LimitSwitch;

/**
 * A class used to create a single-motor "Spool" system that has forward and reverse limit switches
 * An Encoder is required for Autonomous use
 */
public class LimitedSpooler extends Subsystem {
    private final LimitSwitch forwardSwitch;
    private final LimitSwitch reverseSwitch;
    private final Motor spool;
    private final Encoder encoder; // Encoder for autonomous use
    public LimitedSpooler(LimitSwitch forwardSwitch, LimitSwitch reverseSwitch, Motor spool, Encoder encoder) {
        this.forwardSwitch=forwardSwitch;
        this.reverseSwitch=reverseSwitch;
        this.spool=spool;
        this.encoder=encoder;
    }

    /**
     * Feeds motor safety and checks for limit switches
     */
    public void periodic() {
        spool.periodic();
        stopMotorIfNeeded(spool.get());
    }

    /** Drives the main spool motor
     * @param control SingleControl the control used for this LimitedSpooler
     */
    public void drive(SingleControl control) {
        double speed = control.getValue();
        if (stopMotorIfNeeded(speed)) return;
        spool.set(speed);
    }

    /**
     * @param speed the motor speed needing to be tested
     * @return boolean, true if motor was forced stopped, false otherwise
     */
    private boolean stopMotorIfNeeded(double speed) {
        if (forwardSwitch.isClosed() && speed>0) {
            spool.set(0);
            return true;
        }
        if (reverseSwitch.isClosed() && speed<0) {
            spool.set(0);
            return true;
        }
        return false;
    }

    /**
     * @return Encoder the encoder corresponding the spool motor
     */
    public Encoder getEncoder() {
        return encoder;
    }
}
