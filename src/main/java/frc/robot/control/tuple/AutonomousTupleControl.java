package frc.robot.control.tuple;

import frc.robot.Logger;
import frc.robot.Tuple2;

/**
 * A simple implementation of TupleControl that can be used by Commands as a way to interface with Subsystems.
 * By using the setValue()/updateValue() function family, the value outputted by getValue can be changed.
 * Commands can use this function every periodic cycle to update the value sent to the subsystem
 */
public class AutonomousTupleControl extends TupleControl {
    private Tuple2<Double> value;

    /**
     * Creates a new AutonomousTupleControl with a Tuple2 value
     * @param value1 first value
     * @param value2 second value
     */
    public AutonomousTupleControl(double value1, double value2) {
        value = new Tuple2<>(value1, value2);
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/1", value.getVal1());
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/2", value.getVal2());

        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId(), "New AutonomousTupleControl created with values " + value.getVal1() + " and " + value.getVal2());
    }

    /**
     * Creates a new AutonomousTupleControl with a Tuple2 value
     * @param value Tuple2 value
     */
    public AutonomousTupleControl(Tuple2<Double> value) {
        this.value = value;
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/1", value.getVal1());
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/2", value.getVal2());

        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId(), "New AutonomousTupleControl created with values " + value.getVal1() + " and " + value.getVal2());
    }

    @Override
    public Tuple2<Double> getValue() {
        return value;
    }

    /** Sets a new Tuple2 value
     * @param value new Value
     */
    public void setValue(Tuple2<Double> value) {
        this.value = value;
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/1", value.getVal1());
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/2", value.getVal2());
    }

    /** Changes the first value of the tuple
     * @param val new first value
     */
    public void updateValue1(double val) {
        value.setVal1(val);
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/1", value.getVal1());
    }

    /** Changes the second value of the tuple
     * @param val new second value
     */
    public void updateValue2(double val) {
        value.setVal2(val);
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/2", value.getVal2());
    }

    /** Changes both values of the tuple
     * @param val1 new first value
     * @param val2 new second value
     */
    public void updateValue(double val1, double val2) {
        value.setVal1(val1);
        value.setVal2(val2);
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/1", value.getVal1());
        Logger.append(Logger.LogType.Control, "autonomousTupleControl/" + this.getId() + "/value/2", value.getVal2());
    }
}
