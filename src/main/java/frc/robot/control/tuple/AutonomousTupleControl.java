package frc.robot.control.tuple;

import frc.robot.Tuple2;

/**
 * A simple implementation of TupleControl that can be used by Commands as a way to interface with Subsystems.
 * By using the setValue()/updateValue() function family, the value outputted by getValue can be changed.
 * Commands can use this function every periodic cycle to update the value sent to the subsystem
 */
public class AutonomousTupleControl extends TupleControl{
    private Tuple2<Double> value;
    @Override
    public Tuple2<Double> getValue() {
        return value;
    }
    public AutonomousTupleControl(double value1, double value2) {
        value = new Tuple2<>(value1, value2);
    }
    public AutonomousTupleControl(Tuple2<Double> value) {
        this.value=value;
    }

    /** Sets a new Tuple2 value
     * @param value new Value
     */
    public void setValue(Tuple2<Double> value) {
        this.value = value;
    }

    /** Changes the first value of the tuple
     * @param val new first value
     */
    public void updateValue1(double val) {
        value.setVal1(val);
    }

    /** Changes the second value of the tuple
     * @param val new second value
     */
    public void updateValue2(double val) {
        value.setVal2(val);
    }

    /** Changes both values of the tuple
     * @param val1 new first value
     * @param val2 new second value
     */
    public void updateValue(double val1, double val2) {
        value.setVal1(val1);
        value.setVal2(val2);
    }
}
