package frc.robot.control.single;

import frc.robot.control.enumeration.Button;
import frc.robot.control.enumeration.ButtonStyle;

/**
 * A SingleControl that uses a sole Button with two configurable values for pressed and unpressed
 * ButtonStyle can be used to change the behavior as the following:
 * Switch works like a normal switch, if the physical button is pressed, it returns pressed, and vice versa.
 * Set changes the value it returns. Every time the physical button is released, the value it returns is changed. This can be used so you do not have to hold the physical button.
 */
public class SingleButton extends SingleControl {
    private ButtonStyle style;
    private Button button;
    private double unpressed = 0;
    private double pressed = 1;
    private boolean swapped = false;
    private boolean lastState = false;
    @Override
    public double getValue() {
        boolean buttonVal = Button.getButtonValue(button, xbox);
        switch (style) {
            case SWITCH:
                lastState = buttonVal;
                if (buttonVal) {
                    swapped = true;
                    return pressed;
                } else {
                    swapped = false;
                    return unpressed;
                }
            case SET:
                if (!lastState && buttonVal) {
                    swapped = !swapped;
                }
                lastState = buttonVal;
                if (swapped) {
                    return pressed;
                } else {
                    return unpressed;
                }
            default:
                return unpressed;
        }
    }

    /**
     * Sets the Button Style
     * @param style new ButtonStyle
     */
    public void setStyle(ButtonStyle style) {
        this.style = style;
    }

    /** Sets the Button
     * @param button new Button
     */
    public void setButton(Button button) {
        this.button = button;
    }

    /** Sets the Pressed Value
     * @param pressed new Pressed Value
     */
    public void setPressed(double pressed) {
        this.pressed = pressed;
    }

    /** Sets the Unpressed Value
     * @param unpressed new Unpressed Value
     */
    public void setUnpressed(double unpressed) {
        this.unpressed = unpressed;
    }
}
