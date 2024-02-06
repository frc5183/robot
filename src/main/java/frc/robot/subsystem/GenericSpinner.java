package frc.robot.subsystem;

import frc.robot.Logger;
import frc.robot.control.single.SingleControl;
import frc.robot.hardware.motor.Motor;

public class GenericSpinner extends Subsystem {
    private final Motor motor;
    public GenericSpinner(Motor motor) {
        this.motor=motor;

        Logger.append(Logger.LogType.Subsystems, "spinner/" + this.getId() + "/motor", this.motor.getId().toString());

        Logger.append(Logger.LogType.Subsystems, "spinner/" + this.getId(), "New Spinner created with motor " + this.motor.getId());
    }
    public void periodic() {
        motor.periodic();
    }
    public void drive(SingleControl control) {
        motor.set(control.getValue());
    }

}
