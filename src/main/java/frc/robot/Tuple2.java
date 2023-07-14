package frc.robot;

/**
 * Tuple2 is a simple template class used for holding and transferring
 * data that contains 2 of the same type of data.
 * @param <S> The data type to be used for the resulting class of this template.
 */
public class Tuple2<S> {
    private S val1;
    private S val2;
    public Tuple2(S val1, S val2) {
        this.val1=val1;
        this.val2=val2;
    }

    public S getVal1() {
        return val1;
    }
    public S getVal2() {
        return val2;
    }
    public void setVal1(S newVal) {
        val1=newVal;
    }
    public void setVal2(S newVal) {
        val2=newVal;
    }
}
