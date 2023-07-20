package frc.robot.control.tuple;

import frc.robot.Tuple2;
import frc.robot.control.single.SingleControl;

/**
 * An implementation of TupleControl that can be used to
 * Combine 2 different SingleControls, one for each value
 * of the Tuple.
 */
public class CombinedTuple extends TupleControl{
    private SingleControl xStyle;
    private SingleControl yStyle;
    @Override
    public Tuple2<Double> getValue() {
        return new Tuple2<>(xStyle.getValue() , yStyle.getValue());
    }
    public CombinedTuple(SingleControl xStyle, SingleControl yStyle) {
        this.xStyle=xStyle;
        this.yStyle=yStyle;
    }

    /** Sets the X Single Control
     * @param xStyle new X Single Control
     */
    public void setxStyle(SingleControl xStyle) {
        this.xStyle = xStyle;
    }

    /** Sets the Y Single Control
     * @param yStyle new Y Single Control
     */
    public void setyStyle(SingleControl yStyle) {
        this.yStyle = yStyle;
    }
}
