package frc.robot.subsystem;

import frc.robot.control.single.SingleControl;
import frc.robot.hardware.motor.Motor;

public class GenericSpinner extends Subsystem {
    private final Motor motor;
    public GenericSpinner(Motor motor) {
        this.motor=motor;
    }
    public void periodic() {
        motor.periodic();
    }
    public void drive(SingleControl control) {
        motor.set(control.getValue());
    }

}
