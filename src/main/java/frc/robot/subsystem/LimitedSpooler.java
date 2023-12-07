package frc.robot.subsystem;

import frc.robot.control.single.SingleControl;
import frc.robot.hardware.encoder.Encoder;
import frc.robot.hardware.motor.Motor;
import frc.robot.hardware.sensor.LimitSwitch;

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
    public void periodic() {
        spool.periodic();
        if (forwardSwitch.isClosed() && spool.get()>0) { // If the motor hits the forward switch going forward STOP movement.
            spool.set(0);
        }
        if (reverseSwitch.isClosed() && spool.get()<0) { // If the motor hits the reverse switch going backwards STOP movement
            spool.set(0);
        }
    }
    public void drive(SingleControl control) {
        double speed = control.getValue();
        if (forwardSwitch.isClosed() && spool.get()>0) { // If the motor hits the forward switch going forward STOP movement.
            spool.set(0);
        } else if (reverseSwitch.isClosed() && spool.get()<0) { // If the motor hits the reverse switch going backwards STOP movement
            spool.set(0);
        } else { // If it's not going against a switch, run it
            spool.set(speed);
        }
    }
}
