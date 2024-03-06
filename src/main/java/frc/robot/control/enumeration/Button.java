package frc.robot.control.enumeration;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.XboxController;

import java.util.HashMap;

/**
 * Represents the available physical buttons on an XBOX 360 Controller
 */
public enum Button {
    A, B, X, Y, LEFTBUMPER, RIGHTBUMPER, LEFTSTICK, RIGHTSTICK, SELECT, START;

    /**
     * Used to evaluate a Button with an XboxController to retrieve the status of the Physical Button
     * On that Controller. A value of True indicates the button is pressed down.
     * @param button the Button that needs to be evaluated
     * @param xbox The XboxController that is used to retrieve the value
     * @return true if the button is pressed down, false otherwise
     */
    private static HashMap<Button, Debouncer> debouncers = new HashMap<>();
    private static boolean initialized = false;
    public static boolean getButtonValue(Button button, XboxController xbox) {
        if (!initialized) {
            for (Button b : Button.values()) {
                debouncers.put(b, new Debouncer(0.05, Debouncer.DebounceType.kBoth));
            }
            initialized=true;
        }
        switch (button) {
            case A:
                return debouncers.get(button).calculate(xbox.getAButton());
            case B:
                return debouncers.get(button).calculate(xbox.getBButton());
            case X:
                return debouncers.get(button).calculate(xbox.getXButton());
            case Y:
                return debouncers.get(button).calculate(xbox.getYButton());
            case START:
                return debouncers.get(button).calculate(xbox.getStartButton());
            case SELECT:
                return debouncers.get(button).calculate(xbox.getBackButton());
            case LEFTBUMPER:
                return debouncers.get(button).calculate(xbox.getLeftBumper());
            case LEFTSTICK:
                return debouncers.get(button).calculate(xbox.getLeftStickButton());
            case RIGHTBUMPER:
                return debouncers.get(button).calculate(xbox.getRightBumper());
            case RIGHTSTICK:
                return debouncers.get(button).calculate(xbox.getRightStickButton());
            default:
                return false;
        }
    }

}
