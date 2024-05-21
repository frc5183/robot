package frc.robot.control.single;

import frc.robot.control.enumeration.Button;

public class SingleTwoPhaseButton extends SingleControl{
    private Button A, B;
    private double valN, valA, valB;
    public SingleTwoPhaseButton(Button buttonA, Button buttonB, double neutral, double valueA, double valueB) {
        A=buttonA;
        B=buttonB;
        valN=neutral;
        valA=valueA;
        valB=valueB;
    }
    @Override
    public double getValue() {
        if (Button.getButtonValue(A, xbox) && ! Button.getButtonValue(B, xbox)) {
            return valA;
        } else if (Button.getButtonValue(B, xbox) && ! Button.getButtonValue(A, xbox)) {
            return valB;
        } else {
            return valN;
        }
    }
}
