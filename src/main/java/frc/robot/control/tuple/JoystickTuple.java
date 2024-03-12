package frc.robot.control.tuple;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Tuple2;

public class JoystickTuple extends TupleControl{
    private Joystick stick;
    public JoystickTuple(Joystick stick) {
        this.stick = stick;
    }
    @Override
    public Tuple2<Double> getValue() {
        return new Tuple2<>(stick.getMagnitude(), stick.getDirectionRadians());
    }
}
