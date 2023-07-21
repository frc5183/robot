package frc.robot.control.tuple;

import frc.robot.Tuple2;
import frc.robot.control.single.SingleControl;

/**
 * An implementation of TupleControl that can be used to
 * Combine 2 different SingleControls, one for each value
 * of the Tuple.
 */
public class CombinedTuple extends TupleControl {
    private SingleControl xStyle;
    private SingleControl yStyle;

    /**
     * Creates a new CombinedTuple
     * @param xStyle The SingleControl to be used for the X value of the Tuple
     * @param yStyle The SingleControl to be used for the Y value of the Tuple
     */
    public CombinedTuple(SingleControl xStyle, SingleControl yStyle) {
        this.xStyle = xStyle;
        this.yStyle = yStyle;
    }

    @Override
    public Tuple2<Double> getValue() {
        return new Tuple2<>(xStyle.getValue(), yStyle.getValue());
    }

    /** Sets the X Single Control
     * @param xStyle new X Single Control
     */
    public void setXStyle(SingleControl xStyle) {
        this.xStyle = xStyle;
    }

    /** Sets the Y Single Control
     * @param yStyle new Y Single Control
     */
    public void setYStyle(SingleControl yStyle) {
        this.yStyle = yStyle;
    }
}
