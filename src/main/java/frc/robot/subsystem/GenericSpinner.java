package frc.robot.subsystem;

import frc.robot.control.single.SingleControl;
import frc.robot.hardware.motor.Motor;

public class GenericSpinner extends Subsystem {

    private final Motor motor;
    private final String name;
    public GenericSpinner(Motor motor, String name) {
        this.motor=motor;
        this.name=name;
    }
    public void periodic() {
        motor.periodic();
    }
    public void drive(SingleControl control) {
        motor.set(control.getValue());
    }
    public String getName() {
        return name;
    }

    public Motor getMotor() {
        return motor;
    }
}
